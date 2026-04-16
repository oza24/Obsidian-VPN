const mongoose = require("mongoose");

const sessionSchema = new mongoose.Schema({
    user: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
    server: { type: mongoose.Schema.Types.ObjectId, ref: "Server", required: true },
    startTime: { type: Date, default: Date.now },
    endTime: { type: Date, default: null },
    duration: { type: Number, default: 0 },
    bytesDownloaded: { type: Number, default: 0 },
    bytesUploaded: { type: Number, default: 0 },
    virtualIp: { type: String, default: "10.0.1.3" },
    isActive: { type: Boolean, default: true }
});

module.exports = mongoose.model("Session", sessionSchema);
