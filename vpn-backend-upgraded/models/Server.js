const mongoose = require("mongoose");

const serverSchema = new mongoose.Schema({
    country: { type: String, required: true },
    city: { type: String, required: true },
    ip: { type: String, required: true },
    publicKey: { type: String, required: true },
    ping: { type: Number, default: 0 },
    latitude: { type: Number, default: 0 },
    longitude: { type: Number, default: 0 },
    flag: { type: String, default: "🌍" },
    isOnline: { type: Boolean, default: true },
    load: { type: Number, default: 0 }
});

module.exports = mongoose.model("Server", serverSchema);
