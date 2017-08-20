//
//  RemoteSession.h
//  EShop
//
//  Created by 林琳 on 2016/10/15.
//  Copyright © 2016年 Ogemray. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface RemoteSession : NSObject

/** 账号 */
@property (nonatomic,copy) NSString *account;
/** 密码 */
@property (nonatomic,copy) NSString *psw;

/** 用户标识 */
@property (nonatomic,copy) NSString *userMarking;
/** 手机登录令牌 */
@property (nonatomic,copy) NSString *ssToken;

@property (nonatomic,copy)NSString *companySerialNum;
@property (nonatomic,copy)NSString *companyType;
@property (nonatomic,copy)NSString *customModel;
@property (nonatomic,copy)NSString *deviceModel;


+(RemoteSession *)shareRemoteSession;

@end
