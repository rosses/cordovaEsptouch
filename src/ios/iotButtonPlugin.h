//
//  iotButtonPlugin.h
//  Interfaz Cordova
//  cordova-iot-button: esp8266
//
//  Copyright © 2017 - Roberto Osses - Chile. All rights reserved.
//
#import <Cordova/CDVPlugin.h>
#import <Cordova/CDVPluginResult.h>
#import "DataTransform.h"
#import "ConfigClass.h"

@interface esptouchPlugin : CDVPlugin {
	NSTimer *timer;
	int timeTick;
	DataTransform *_dataTransform;
	ConfigClass *_configClass;  
}
@property NSString * callbackId;

- (void)smartConfig:(CDVInvokedUrlCommand*)command;

- (void)cancelConfig:(CDVInvokedUrlCommand*)command;

@end
