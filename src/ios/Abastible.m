/********* Abastible.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import <AbastibleFramework/Abastible_SDK.h>
@interface Abastible : CDVPlugin
// Member variables go here.

- (void)startMeasurement:(CDVInvokedUrlCommand*)command;
@end


@implementation Abastible {
    CDVInvokedUrlCommand* resultCommand;
    CDVPluginResult* pluginResult;
}
//@implementation Abastible_SDK

- (void)startMeasurement:(CDVInvokedUrlCommand*)command {
    
    resultCommand = command;
    pluginResult = nil;
    
    NSNumber* weight = [command.arguments objectAtIndex:0];
    NSNumber* tarra = [command.arguments objectAtIndex:1];
    NSString* type = [command.arguments objectAtIndex:2];
    NSNumber* cylinderId = [command.arguments objectAtIndex:3];
    NSNumber* full = [command.arguments objectAtIndex:4];
    
    CRCilinderType ct;
    CRFillingType ft;
    
    //check weight
    if([weight integerValue] == 5) {
        ct = CRCilinderType_5kg;
    }
    else if([weight integerValue] == 11) {
        ct = CRCilinderType_11kg;
    }
    else if([weight integerValue] == 15) {
        ct = CRCilinderType_15kg;
    }
    else if([weight integerValue] == 45) {
        ct = CRCilinderType_45kg;
    }
    else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Wrong weight input!"];
    }
    
    //check tarra
    if([tarra integerValue] <= 0) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Wrong tarra. Should be higher than 0!"];
    }
    
    //check type
    if([type isEqualToString:@"butane"]) {
        ft = CRFillingType_Butane;
    }
    else if([type isEqualToString:@"propane"]) {
        ft = CRFillingType_Propane;
    }
    else {
        ft = CRFillingType_Unknown;
    }
    
    //check id
    if(cylinderId < 0) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"The id should NOT be negative!"];
    }
    
    if(pluginResult != nil) {
        [self.commandDelegate sendPluginResult:pluginResult callbackId:resultCommand.callbackId];
    }
    else {
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(measurementResult:) name:@"ABASTIBLE_MEASUREMENT_RESULT" object:nil];
        [Abastible_SDK.sharedInstance startMeasurement:ct tarra:[tarra intValue] fillingType:ft cilinderId:[cylinderId stringValue] cilinderIsFull:[full boolValue]];
    }
    
}

-(void)measurementResult:(NSNotification *)notification {
    
    NSDictionary *measurementInfo = notification.userInfo;
    NSNumber* measurementResult = [measurementInfo objectForKey:@"measurementInfo"];
    NSString* result = [NSString stringWithFormat:@"%d", (int)measurementResult.integerValue];
    
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:result];
    [[NSNotificationCenter defaultCenter] removeObserver:self];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:resultCommand.callbackId];
    
}

@end
