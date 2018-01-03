$(function () {
    //getStatus('ExteriorLights', '.statusLights');

    $('.webcamPiGarage')[0].href = baseUrlNoProxy + 'pigarage/webcam';

    $('.leftDoor').click(function () {
        showLoader();

        $.ajax({
            method: 'POST',
            url: baseUrl + 'LeftGarageDoor',
        }).complete(function (data, status) {
            hideLoader();
        });
    });

    $('.rightDoor').click(function () {
        showLoader();

        $.ajax({
            method: 'POST',
            url: baseUrl + 'RightGarageDoor',
        }).complete(function (data, status) {
            hideLoader();
        });
    });

    $('.lights').click(function () {
        showLoader();
        var id = 'ExteriorLights';
        var newValue = -1;

        $.ajax({
            method: 'PUT',
            url: urlCurrent + 'relays/' + id,
            data: { value: '1' }
        }).complete(function (data, status) {
            var txt = "Unknown";
            var cls = 'text-info';

            if (data.statusText == "OK") {
                var val = data.responseJSON.value;

                if (val == 0)
                    txt = "Off";
                else if (val == 1)
                    txt = "On";
            }
            else {
                txt = "Error";
                cls = 'text-warning';
            }

            $('.statusLights').text(txt).removeClass('text-info').addClass(cls);
            hideLoader();
        });
    });
});

function getStatus(id, selector) {
    $.ajax({
        method: 'GET',
        url: urlCurrent + 'relays/' + id
    }).complete(function (data, status) {
        var label = 'Unknown';
        var textClass = 'text-info';

        if (data.statusText == "OK") {
            var val = data.responseJSON.value;

            if (val == 0) {
                label = 'Off';
                textClass = 'text-info';
            }
            else if (val == 1)
                label = 'On';
        }
        else {
            label = 'Unknown - Error';
            textClass = 'text-warning';
        }

        $(selector).text(label).removeClass('text-muted').addClass(textClass);
    });
}