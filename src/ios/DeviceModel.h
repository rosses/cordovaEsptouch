//
//  DeviceModel.h
//  cordova-iot-button: esp8266
//
//  Copyright © 2017 - Roberto Osses - Chile. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface DeviceModel : NSObject

@property (nonatomic,copy)NSString *FirewareMark;

@property (nonatomic,copy)NSString *companySerialNum; //00000001

@property (nonatomic,copy)NSString *companyType; //00000001

@property (nonatomic,copy)NSString *customModel; //00000001

@property (nonatomic,copy)NSString *deviceModel;

@property (nonatomic,copy)NSString *deviceName;

@property (nonatomic,copy)NSString *deviceType; //0902

@property (nonatomic,copy)NSString *did;

@property (nonatomic,copy)NSString *dmac;

@property (nonatomic,copy)NSString *firmwareVersion;

@property (nonatomic,copy)NSString *goodsName;

@property (nonatomic,copy)NSString *goodsPictureURL;

@property (nonatomic,copy)NSString *goodsPrice;

@property (nonatomic,copy)NSString *isLowPower;

@property (nonatomic,copy)NSString *orderMarking;

@property (nonatomic,copy)NSString *protocolVersion;

@property (nonatomic,copy)NSString *resetVer;

@property (nonatomic,copy)NSString *deviceIp;

+(DeviceModel *)modelWithDic:(NSDictionary *)dic;

@end
