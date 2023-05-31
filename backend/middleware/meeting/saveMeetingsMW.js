const requireOption = require('../requireOption');

module.exports = function (objectrepository) {

    const MeetingModel = requireOption(objectrepository, 'MeetingModel');

    return function (req, res, next) {
        var json = req.body;
        if (json.hasOwnProperty("_id")) {
            delete json["_id"]
        }
        var meeting = new MeetingModel(json);

        meeting.save(err => {
            if (err) {
                return next(err);
            }
            
            res.json(meeting);
            return next();
        });
    };

};