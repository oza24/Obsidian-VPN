const User = require("../models/User");

// GET /api/user/profile
exports.getProfile = async (req, res) => {
    try {
        const user = req.user;
        res.json({ id: user._id, name: user.name, email: user.email, subscription: user.subscription, devices: user.devices, maxDevices: user.maxDevices, createdAt: user.createdAt });
    } catch (err) {
        res.status(500).json({ message: "Server error" });
    }
};

// PUT /api/user/profile
exports.updateProfile = async (req, res) => {
    try {
        const { name } = req.body;
        const user = await User.findByIdAndUpdate(req.user._id, { name }, { new: true });
        res.json({ id: user._id, name: user.name, email: user.email });
    } catch (err) {
        res.status(500).json({ message: "Server error" });
    }
};
