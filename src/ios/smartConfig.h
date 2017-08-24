//
//  smartConfig.h
//  cordova-iot-button: esp8266
//
//  Copyright © 2017 - Roberto Osses - Chile. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface smartConfig : NSObject

- (void)StopSmartConfig;


- (int )StartSmartConfigSetSSID:(NSString *)SSID andSetPassWord:(NSString *)Password andTokenString:(NSString *)tokenString;

@end
