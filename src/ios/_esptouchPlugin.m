#import "esptouchPlugin.h"


@interface EspTouchDelegateImpl : NSObject<ESPTouchDelegate>
@property (nonatomic, strong) CDVInvokedUrlCommand *command;
@property (nonatomic, weak) id <CDVCommandDelegate> commandDelegate;

@end

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


@implementation esptouchPlugin

- (void) smartConfig:(CDVInvokedUrlCommand *)command{
    [self._condition lock];
    NSString *apSsid = (NSString *)[command.arguments objectAtIndex:0];
    NSString *apBssid = @"";
    /*NSString *apBssid = (NSString *)[command.arguments objectAtIndex:1];*/
    NSString *apPwd = (NSString *)[command.arguments objectAtIndex:1];
    /*NSString *isSsidHiddenStr=(NSString *)[command.arguments objectAtIndex:3];*/
    BOOL isSsidHidden = true;
    NSLog(@"ESPTouchPlugin: for Cordova by rosses");
    NSLog(@"ESPTouchPlugin: -------------------");
    NSString *msgStartConfig = [NSString stringWithFormat:@"ESPTouchPlugin: %@/%@/%@", apSsid, apBssid, apPwd];
    NSLog(@"%@", msgStartConfig);
    /*
    if([isSsidHiddenStr compare:@"NO"]==NSOrderedSame){
        isSsidHidden=false;
    }*/
    int taskCount = 1;//(int)[[command.arguments objectAtIndex:4] intValue];
    self._esptouchTask =
    [[ESPTouchTask alloc]initWithApSsid:apSsid andApBssid:apBssid andApPwd:apPwd andIsSsidHiden:isSsidHidden];
    EspTouchDelegateImpl *esptouchDelegate=[[EspTouchDelegateImpl alloc]init];
    esptouchDelegate.command=command;
    esptouchDelegate.commandDelegate=self.commandDelegate;
    [self._esptouchTask setEsptouchDelegate:esptouchDelegate];
    [self._condition unlock];
    NSLog(@"ESPTouchPlugin: runInBackground line");
    [self.commandDelegate runInBackground:^{
    dispatch_queue_t  queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_async(queue, ^{
        NSArray * esptouchResultArray = [self._esptouchTask executeForResults:taskCount];

       // show the result to the user in UI Main Thread
        NSString *daStr = @"imlink";
        NSLog(@"ESPTouchPlugin: queueName");
        NSLog(@"%@", daStr);

        const char *queueName = [daStr UTF8String];
        dispatch_queue_t myQueue = dispatch_queue_create(queueName, DISPATCH_QUEUE_CONCURRENT);
        dispatch_async(myQueue, ^{

            NSLog(@"ESPTouchPlugin: secondQueue");

            ESPTouchResult *firstResult = [esptouchResultArray objectAtIndex:0];
            // check whether the task is cancelled and no results received
            if (!firstResult.isCancelled)
            {
                NSMutableString *mutableStr = [[NSMutableString alloc]init];
                NSUInteger count = 0;
                // max results to be displayed, if it is more than maxDisplayCount,
                // just show the count of redundant ones
                const int maxDisplayCount = 5;
                if ([firstResult isSuc])
                {

                    /* BytesIO **/
                    //
                     
                    NSString *did = (NSString *)[firstResult bssid];
                    NSString *ip = (NSString *)[firstResult ipAddrData];
                    /*for (int i = 0; i < [esptouchResultArray count]; ++i)
                    {
                        ESPTouchResult *resultInArray = [esptouchResultArray objectAtIndex:i];
                        [mutableStr appendString:[resultInArray description]];
                        [mutableStr appendString:@"\n"];
                        count++;
                        if (count >= maxDisplayCount)
                        {
                            break;
                        }
                    }
                    if (count < [esptouchResultArray count])
                    {
                        [mutableStr appendString:[NSString stringWithFormat:@"\nthere's %lu more result(s) without showing\n",(unsigned long)([esptouchResultArray count] - count)]];
                    }
                    */

                    NSString *outputString = [NSString stringWithFormat:@"%@/%@/%@", did, ip,  @"finished"];

                    CDVPluginResult* pluginResult = nil;
                    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString: outputString];
                    [pluginResult setKeepCallbackAsBool:true];
                    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                }
                else
                {
                    NSString *outputString = [NSString stringWithFormat:@"%@/%@/%@", apSsid, apPwd,  @"fail"];
                    CDVPluginResult* pluginResult = nil;
                    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: outputString];
                    [pluginResult setKeepCallbackAsBool:true];
                    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                }
            }
        });
    });
    }];
}


- (void) cancelConfig:(CDVInvokedUrlCommand *)command{
    [self._condition lock];
    if (self._esptouchTask != nil)
    {
        [self._esptouchTask interrupt];
    }
    [self._condition unlock];
    CDVPluginResult* pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString: @"cancel success"];
    [pluginResult setKeepCallbackAsBool:true];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end