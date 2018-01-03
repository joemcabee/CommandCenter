$(function () {
    $.ajax({
        method: 'GET',
        url: baseUrl + 'RobotPing'
    }).complete(function (data, status) {
        var statusLabel = $('.statusPiRobot');
        var cls = 'text-info';
        var txt = 'Online';

        if (status != 'success') {
            cls = 'text-warning';
            txt = 'Offline';
        }

        statusLabel.text(txt).removeClass('text-muted').addClass(cls);
    });

    $.ajax({
        method: 'GET',
        url: baseUrl + 'GaragePing'
    }).complete(function (data, status) {
        var statusLabel = $('.statusPiGarage');
        var cls = 'text-info';
        var txt = 'Online';

        if (status != 'success') {
            cls = 'text-warning';
            txt = 'Offline';
        }

        statusLabel.text(txt).removeClass('text-muted').addClass(cls);
    });
});