//
//  RemoteSession.h
//  cordova-iot-button: esp8266
//
//  Copyright © 2017 - Roberto Osses - Chile. All rights reserved.
//


#import <Foundation/Foundation.h>

@interface RemoteSession : NSObject

@property (nonatomic,copy) NSString *account;

@property (nonatomic,copy) NSString *psw;

@property (nonatomic,copy) NSString *userMarking;

@property (nonatomic,copy) NSString *ssToken;

@property (nonatomic,copy)NSString *companySerialNum;
@property (nonatomic,copy)NSString *companyType;
@property (nonatomic,copy)NSString *customModel;
@property (nonatomic,copy)NSString *deviceModel;


+(RemoteSession *)shareRemoteSession;

@end
