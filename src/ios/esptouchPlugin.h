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

//@property (nonatomic, strong) NSCondition *_condition;
//@property (atomic, strong) ESPTouchTask *_esptouchTask;
//@property (atomic, strong)   
//@property (atomic, strong) 


- (void)smartConfig:(CDVInvokedUrlCommand*)command;

- (void)cancelConfig:(CDVInvokedUrlCommand*)command;

@end
