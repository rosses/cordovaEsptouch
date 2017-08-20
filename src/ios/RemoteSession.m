//
//  RemoteSession.m
//  EShop
//
//  Created by 林琳 on 2016/10/15.
//  Copyright © 2016年 Ogemray. All rights reserved.
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
