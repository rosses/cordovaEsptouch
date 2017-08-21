#import "esptouchPlugin.h"
#import "smartConfig.h"
#import "DataTransform.h"
#import "RemoteSession.h"
#import "DeviceModel.h"
#import "ConfigClass.h"
#import "HttpManager.h"
/*
@interface EspTouchDelegateImpl : NSObject<ESPTouchDelegate>
@property (nonatomic, strong) CDVInvokedUrlCommand *command;
@property (nonatomic, weak) id <CDVCommandDelegate> commandDelegate;

@end
*/
/*
@implementation EspTouchDelegateImpl

-(void) onEsptouchResultAddedWithResult: (ESPTouchResult *) result
{
    NSString *InetAddress=[ESP_NetUtil descriptionInetAddrByData:result.ipAddrData];
    NSString *text=[NSString stringWithFormat:@"bssid=%@,InetAddress=%@",result.bssid,InetAddress];
    CDVPluginResult* pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString: text];
    [pluginResult setKeepCallbackAsBool:true];
    //[self.commandDelegate sendPluginResult:pluginResult callbackId:self.command.callbackId];  //add by lianghuiyuan
}
@end
*/


@implementation esptouchPlugin

-(void)timerEsp:(CDVInvokedUrlCommand *)command{
    timeTick++;
    if(timeTick == 60){
        [timer invalidate];
        [_configClass stopConfig];

        CDVPluginResult* pluginResult = nil;
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"timeout"];
        [pluginResult setKeepCallbackAsBool:true];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}

- (void) smartConfig:(CDVInvokedUrlCommand *)command{
    [self._condition lock];
    NSString *apSsid = (NSString *)[command.arguments objectAtIndex:0];
    NSString *apPwd = (NSString *)[command.arguments objectAtIndex:1];

    NSLog(@"ESPTouchPlugin: for Cordova by rosses");
    NSLog(@"ESPTouchPlugin: apSsid--->apPwd");
    NSLog(@"ESPTouchPlugin: %@ --> %@", apSsid, apPwd);

    [timer invalidate];
    timer = [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(timerEsp) userInfo:nil repeats:YES];
    
    //[self.commandDelegate runInBackground:^{
        _configClass = [[ConfigClass alloc] init];  
        _configClass.delegate = self; 
        NSLog(@"ESPTouchPlugin: starConfigWithWifiName");
        [_configClass starConfigWithWifiName:apSsid andWifiPsw:apPwd andUserMarking: @"3517" andOrderMarking:@"" andDeviceName:@""];
    //}];

}

- (void) cancelConfig:(CDVInvokedUrlCommand *)command{

    [timer invalidate];
    [_configClass stopConfig];

    CDVPluginResult* pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString: @"cancel success"];
    [pluginResult setKeepCallbackAsBool:true];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

#pragma mark ------<ConfigDelegate>
//Configuration of equipment agent method of success
- (void)configSuccessWithDeviceMac:(DeviceModel *)deviceModel
{
    NSLog(@"ESPTouchPlugin: SUCCESS");
    NSString *did = deviceModel.div;
    NSString *om = deviceModel.orderMarking;
    NSString *outputString = [NSString stringWithFormat:@"%@/%@/%@", did, om,  @"finished"];

    CDVPluginResult* pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString: outputString];
    [pluginResult setKeepCallbackAsBool:true];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end