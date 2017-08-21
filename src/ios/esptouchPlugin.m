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

- (void) smartConfig:(CDVInvokedUrlCommand *)command{
    [self._condition lock];
    NSString *apSsid = (NSString *)[command.arguments objectAtIndex:0];
    NSString *apBssid = @"";
    /*NSString *apBssid = (NSString *)[command.arguments objectAtIndex:1];*/
    NSString *apPwd = (NSString *)[command.arguments objectAtIndex:1];
    /*NSString *isSsidHiddenStr=(NSString *)[command.arguments objectAtIndex:3];*/
    
    NSLog(@"ESPTouchPlugin: for Cordova by rosses");
    NSLog(@"ESPTouchPlugin: apSsid--->apPwd");
    NSLog(@"ESPTouchPlugin: %@ --> %@", apSsid, apPwd);

    self._configClass = [[ConfigClass alloc] init];  
    //self._configClass.delegate = self; 
    NSLog(@"ESPTouchPlugin: starConfigWithWifiName");
    [self._configClass starConfigWithWifiName:apSsid andWifiPsw:apPwd andUserMarking: @"3517" andOrderMarking:@"" andDeviceName:@""];
    //self._configClass.delegate
    /*NSLog(@"ESPTouchPlugin: Now runInBackground");
    [self._configClass.delegate runInBackground:^{
        NSLog(@"ESPTouchPlugin: dispatch_queue_t");
        dispatch_queue_t  queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
        dispatch_async(queue, ^{
            NSLog(@"ESPTouchPlugin: async");

            NSString *daStr = @"imlink";
            const char *queueName = [daStr UTF8String];
            NSLog(@"ESPTouchPlugin: myQueue");
            dispatch_queue_t myQueue = dispatch_queue_create(queueName, DISPATCH_QUEUE_CONCURRENT);
            //DeviceModel *deviceModel = [self._configClass.delegate];

            dispatch_async(myQueue, ^{

                //NSLog(@"ESPTouchPlugin: %@", deviceModel);
                NSString *outputString = [NSString stringWithFormat:@"%@/%@/%@", @"chao", @"ctm",  @"fail"];
                CDVPluginResult* pluginResult = nil;
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: outputString];
                [pluginResult setKeepCallbackAsBool:true];
                [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];

            });




        });
    }];
    */
    
    /*
    NSString *outputString = [NSString stringWithFormat:@"%@/%@/%@", did, ip,  @"finished"];

    CDVPluginResult* pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString: outputString];
    [pluginResult setKeepCallbackAsBool:true];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    */



}


- (void) cancelConfig:(CDVInvokedUrlCommand *)command{
    /*[self._condition lock];
    if (self._esptouchTask != nil)
    {
        [self._esptouchTask interrupt];
    }
    [self._condition unlock];
    */
    CDVPluginResult* pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString: @"cancel success"];
    [pluginResult setKeepCallbackAsBool:true];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

#pragma mark ------<ConfigDelegate>
//Configuration of equipment agent method of success
- (void)configSuccessWithDeviceMac:(DeviceModel *)deviceModel
{
    NSLog(@"ESPTouchPlugin: SUCcESS");
}

@end