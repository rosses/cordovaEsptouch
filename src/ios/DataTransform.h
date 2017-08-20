//
//  DataTransform.h
//  CustomSwitch
//
//  Created by 林琳 on 16/8/12.
//  Copyright © 2016年 Ogemray. All rights reserved.
//

#import <Foundation/Foundation.h>


//数据转换类-->主要用于Byte和各种数据类型之间的转换

@interface DataTransform : NSObject


/**
 *  将int型转换为Byte型（NSData）
 */
- (NSData *)little_intToByteWithData:(int)i andLength:(int)len;

/**
 *  将Bytes型转为int型
 */
- (int)little_bytesToInt:(NSData *)bytes;

/**
 *  将long型转换为Byte型（NSData）
 */
- (NSData *)little_longToBytes:(long long)l;
/**
 *  将（NSData）型转换为（long long）型
 */
-(long long)little_bytesToLong:(NSData *)bytes;

/**
 *  转换成二进制字符串
 */
- (NSString *)decimalTOBinary:(uint16_t)tmpid backLength:(int)length;


/*
 *  Byte转short
 */
-(short)little_bytesToShort:(NSData *)bytes;

//UTF-8转Unicode
- (NSString *) utf8ToUnicode:(NSString *)string;

//Unicode转UTF-8
- (NSString*) unicodeToUtf8:(NSString*)aUnicodeString;

@end
