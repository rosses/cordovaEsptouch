#import "esptouchPlugin.h"
#import "smartConfig.h"
#import "DataTransform.h"
#import "RemoteSession.h"
#import "DeviceModel.h"
#import "ConfigClass.h"
#import "HttpManager.h"


@implementation esptouchPlugin

-(void)timerEsp {
    timeTick++;
    if(timeTick == 60){
        [timer invalidate];
        [_configClass stopConfig];

        NSString *res = @"ERR";
        NSString *did = @"";
        NSString *ip = @"";
        NSString *mac = @"";

        NSDictionary *jsonDictionary = [NSDictionary dictionaryWithObjectsAndKeys:
                                        res, @"", 
                                        did, @"",
                                        ip, @"",
                                        mac, @"",
                                        nil];

    NSError *error;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:jsonDictionary options:0 error:&error];
    NSString *resultAsString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];

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

    NSString *res = @"ERR";
    NSString *did = @"";
    NSString *ip = @"";
    NSString *mac = @"";

    NSDictionary *jsonDictionary = [NSDictionary dictionaryWithObjectsAndKeys:
                                    res, @"", 
                                    did, @"",
                                    ip, @"",
                                    mac, @"",
                                    nil];

    NSError *error;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:jsonDictionary options:0 error:&error];
    NSString *resultAsString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];

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

    NSString *res = @"OK";
    NSString *did = deviceModel.did;
    NSString *mac = deviceModel.dmac;
    NSString *ip = @"";

    NSDictionary *jsonDictionary = [NSDictionary dictionaryWithObjectsAndKeys:
                                    res, @"", 
                                    did, @"",
                                    ip, @"",
                                    mac, @"",
                                    nil];

    NSError *error;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:jsonDictionary options:0 error:&error];
    NSString *resultAsString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];

    CDVPluginResult* pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString: resultAsString];
    [pluginResult setKeepCallbackAsBool:true];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.callbackId];
}

@end