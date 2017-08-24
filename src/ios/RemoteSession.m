//
//  RemoteSession.m
//  cordova-iot-button: esp8266
//
//  Copyright © 2017 - Roberto Osses - Chile. All rights reserved.
//

#import "RemoteSession.h"

static RemoteSession *remoteSessionInstance=nil;

@implementation RemoteSession

+(RemoteSession *)shareRemoteSession
{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        remoteSessionInstance=[[self alloc]init];
    });
    return remoteSessionInstance;
}



@end
