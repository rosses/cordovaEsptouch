//
//  HttpManager.h
//  EShop
//
//  Created by 林琳 on 16/10/8.
//  Copyright © 2016年 Ogemray. All rights reserved.
//

#import <Foundation/Foundation.h>


typedef void(^NormalBlock)(id result,NSError *error);

@interface HttpManager : NSObject

/** GET请求 */
+(void)getRequestDataWithUrl:(NSString *)urlString resultBlock:(NormalBlock)block;

/** POST请求 */
+(void)postRequestDataWithUrl:(NSString *)urlString andKeyValue:(NSString *)param resultBlock:(NormalBlock)block;

@end
