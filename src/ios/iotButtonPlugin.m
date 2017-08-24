//
//  iotButtonPlugin.m
//  Interfaz Cordova
//  cordova-iot-button: esp8266
//
//  Copyright © 2017 - Roberto Osses - Chile. All rights reserved.
//

#import "iotButtonPlugin.h"
#import "smartConfig.h"
#import "DataTransform.h"
#import "RemoteSession.h"
#import "DeviceModel.h"
#import "ConfigClass.h"
#import "HttpManager.h"


@implementation iotButtonPlugin

-(void)timerEsp {
    timeTick++;
    if(timeTick == 60){
        [timer invalidate];
        [_configClass stopConfig];

        NSString *resultAsString = [NSString stringWithFormat:@"{\"res\":\"%@\",\"did\":\"%@\",\"ip\":\"%@\",\"mac\":\"%@\"}", @"ERR", @"", @"", @""];

        CDVPluginResult* pluginResult = nil;
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: resultAsString];
        [pluginResult setKeepCallbackAsBool:true];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:self.callbackId];
    }
}

- (void) smartConfig:(CDVInvokedUrlCommand *)command{
    NSString *apSsid = (NSString *)[command.arguments objectAtIndex:0];
    NSString *apPwd = (NSString *)[command.arguments objectAtIndex:1];
    self.callbackId = command.callbackId;
    NSLog(@"ESPTouchPlugin: for Cordova by rosses");
    NSLog(@"ESPTouchPlugin: apSsid--->apPwd");
    NSLog(@"ESPTouchPlugin: %@ --> %@", apSsid, apPwd);

    [timer invalidate];
    timer = [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(timerEsp) userInfo:nil repeats:YES];
    
    _configClass = [[ConfigClass alloc] init];  
    _configClass.delegate = self; 
    NSLog(@"ESPTouchPlugin: starConfigWithWifiName");
    [_configClass starConfigWithWifiName:apSsid andWifiPsw:apPwd andUserMarking: @"3517" andOrderMarking:@"" andDeviceName:@""];

}

- (void) cancelConfig:(CDVInvokedUrlCommand *)command{

    [timer invalidate];
    [_configClass stopConfig];

    NSString *resultAsString = [NSString stringWithFormat:@"{\"res\":\"%@\",\"did\":\"%@\",\"ip\":\"%@\",\"mac\":\"%@\"}", @"ERR", @"", @"", @""];

    CDVPluginResult* pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString: resultAsString];
    [pluginResult setKeepCallbackAsBool:true];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

#pragma mark ------<ConfigDelegate>
- (void)configSuccessWithDeviceMac:(DeviceModel *)deviceModel
{
    NSLog(@"ESPTouchPlugin: SUCCESS");

    [timer invalidate];
    [_configClass stopConfig];

    NSString *resultAsString = [NSString stringWithFormat:@"{\"res\":\"%@\",\"did\":\"%@\",\"ip\":\"%@\",\"mac\":\"%@\"}", @"OK", deviceModel.did, @"", deviceModel.dmac];

    CDVPluginResult* pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString: resultAsString];
    [pluginResult setKeepCallbackAsBool:true];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.callbackId];
}

@end