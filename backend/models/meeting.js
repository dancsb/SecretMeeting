const db = require('../config/db');

const Meeting = db.model('Meeting', {
    topic: String,
    desc: String,
    date: {
        type: Date,
        default: Date.now
    },
    coord: {
        lon: Number,
        lat: Number
    }
});

module.exports = Meeting;