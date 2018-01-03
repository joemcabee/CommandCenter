var Gpio = require('onoff').Gpio,
    leftGargageDoor = new Gpio(24, 'out'),
    rightGarageDoor = new Gpio(23, 'out'),
    interiorGarageLights = new Gpio(12, 'out'),
    exteriorLights = new Gpio(23, 'out');

leftGargageDoor.lock = false;
rightGarageDoor.lock = false;
interiorGarageLights.lock = false;
exteriorLights.lock = false;

//var iv = setInterval(function () {
//    led.writeSync(led.readSync() === 0 ? 1 : 0)
//    console.log("test");
//}, 500);

//// Stop blinking the LED and turn it off after 5 seconds.
//setTimeout(function () {
//    clearInterval(iv); // Stop blinking
//    led.writeSync(0);  // Turn LED off.
//    led.unexport();    // Unexport GPIO and free resources
//}, 5000);

module.exports = {
    getValue: function (device) {
        console.log('relay.js -> getValue: ' + device);
        var gpio;

        if (device == 'LeftGarageDoor') {
            gpio = leftGargageDoor;
        }
        else if (device == 'RightGarageDoor') {
            gpio = rightGarageDoor;
        }
        else if (device == 'InteriorGarageLights') {
            gpio = interiorGarageLights;
        }
        else if (device == 'ExteriorLights') {
            gpio = exteriorLights;
        }

        if (gpio == null) {
            console.log('Could not find device "' + device + '".');
            return null;
        }
        else {
            var value = gpio.readSync();
            console.log('Device "' + device + '" has current value of "' + value + '".');
            return gpio.readSync();
        }
    },
    changeValue: function (device, value) {
        var gpio;

        if (device == 'LeftGarageDoor') {
            gpio = leftGargageDoor;
            flipValueWithTimeout(device, gpio, 1000);
        }
        else if (device == 'RightGarageDoor') {
            gpio = rightGarageDoor;
            flipValueWithTimeout(device, gpio, 1000);
        }
        else if (device == 'InteriorGarageLights') {
            gpio = interiorGarageLights;
            flipValueWithLock(device, gpio);
        }
        else if (device == 'ExteriorLights') {
            gpio = exteriorLights;
            flipValueWithLock(device, gpio);
        }
    }
};

function setValue(device, gpio, value) {
    //only proceed if device found
    if (gpio != null) {
        console.log('Found device "' + device + '". Starting setValue.');
        var currentValue = gpio.readSync();

        console.log('Current value for "' + device + '" is "' + currentValue  +'"');
        
        //only write new value if it's different than current value
        if (currentValue == value) {
            console.log('Value is already set to "' + value + '". No changes made.');
        }
        else {
            gpio.writeSync(value);

            console.log('Changed value to "' + value + '".');

            //release pin if shutting it off
            if (value == 0) {
                gpio.unexport();
                console.log('Unexport called.');
            }
        }
    }
}

function setValueWithTimeout(device, gpio, timeout) {
    setValue(device, gpio, 1);

    setTimeout(function () {
        setValue(device, gpio, 0);
        console.log('Timeout elapsed. Value flipped back.');
    }, timeout);
}

function flipValueWithLock(device, gpio) {
    if (gpio.lock == true) {
        console.log(device + ' is locked. Throwing Error.');
        throw 'LockException';
    }
    else {
        gpio.lock = true;
        console.log('Locking ' + device);

        flipValue(device, gpio);

        gpio.lock = false;
        console.log('Unlocked ' + device);
    }
}

function flipValue(device, gpio) {
    //only proceed if device found
    if (gpio != null) {
        console.log('Found device "' + device + '". Starting flipValue.');
        var currentValue = gpio.readSync();

        console.log('Current value for "' + device + '" is "' + currentValue + '"');

        var newValue = currentValue ^ 1;

        gpio.writeSync(newValue);

        console.log('Changed value to "' + newValue + '".');

        //release pin if shutting it off
        //if (newValue == 0) {
        //    gpio.unexport();
        //    console.log('Unexport called.');
        //}
    }
}

function flipValueWithTimeout(device, gpio, timeout) {
    if (gpio != null) {
        if (gpio.lock == true) {
            console.log(device + ' is locked. Throwing Error.');
            throw 'LockException';
        }
        else {
            gpio.lock = true;
            console.log('Locking ' + device);
        }

        console.log('Found device "' + device + '". Starting flipValueWithTimeout.');

        flipValue(device, gpio);

        setTimeout(function () {
            flipValue(device, gpio);
            console.log('flipValue timeout elapsed. Value flipped back.');
            gpio.lock = false;
            console.log(device + ' unlocked.');
        }, timeout);
    }
}