const requireOption = require('../requireOption');

module.exports = function (objectrepository) {

    const MeetingModel = requireOption(objectrepository, 'MeetingModel');

    return function (req, res, next) {
        MeetingModel.find({
        }, null, {
            lean: {}
        }, async (err, meetings) => {
            if (err) {
                return next(err);
            }

            res.json(meetings);
            return next();
        });
    };

};