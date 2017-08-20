//
//  DataTransform.m
//  CustomSwitch
//
//  Created by 林琳 on 16/8/12.
//  Copyright © 2016年 Ogemray. All rights reserved.
//

#import "DataTransform.h"

@implementation DataTransform

/**
 *  将int型转换为Byte型（NSData）
 */
- (NSData *)little_intToByteWithData:(int)i andLength:(int)len{
    Byte abyte[len];
    if (len == 1) {
        abyte[0] = (Byte) (0xff & i);
    } else if (len == 2) {
        abyte[0] = (Byte) (0xff & i);
        abyte[1] = (Byte) ((0xff00 & i) >> 8);
    } else {
        abyte[0] = (Byte) (0xff & i);
        abyte[1] = (Byte) ((0xff00 & i) >> 8);
        abyte[2] = (Byte) ((0xff0000 & i) >> 16);
        abyte[3] = (Byte) ((0xff000000 & i) >> 24);
    }
    NSData *adata = [NSData dataWithBytes:abyte length:len];
    return adata;
}

/**
 *  将Bytes型转为int型
 */
-(int)little_bytesToInt:(NSData *)bytes {
    int addr = 0;
    Byte *abyte = (Byte *)[bytes bytes];
    if (bytes.length == 1) {
        addr = abyte[0] & 0xFF;
    } else if (bytes.length == 2) {
        addr = abyte[0] & 0xFF;
        addr |= (((int) abyte[1] << 8) & 0xFF00);
    } else {
        addr = abyte[0] & 0xFF;
        addr |= (((int) abyte[1] << 8) & 0xFF00);
        addr |= (((int) abyte[2] << 16) & 0xFF0000);
        addr |= (((int) abyte[3] << 24) & 0xFF000000);
    }
    return addr;
}

/*
 *  Byte转short
 */
-(short)little_bytesToShort:(NSData *)bytes
{
    short addr = 0;
    Byte *abyte = (Byte *)[bytes bytes];
    if (bytes.length == 1) {
        addr = (short)abyte[0] & 0xFF;
    } else if (bytes.length == 2) {
        addr = (short)abyte[0] & 0xFF;
        addr |= (((short) abyte[1] << 8) & 0xFF00);
    } else {
        addr = abyte[0] & 0xFF;
        addr |= (((short) abyte[1] << 8) & 0xFF00);
        addr |= (((short) abyte[2] << 16) & 0xFF0000);
        addr |= (((short) abyte[3] << 24) & 0xFF000000);
    }
    return addr;
}


/**
 *  将long型转换为Byte型（NSData）
 */
- (NSData *)little_longToBytes:(long long)l
{
    long long temp = l;
    Byte b[8];
    for (int i = 0; i < 8; i++) {
        b[i] = (temp & 0xff);
        //将最低位保存在最低位
        temp = temp >> 8; // 向右移8位
    }
    NSData *data = [NSData dataWithBytes:b length:8];
    
    return data;
}


/**
 * 将byte(NSData)转换为long long类型
 */
-(long long)little_bytesToLong:(NSData *)bytes {
    long long addr = 0;
    Byte *abyte = (Byte *)[bytes bytes];
    if (bytes.length == 1) {
        addr = abyte[0] & 0xFF;
    } else if (bytes.length == 2) {
        addr = abyte[0] & 0xFF;
        addr |= (((long long) abyte[1] << 8) & 0xFF00);
    } else {
        addr = abyte[0] & 0xFF;
        addr |= (((long long) abyte[1] << 8)  & 0xFF00);
        addr |= (((long long) abyte[2] << 16) & 0xFF0000);
        addr |= (((long long) abyte[3] << 24) & 0xFF000000);
        addr |= (((long long) abyte[4] << 32) & 0xFF00000000);
        addr |= (((long long) abyte[5] << 40) & 0xFF0000000000);
        addr |= (((long long) abyte[6] << 48) & 0xFF000000000000);
        addr |= (((long long) abyte[7] << 56) & 0xFF00000000000000);
    }
    return addr;
}


/**
 *  转换成二进制字符串
 */
-(NSString *)decimalTOBinary:(uint16_t)tmpid backLength:(int)length
{
    NSString *a = @"";
    while (tmpid)
    {
        a = [[NSString stringWithFormat:@"%d",tmpid%2] stringByAppendingString:a];
        if (tmpid/2 < 1)
        {
            break;
        }
        tmpid = tmpid/2 ;
    }
    
    if (a.length <= length)
    {
        NSMutableString *b = [[NSMutableString alloc]init];;
        for (int i = 0; i < length - a.length; i++)
        {
            [b appendString:@"0"];
        }
        
        a = [b stringByAppendingString:a];
    }
    
    return a;
    
}

//UTF-8转Unicode
-(NSString *) utf8ToUnicode:(NSString *)string
{
    NSUInteger length = [string length];
    NSMutableString *s = [NSMutableString stringWithCapacity:0];
    for (int i = 0;i < length; i++){
        unichar _char = [string characterAtIndex:i];
        //判断是否为英文和数字
        if (_char <= '9' && _char >= '0'){
            [s appendFormat:@"%@",[string substringWithRange:NSMakeRange(i, 1)]];
        }
        else if(_char >= 'a' && _char <= 'z'){
            [s appendFormat:@"%@",[string substringWithRange:NSMakeRange(i, 1)]];
        }
        else if(_char >= 'A' && _char <= 'Z'){
            [s appendFormat:@"%@",[string substringWithRange:NSMakeRange(i, 1)]];
        }
        else{
            [s appendFormat:@"\\u%x",[string characterAtIndex:i]];
        }
    }
    return s;
}

//Unicode转UTF-8
- (NSString*) replaceUnicode:(NSString*)aUnicodeString
{
    NSString *tempStr1 = [aUnicodeString stringByReplacingOccurrencesOfString:@"\\u" withString:@"\\U"];
    NSString *tempStr2 = [tempStr1 stringByReplacingOccurrencesOfString:@"\"" withString:@"\\\""];
    NSString *tempStr3 = [[@"\"" stringByAppendingString:tempStr2] stringByAppendingString:@"\""];
    NSData *tempData = [tempStr3 dataUsingEncoding:NSUTF8StringEncoding];
    NSString* returnStr = [NSPropertyListSerialization propertyListFromData:tempData  mutabilityOption:NSPropertyListImmutable format:NULL errorDescription:NULL];
    
    return [returnStr stringByReplacingOccurrencesOfString:@"\\r\\n" withString:@"\n"];
}


@end
