//
//  Cilinder_API.h
//  AbastibleSDK
//
//  Created by Henri Jurjens on 15/06/2017.
//  Copyright © 2017 Henri Jurjens. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SCListener.h"

@protocol Cilinder_API_Delegate<NSObject>
- (void) calculationFinished;
@end

// in a header
extern const int INVALID_MEASUREMENT;
extern const int MEASUREMENT_NOT_TRUSTED;
extern const int BUTANE_INDEX_OFFSET;

typedef NS_ENUM(NSUInteger, CRCilinderType) {
    CRCilinderType_5kg, // Default
    CRCilinderType_11kg,
    CRCilinderType_15kg,
    CRCilinderType_45kg
};

typedef NS_ENUM(NSUInteger, CRFillingType) {
    CRFillingType_Unknown,
    CRFillingType_Propane,
    CRFillingType_Butane,
};

@interface Abastible_SDK : NSObject
+ (Abastible_SDK *)sharedInstance;

//- (void) startMeasurement;
- (void) calculateResult;

-(void) startMeasurement:(CRCilinderType) cilinderType tarra:(NSInteger)tarraInGram fillingType:(CRFillingType)fillingType cilinderId:(NSString*)cilinderId cilinderIsFull:(BOOL)cilinderIsFull;

@property (nonatomic, weak) id<Cilinder_API_Delegate> delegate;

@end
