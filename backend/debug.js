const express = require('express');
const app = express();

app.use(express.urlencoded({extended: true}));
app.use(express.json());

require('./route/index')(app);

app.use((err, req, res, next) => {
    res.end('Problem...');
    console.log(err);
});

app.listen(80, function() {
    console.log('Hello :80');
});