/**
 * Module dependencies.
 */

var express = require('express');
var http = require('http');
var path = require('path');
var fetch = require('node-fetch');
var routes = require('./routes');
var database = require('./database');
var request = require('request');

// create a rolling file logger based on date/time that fires process events
//var opts = {
//    errorEventName: 'error',
//    logDirectory: '/logfiles', // NOTE: folder must exist and be writable...
//    fileNamePattern: 'roll-<DATE>.log',
//    dateFormat: 'YYYY.MM.DD'
//};

//var log = require('simple-node-logger').createRollingFileLogger(opts);

var app = express();

// all environments
app.set('port', process.env.PORT || 3000);
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.json());
app.use(express.urlencoded());
app.use(express.methodOverride());
app.use(app.router);
app.use(require('stylus').middleware(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'public')));

// development only
if ('development' == app.get('env')) {
    app.use(express.errorHandler());
}

app.get('/', routes.index);
app.get('/robot', routes.robot);
app.get('/garage', routes.garage);

app.get('/proxy/:service', function (req, res) {
    console.log('GET Proxy request for: ' + req.params.service);
    
    var service = database.getService(req.params.service);
    var node = database.getNodeById(service.nodeId);

    console.log('Service: ' + service.svcName + ', Node: ' + node.serverName);

    //modify the url in any way you want
    var endpoint = node.protocol + node.ipAddress + node.port + service.api + service.pin + service.act + service.arg;
    console.log('New proxy url: ' + endpoint);

    request(endpoint, function (error, response, body) {
        if (error) {
            res.status(500).send();
            return console.error('Get failed for ' + req.params.service + ':', error);
        }
        
        console.log('Get Successful for ' + req.params.service);
    }).pipe(res);
});

app.post('/proxy/:service', function (req, res) {
    console.log('POST Proxy request for: ' + req.params.service);

    var service = database.getService(req.params.service);
    var node = database.getNodeById(service.nodeId);

    console.log('Service: ' + service.svcName + ', Node: ' + node.serverName);

    //modify the url in any way you want
    var endpoint = node.protocol + node.ipAddress + node.port + service.api + service.pin + service.act + service.arg;
    console.log('New proxy url: ' + endpoint);

    request.post({ url: endpoint }, function callback(err, httpResponse, body) {
        if (err) {
            res.status(500).send();
            return console.error('Post failed for ' + req.params.service + ':', err);
        }

        res.status(200).send();
        console.log('Post successful for ' + req.params.service + '.  Server responded with:', body);
    });
});

http.createServer(app).listen(app.get('port'), function () {
    console.log('Express server listening on port ' + app.get('port'));
});
