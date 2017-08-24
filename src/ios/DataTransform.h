//
//  DataTransform.h
//  cordova-iot-button: esp8266
//
//  Copyright © 2017 - Roberto Osses - Chile. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface DataTransform : NSObject

- (NSData *)little_intToByteWithData:(int)i andLength:(int)len;

- (int)little_bytesToInt:(NSData *)bytes;

- (NSData *)little_longToBytes:(long long)l;

-(long long)little_bytesToLong:(NSData *)bytes;

- (NSString *)decimalTOBinary:(uint16_t)tmpid backLength:(int)length;

-(short)little_bytesToShort:(NSData *)bytes;

- (NSString *) utf8ToUnicode:(NSString *)string;

- (NSString*) unicodeToUtf8:(NSString*)aUnicodeString;

@end
