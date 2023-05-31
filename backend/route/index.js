const authMW = require('../middleware/authMW');
const getMeetingsMW = require('../middleware/meeting/getMeetingsMW');
const saveMeetingsMW = require('../middleware/meeting/saveMeetingsMW');
const delMeetingsMW = require('../middleware/meeting/delMeetingsMW');

const MeetingModel = require('../models/meeting');
const ResponseModel = require('../models/response');

module.exports = function(app) {
    const objRepo = {
        MeetingModel: MeetingModel,
        ResponseModel: ResponseModel
    };

    app.get(
        '/meeting',
        authMW(objRepo),
        getMeetingsMW(objRepo)
    );

    app.post(
        '/meeting',
        authMW(objRepo),
        saveMeetingsMW(objRepo)
    );

    app.delete(
        '/meeting/:meetingid',
        authMW(objRepo),
        delMeetingsMW(objRepo)
    );

    app.get(
        '/apk',
        authMW(objRepo),
        function(req,res) {
            res.set('Content-Disposition', 'attachment; filename="SecretMeeting.apk"'); 
            res.sendFile('SecretMeeting.apk', { root: __dirname + '/../' });
        }
    );

    app.get(
        '/',
        function(req,res) {
            res.json({ API_version: "1.0.3" });
        }
    );
};