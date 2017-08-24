//
//  HttpManager.h
//  cordova-iot-button: esp8266
//
//  Copyright © 2017 - Roberto Osses - Chile. All rights reserved.
//

#import <Foundation/Foundation.h>


typedef void(^NormalBlock)(id result,NSError *error);

@interface HttpManager : NSObject

+(void)getRequestDataWithUrl:(NSString *)urlString resultBlock:(NormalBlock)block;

+(void)postRequestDataWithUrl:(NSString *)urlString andKeyValue:(NSString *)param resultBlock:(NormalBlock)block;

@end
