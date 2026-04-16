const express = require("express");
const dotenv = require("dotenv");
const cors = require("cors");
const connectDB = require("./config/db");

dotenv.config();
connectDB();

const app = express();

app.use(cors());
app.use(express.json());

// ── Auth ──────────────────────────────────────────
app.use("/api/auth", require("./routes/authRoutes"));

// ── Servers (now MongoDB-backed) ──────────────────
app.use("/api/vpn", require("./routes/vpnRoutes"));

// ── Sessions (new) ────────────────────────────────
app.use("/api/sessions", require("./routes/sessionRoutes"));

// ── User profile (new) ────────────────────────────
app.use("/api/user", require("./routes/userRoutes"));

// Health check
app.get("/", (req, res) => res.json({ status: "Obsidian VPN Backend Running" }));

app.listen(5000, () => console.log("Server running on port 5000"));