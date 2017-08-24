//
//  HttpManager.m
//  cordova-iot-button: esp8266
//
//  Copyright © 2017 - Roberto Osses - Chile. All rights reserved.
//

#import "HttpManager.h"

@implementation HttpManager


+(void)getRequestDataWithUrl:(NSString *)urlString resultBlock:(NormalBlock)block
{
    
}

+(void)postRequestDataWithUrl:(NSString *)urlString andKeyValue:(NSString *)param resultBlock:(NormalBlock)block
{
    NSURL *url = [NSURL URLWithString:urlString];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    request.HTTPMethod = @"POST";
    request.HTTPBody = [param dataUsingEncoding:NSUTF8StringEncoding];
    [request setAllHTTPHeaderFields:@{@"Content-Type":@"application/json"}];
    
    NSURLSession *session = [NSURLSession sharedSession];
    
    NSURLSessionTask *task = [session dataTaskWithRequest:request completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
        dispatch_async(dispatch_get_main_queue(), ^{
            if (!error) {
                block([NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:nil],nil);
            }else {
                block(nil,error);
            }
        });
        
    }];
    [task resume];
}

@end













