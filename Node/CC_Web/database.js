module.exports = {
    getService: function (serviceName) {
        var svc;

        if (serviceName == 'RightGarageDoor')
            svc = { id: 1, svcName: 'RightGarageDoor', nodeId: 1, api: 'api/relays/', pin: '23/', act: 'flipValueWithTimeout/', arg: '1000', argDesc: 'Timeout' };
        else if (serviceName == 'LeftGarageDoor')
            svc = { id: 2, svcName: 'LeftGarageDoor', nodeId: 1, api: 'api/relays/', pin: '24/', act: 'flipValueWithTimeout/', arg: '1000', argDesc: 'Timeout' };
        else if (serviceName == 'GaragePing')
            svc = { id: 3, svcName: 'GaragePing', nodeId: 1, api: 'api/ping', pin: '', act: '', arg: '', argDesc: '' };
        else if (serviceName == 'RobotPing')
            svc = { id: 4, svcName: 'RobotPing', nodeId: 2, api: 'api/ping', pin: '', act: '', arg: '', argDesc: '' };
        else if (serviceName == 'SensorPing')
            svc = { id: 5, svcName: 'SensorPing', nodeId: 3, api: 'api/ping', pin: '', act: '', arg: '', argDesc: '' };
        else if (serviceName == 'Temp')
            svc = { id: 6, svcName: 'Temp', nodeId: 3, api: 'api/temp', pin: '', act: '', arg: '', argDesc: '' };
        else if (serviceName == 'Light')
            svc = { id: 7, svcName: 'Light', nodeId: 3, api: 'api/light', pin: '', act: '', arg: '', argDesc: '' };
        else if (serviceName == 'EnviroPing')
            svc = { id: 8, svcName: 'SensorPing', nodeId: 4, api: 'api/ping', pin: '', act: '', arg: '', argDesc: '' };
        else if (serviceName == 'EnviroTemp')
            svc = { id: 9, svcName: 'EnviroTemp', nodeId: 4, api: 'api/temp', pin: '', act: '', arg: '', argDesc: '' };
        else if (serviceName == 'EnviroLight')
            svc = { id: 10, svcName: 'EnviroLight', nodeId: 4, api: 'api/light', pin: '', act: '', arg: '', argDesc: '' };

        return svc;
    },
    getNodeById: function (nodeId) {
        var node;

        if (nodeId == 1)
            node = { id: 1, serverName: 'pi-garage', protocol: 'http://', ipAddress: '192.168.0.201', port: ':3000/' };
        else if (nodeId == 2)
            node = { id: 2, serverName: 'pi-robot', protocol: 'http://', ipAddress: '192.168.0.202', port: ':3000/' };
        else if (nodeId == 3)
            node = { id: 3, serverName: 'pi-sensor', protocol: 'http://', ipAddress: '192.168.0.203', port: ':3000/' };
        else if (nodeId == 4)
            node = { id: 4, serverName: 'pi-enviro', protocol: 'http://', ipAddress: '192.168.0.204', port: ':3000/' };

        return node;
    },
    getNodeByServerName: function (name) {
        var node;

        if (name == 'pi-garage')
            node = getNodeById(1);
        else if (name == 'pi-robot')
            node = getNodeById(2);
        else if (name == 'pi-sensor')
            node = getNodeById(3);
        else if (name == 'pi-enviro')
            node = getNodeById(4);

        return node;
    }
};
//var dblite = require('dblite');
//var db = dblite('commandcenter.db');

//function initialize() {
//    db.query('DROP TABLE IF EXISTS Svc;');
//    db.query('DROP TABLE IF EXISTS Node;');
//    db.query('CREATE TABLE IF NOT EXISTS Node (id INTEGER PRIMARY KEY, serverName TEXT, protocol TEXT, ipAddress TEXT	port TEXT);');
//    db.query('CREATE TABLE IF NOT EXISTS Svc(id INTEGER PRIMARY KEY, svcName TEXT, nodeId INTEGER, api TEXT, pin TEXT, act TEXT, arg TEXT, argDesc TEXT, CONSTRAINT Svc_fk_nodeId FOREIGN KEY (nodeId) REFERENCES Node (id) ON UPDATE CASCADE ON DELETE CASCADE);');
//    db.query("INSERT INTO Node (id, serverName, protocol, ipAddress, port) VALUES (1, 'pi0', 'http://', '192.168.0.107', ':3000/');");
//    db.query("INSERT INTO Node (id, serverName, protocal, ipAddress, port) VALUES (2, 'pi1', 'http://', '127.0.0.1', ':3000/');");
//    db.query("INSERT INTO Node (id, serverName, protocol, ipAddress, port) VALUES (@id, '@serverName, @protocol, @ipAddress, @port);", { id: 3, serverName: 'pi2', protocol: 'http://', ipAddress: '192.168.0.134', port: ':3000/' });
//    db.query("INSERT INTO Node (id, serverName, protocol, ipAddress, port) VALUES (4, 'pi3', 'http://', '127.0.0.1', ':3000/');");
//    db.query("INSERT INTO Svc (id, svcName, nodeId, api, pin, act, arg, argDesc) VALUES (1, 'Ping', 3, 'ping', 23, '', '', '');");
//    db.query("INSERT INTO Svc (id, svcName, nodeId, api, pin, act, arg, argDesc) VALUES (2, 'RightGarageDoor', 3, 'relays/', 23, 'flipValueWithTimeout', '1000', 'Timeout value');");
//    db.query("INSERT INTO Svc (id, svcName, nodeId, api, pin, act, arg, argDesc) VALUES (3, 'LeftGarageDoor', 3, 'relays', 24, 'flipValueWithTimeout', '1000', 'Timeout value');");
//    db.query('INSERT INTO test VALUES(null, ?)', ['some text']);
//}
