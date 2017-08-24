//
//  ConfigClass.h
//  cordova-iot-button: esp8266
//
//  Copyright © 2017 - Roberto Osses - Chile. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "DeviceModel.h"

#define kDevicePort 17744

#define KMulticastIp @"127.0.0.1"
#define KMulticastPort 10001


@protocol ConfigDelegate <NSObject>

@required

/**
 *  Configuracion basada en la mac
 *
 *  deviceMac:  Mac del boton
 */
-(void)configSuccessWithDeviceMac:(DeviceModel *)deviceModel;

@end

@interface ConfigClass : NSObject

@property (nonatomic,assign) id <ConfigDelegate> delegate;

/**
 *  Funcion inicial, del SDK
 *
 *  wifiName:   WiFi name
 *  wifiPsw:    WiFi password
 */
-(void)starConfigWithWifiName:(NSString *)wifiName
                   andWifiPsw:(NSString *)wifiPsw
               andUserMarking:(NSString *)userMarking
              andOrderMarking:(NSString *)orderMarking
                andDeviceName:(NSString *)deviceName;


/** Stop */
-(void)stopConfig;

@end



