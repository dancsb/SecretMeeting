const requireOption = require('../requireOption');

module.exports = function (objectrepository) {

    const MeetingModel = requireOption(objectrepository, 'MeetingModel');
    const ResponseModel = requireOption(objectrepository, 'ResponseModel');

    return function (req, res, next) {
        MeetingModel.deleteOne({
            _id: req.params.meetingid
        }, err => {
            if (err) {
                return next(err);
            }
            
            var response = new ResponseModel();
            response.code = 200;
            response.message = "Sirály!"
            res.json(response);
            return next();
        });
    };

};