const Server = require("../models/Server");

// GET /api/vpn/servers
exports.getServers = async (req, res) => {
    try {
        const servers = await Server.find({ isOnline: true }).sort({ ping: 1 });
        res.json(servers);
    } catch (err) {
        res.status(500).json({ message: "Server error" });
    }
};

// GET /api/vpn/servers/fastest
exports.getFastestServer = async (req, res) => {
    try {
        const server = await Server.findOne({ isOnline: true }).sort({ ping: 1 });
        if (!server) return res.status(404).json({ message: "No servers available" });
        res.json(server);
    } catch (err) {
        res.status(500).json({ message: "Server error" });
    }
};

// POST /api/vpn/seed  ← run once to populate MongoDB
exports.seedServers = async (req, res) => {
    try {
        await Server.deleteMany({});
        await Server.insertMany([
            {
                country: "India", city: "Mumbai",
                ip: "13.200.45.13",
                publicKey: "MDT12RogedqjiM6xcNdfyT3QfBjY7oHq0D3nQWO12EA=",
                ping: 18, latitude: 19.0760, longitude: 72.8777,
                flag: "🇮🇳", isOnline: true, load: 23
            },
            {
                country: "Singapore", city: "Singapore",
                ip: "47.131.18.89",
                publicKey: "W3fjjg5am+Q8Rrwo6sEPSDmPMa6C1C8SWZoPg6meVHU=",
                ping: 45, latitude: 1.3521, longitude: 103.8198,
                flag: "🇸🇬", isOnline: true, load: 41
            }
        ]);
        res.json({ message: "Servers seeded", count: 2 });
    } catch (err) {
        res.status(500).json({ message: "Server error" });
    }
};
