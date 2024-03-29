<!DOCTYPE html>
<html>
<head>
<title>Device Properties Example</title>

<script type="text/javascript" charset="utf-8" src="../JS/wormhole.js"></script>
<script type="text/javascript" charset="utf-8">

// Wait for Wormhole to load
//
document.addEventListener("deviceready", onDeviceReady, false);

// Wormhole is ready
//
function onDeviceReady() {
	alert("imei="+device.name);
    var element = document.getElementById('deviceProperties');

    element.innerHTML = 'Device Name: '     + device.name     + '<br />' + 
                        'Device Platform: ' + device.platform + '<br />' + 
                        'Device UUID: '     + device.uuid     + '<br />' + 
                        'Device Version: '  + device.version  + '<br />';
                        
}

</script>
</head>
<body>
  <p id="deviceProperties">Loading device properties...</p>
</body>
</html>