const express = require("express");
const router = express.Router();
const auth = require("../middleware/authMiddleware");
const { getProfile, updateProfile } = require("../controllers/userController");

router.use(auth);
router.get("/profile", getProfile);
router.put("/profile", updateProfile);
module.exports = router;
