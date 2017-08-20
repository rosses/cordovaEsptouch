//
//  DeviceModel.m
//  EShop
//
//  Created by 林琳 on 2016/10/17.
//  Copyright © 2016年 Ogemray. All rights reserved.
//

#import "DeviceModel.h"
#import "RemoteSession.h"


@implementation DeviceModel

+(DeviceModel *)modelWithDic:(NSDictionary *)dic
{
    DeviceModel *device = [[DeviceModel alloc]init];
    device.FirewareMark = [dic objectForKey:@"FirewareMark"];
    device.companySerialNum = [dic objectForKey:@"companySerialNum"];
    device.companyType = [dic objectForKey:@"companyType"];
    device.customModel = [dic objectForKey:@"customModel"];
    device.deviceModel = [dic objectForKey:@"deviceModel"];
    device.deviceName = [dic objectForKey:@"deviceName"];
    device.deviceType = [dic objectForKey:@"deviceType"];
    device.did = [dic objectForKey:@"did"];
    device.dmac = [dic objectForKey:@"dmac"];
    device.firmwareVersion = [dic objectForKey:@"firmwareVersion"];
    device.goodsName = [dic objectForKey:@"goodsName"];
    device.goodsPictureURL = [NSString stringWithFormat:@"%@%@",ServerAddress,[dic objectForKey:@"goodsPictureURL"]];
    device.goodsPrice = [dic objectForKey:@"goodsPrice"];
    device.isLowPower = [dic objectForKey:@"isLowPower"];
    device.orderMarking = [dic objectForKey:@"orderMarking"];
    device.protocolVersion = [dic objectForKey:@"protocolVersion"];
    
    [RemoteSession shareRemoteSession].companySerialNum = device.companySerialNum;
    [RemoteSession shareRemoteSession].companyType = device.companyType;
    [RemoteSession shareRemoteSession].customModel = device.customModel;
    [RemoteSession shareRemoteSession].deviceModel = device.deviceModel;
    
    return device;
}

@end
