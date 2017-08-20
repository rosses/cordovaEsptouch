//
//  ConfigClass.h
//  SmartConfigDemo
//
//  Created by 林琳 on 2016/11/18.
//  Copyright © 2016年 Ogemray. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "DeviceModel.h"

//Equipment port
#define kDevicePort 17744

//Multicast IP and Port
#define KMulticastIp @"239.145.145.145"
#define KMulticastPort 10001


@protocol ConfigDelegate <NSObject>

@required

/**
 *  Configuration equipment successfully
 *
 *  deviceMac:  The MAC address of the equipment
 */
-(void)configSuccessWithDeviceMac:(DeviceModel *)deviceModel;

@end

@interface ConfigClass : NSObject

@property (nonatomic,assign) id <ConfigDelegate> delegate;

/**
 *  Start the configuration
 *
 *  wifiName:   WiFi name
 *  wifiPsw:    WiFi password
 */
-(void)starConfigWithWifiName:(NSString *)wifiName
                   andWifiPsw:(NSString *)wifiPsw
               andUserMarking:(NSString *)userMarking
              andOrderMarking:(NSString *)orderMarking
                andDeviceName:(NSString *)deviceName;


/** Stop configuration */
-(void)stopConfig;

@end



