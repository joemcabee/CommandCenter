//var dblite = require('dblite');
//var db = dblite('commandcenter.db');

//function initialize() {
//    db.query('DROP TABLE IF EXISTS Svc;');
//    db.query('DROP TABLE IF EXISTS Node;');
//    db.query('CREATE TABLE IF NOT EXISTS Node (id INTEGER PRIMARY KEY, serverName TEXT, protocol TEXT, ipAddress TEXT	port TEXT);');
//    db.query('CREATE TABLE IF NOT EXISTS Svc(id INTEGER PRIMARY KEY, svcName TEXT, nodeId INTEGER, api TEXT, pin TEXT, act TEXT, arg TEXT, argDesc TEXT, CONSTRAINT Svc_fk_nodeId FOREIGN KEY (nodeId) REFERENCES Node (id) ON UPDATE CASCADE ON DELETE CASCADE);');
//    //db.query("INSERT INTO Node (id, serverName, protocol, ipAddress, port) VALUES (1, 'pi0', 'http://', '192.168.0.107', ':3000/');");
//    //db.query("INSERT INTO Node (id, serverName, protocal, ipAddress, port) VALUES (2, 'pi1', 'http://', '127.0.0.1', ':3000/');");
//    db.query("INSERT INTO Node (id, serverName, protocol, ipAddress, port) VALUES (@id, '@serverName, @protocol, @ipAddress, @port);", { id: 3, serverName: 'pi2', protocol: 'http://', ipAddress: '192.168.0.134', port: ':3000/' });
//    //db.query("INSERT INTO Node (id, serverName, protocol, ipAddress, port) VALUES (4, 'pi3', 'http://', '127.0.0.1', ':3000/');");
//    db.query("INSERT INTO Svc (id, svcName, nodeId, api, pin, act, arg, argDesc) VALUES (1, 'Ping', 3, 'ping', 23, '', '', '');");
//    //db.query("INSERT INTO Svc (id, svcName, nodeId, api, pin, act, arg, argDesc) VALUES (2, 'RightGarageDoor', 3, 'relays/', 23, 'flipValueWithTimeout', '1000', 'Timeout value');");
//    //db.query("INSERT INTO Svc (id, svcName, nodeId, api, pin, act, arg, argDesc) VALUES (3, 'LeftGarageDoor', 3, 'relays', 24, 'flipValueWithTimeout', '1000', 'Timeout value');");
//    //db.query('INSERT INTO test VALUES(null, ?)', ['some text']);
//}

//function getService(serviceName) {
//    var svc = db.query('SELECT * FROM Svc WHERE svcName = ?',
//        [serviceName],
//        ['id', 'svcName', 'nodeId', 'api', 'pin', 'act', 'arg', 'argDesc']);

//    return svc[0];
//}

//function getNode(nodeId) {
//    var node = db.query('SELECT * FROM Node WHERE id = ?',
//        svc.nodeId,
//        ['id', 'serverName', 'protocol', 'ipAddress', 'port']);

//    return node[0];
//}
