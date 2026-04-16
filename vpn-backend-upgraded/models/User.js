const mongoose = require("mongoose");

const userSchema = new mongoose.Schema({
    name: { type: String, required: true, trim: true },
    email: { type: String, required: true, unique: true, lowercase: true },
    password: { type: String, required: true, select: false },
    subscription: {
        plan: { type: String, enum: ["free", "premium"], default: "free" },
        activeSince: { type: Date, default: Date.now },
        expiresAt: { type: Date, default: null }
    },
    devices: { type: Number, default: 1 },
    maxDevices: { type: Number, default: 10 },
    createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model("User", userSchema);
