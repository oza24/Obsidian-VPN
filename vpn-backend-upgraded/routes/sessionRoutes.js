const express = require("express");
const router = express.Router();
const auth = require("../middleware/authMiddleware");
const { startSession, endSession, getCurrentSession, getSessionHistory } = require("../controllers/sessionController");

router.use(auth);
router.post("/start", startSession);
router.post("/end", endSession);
router.get("/current", getCurrentSession);
router.get("/history", getSessionHistory);
module.exports = router;
