#  iotButtonPlugin
Plugin Cordova Esptouch c/ smartconfig esp8266 para Boton fabricado por Ogemray
## Instalación
cordova plugin add https://github.com/rosses/cordova-iot-button.git

## Métodos

1.iotButtonPlugin.smartConfig 

```javascript
esptouchPlugin.smartConfig(SSID, Password, successFunction(json), errorFunction(json));
```

SSID = Wifi
Password = Clave Wifi
successFunction = Retorna un string que se debe parsear a JSON con el resultado, IP, MAC y DeviceID
errorFunction = Returna un string que se debe parsear a JSON con el resultado de ERROR e IP,MAC,DeviceID en blanco 

2.iotButtonPlugin.cancelConfig

```javascript
iotButtonPlugin.cancelConfig(successFunction, cancelFunction);
```
Los parametros de los callback no son interesantes.

# Importante

No debes llamar en 2 instancias a smartConfig, solo en 1, antes asegurece de llamar a cancelConfig. 2 llamadas a smartConfig pueden otorgar un resultado no deseado.

# Credits

ESPTouch de ExpressIf
Ogemray extension (Fabricante)