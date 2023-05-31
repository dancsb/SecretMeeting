const express = require('express');
const app = express();

const fs = require('fs');
const http = require('http');
const https = require('https');

const credentials = {
    key: fs.readFileSync('/etc/letsencrypt/live/secret.dancs.org/privkey.pem'),
    cert: fs.readFileSync('/etc/letsencrypt/live/secret.dancs.org/fullchain.pem')
};

app.use(express.urlencoded({extended: true}));
app.use(express.json());

require('./route/index')(app);

app.use((err, req, res, next) => {
    res.end('Problem...');
    console.log(err);
});

const httpServer = http.createServer(function (req, res) {
    res.writeHead(301, { "Location": "https://" + req.headers['host'] + req.url });
    res.end();
});
const httpsServer = https.createServer(credentials, app);

httpServer.listen(803);
httpsServer.listen(4433);