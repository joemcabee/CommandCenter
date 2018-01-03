/*
 * GET home page.
 */

function getService(serviceName) {
    var svc;

    if (serviceName == 'Ping')
        svc = { id: 1, svcName: 'Ping', nodeId: 3, api: 'ping', pin: 23, act: '', arg: '', argDesc: '' };
    else if (serviceName == 'RightGarageDoor')
        svc = { id: 2, svcName: 'RightGarageDoor', nodeId: 3, api: 'relays/', pin: 23, act: 'flipValueWithTimeout', arg: '1000', argDesc: 'Timeout' };
    else if (serviceName == 'LeftGarageDoor')
        svc = { id: 3, svcName: 'LeftGarageDoor', nodeId: 3, api: 'relays', pin: 24, act: 'flipValueWithTimeout', arg: '1000', argDesc: 'Timeout' };

    return svc;
}

exports.index = function (req, res) {
    res.render('index', { title: 'Home', year: new Date().getFullYear() });
};

exports.robot = function (req, res) {
    res.render('robot', { title: 'Robot', year: new Date().getFullYear(), message: 'Robot' });
};

exports.garage = function (req, res) {
    var left = getService('LeftGarageDoor');
    var right = getService('LeftGarageDoor');

    res.render('garage', {
        title: 'Garage',
        year: new Date().getFullYear(),
        message: 'Garage',
        leftPin: left.pin,
        leftAct: left.act,
        leftArg: left.arg,
        rightPin: right.pin,
        rightAct: right.act,
        rightArg: right.arg
    });
};