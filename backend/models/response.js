const db = require('../config/db');

const Response = db.model('Response', {
    code: Number,
    message: String
});

module.exports = Response;