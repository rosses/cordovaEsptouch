//
//  DeviceModel.h
//  EShop
//
//  Created by 林琳 on 2016/10/17.
//  Copyright © 2016年 Ogemray. All rights reserved.
//

#import <Foundation/Foundation.h>

// 绑定设备model类

@interface DeviceModel : NSObject

/** 固件标识 */
@property (nonatomic,copy)NSString *FirewareMark;

/** 企业自编号 */
@property (nonatomic,copy)NSString *companySerialNum; //00000001

/** 企业代码 */
@property (nonatomic,copy)NSString *companyType; //00000001

/** 自定义型号 */
@property (nonatomic,copy)NSString *customModel; //00000001

/** 产品型号 */
@property (nonatomic,copy)NSString *deviceModel;

/** 设备名称 */
@property (nonatomic,copy)NSString *deviceName;

/** 设备类型 */
@property (nonatomic,copy)NSString *deviceType; //0902

/** 设备ID */
@property (nonatomic,copy)NSString *did;

/** 设备MAC地址 */
@property (nonatomic,copy)NSString *dmac;

/** 固件版本 */
@property (nonatomic,copy)NSString *firmwareVersion;

/** 设备绑定商品名字 */
@property (nonatomic,copy)NSString *goodsName;

/** 设备绑定商品图片 */
@property (nonatomic,copy)NSString *goodsPictureURL;

/** 设备绑定商品价格 */
@property (nonatomic,copy)NSString *goodsPrice;

/** 是否低电量 */
@property (nonatomic,copy)NSString *isLowPower;

/** 订单编号 */
@property (nonatomic,copy)NSString *orderMarking;

/** 协议版本 */
@property (nonatomic,copy)NSString *protocolVersion;


//设备配置时额外加的一些属性

/** 设备的重置版本号 */
@property (nonatomic,copy)NSString *resetVer;

//设备类型deviceTyoe

/** 设备IP */
@property (nonatomic,copy)NSString *deviceIp;

//MAC dmac

//产品特性

//配置状态


+(DeviceModel *)modelWithDic:(NSDictionary *)dic;

@end
