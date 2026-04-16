const express = require("express");
const router = express.Router();
const auth = require("../middleware/authMiddleware");
const { getServers, getFastestServer, seedServers } = require("../controllers/vpnController");

router.get("/servers", auth, getServers);
router.get("/servers/fastest", auth, getFastestServer);
router.post("/seed", seedServers); // no auth - run once only
module.exports = router;
