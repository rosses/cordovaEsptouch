var exec    = require('cordova/exec'),
cordova = require('cordova');

module.exports = {
	smartConfig:function(apSsid,apPassword,successCallback, errorCallback){
		exec(successCallback, errorCallback, "esptouchPlugin", "smartConfig", [apSsid,apPassword]);
	},
	
	cancelConfig:function(successCallback, errorCallback){
		exec(successCallback, errorCallback, "esptouchPlugin", "cancelConfig", []);
	}
}