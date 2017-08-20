//
//  smartConfig.h
//  smartConfig
//
//  Created by sunrun on 14-8-27.
//  Copyright (c) 2014年 sunrun. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface smartConfig : NSObject
//停止配置
- (void)StopSmartConfig;

//开始配置
- (int )StartSmartConfigSetSSID:(NSString *)SSID andSetPassWord:(NSString *)Password andTokenString:(NSString *)tokenString;

@end
