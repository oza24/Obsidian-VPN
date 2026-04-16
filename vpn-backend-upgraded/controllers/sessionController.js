const Session = require("../models/Session");
const Server = require("../models/Server");

// POST /api/sessions/start
exports.startSession = async (req, res) => {
    try {
        const { serverId, virtualIp } = req.body;

        // End any existing active session
        await Session.updateMany(
            { user: req.user._id, isActive: true },
            { isActive: false, endTime: new Date() }
        );

        const server = await Server.findById(serverId);
        if (!server) return res.status(404).json({ message: "Server not found" });

        const session = await Session.create({
            user: req.user._id,
            server: serverId,
            virtualIp: virtualIp || "10.0.1.3",
            isActive: true
        });

        await session.populate("server");
        res.status(201).json(session);
    } catch (err) {
        console.error(err);
        res.status(500).json({ message: "Server error" });
    }
};

// POST /api/sessions/end
exports.endSession = async (req, res) => {
    try {
        const { bytesDownloaded, bytesUploaded } = req.body;

        const session = await Session.findOneAndUpdate(
            { user: req.user._id, isActive: true },
            { isActive: false, endTime: new Date(), bytesDownloaded: bytesDownloaded || 0, bytesUploaded: bytesUploaded || 0 },
            { new: true }
        );

        if (!session) return res.status(404).json({ message: "No active session" });

        session.duration = Math.floor((session.endTime - session.startTime) / 1000);
        await session.save();
        res.json(session);
    } catch (err) {
        res.status(500).json({ message: "Server error" });
    }
};

// GET /api/sessions/current
exports.getCurrentSession = async (req, res) => {
    try {
        const session = await Session.findOne({ user: req.user._id, isActive: true }).populate("server");
        if (!session) return res.json({ active: false });

        const liveDuration = Math.floor((new Date() - session.startTime) / 1000);
        res.json({ active: true, session: { ...session.toObject(), liveDuration } });
    } catch (err) {
        res.status(500).json({ message: "Server error" });
    }
};

// GET /api/sessions/history
exports.getSessionHistory = async (req, res) => {
    try {
        const page = parseInt(req.query.page) || 1;
        const limit = parseInt(req.query.limit) || 10;

        const sessions = await Session.find({ user: req.user._id, isActive: false })
            .populate("server", "country city flag")
            .sort({ startTime: -1 })
            .skip((page - 1) * limit)
            .limit(limit);

        const stats = await Session.aggregate([
            { $match: { user: req.user._id } },
            { $group: {
                _id: null,
                totalDownloaded: { $sum: "$bytesDownloaded" },
                totalUploaded: { $sum: "$bytesUploaded" },
                totalDuration: { $sum: "$duration" },
                totalSessions: { $sum: 1 }
            }}
        ]);

        res.json({ sessions, stats: stats[0] || { totalDownloaded: 0, totalUploaded: 0, totalDuration: 0, totalSessions: 0 } });
    } catch (err) {
        res.status(500).json({ message: "Server error" });
    }
};
