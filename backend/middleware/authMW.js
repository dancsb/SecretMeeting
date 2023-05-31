const apikey = require('../config/api')

module.exports = function () {
    return function (req, res, next) {
        if (typeof req.query.apikey === 'undefined' || req.query.apikey != apikey) {
            return res.end('No API key, no service!');
        }

        return next();
    };
};