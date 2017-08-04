//
// SCListener 1.0.1
// http://github.com/stephencelis/sc_listener
//
// (c) 2009-* Stephen Celis, <stephen@stephencelis.com>.
// Released under the MIT License.
//

#import <Foundation/Foundation.h>
#import <AudioToolbox/AudioQueue.h>
#import <AudioToolbox/AudioServices.h>
#import "kiss_fftr.h"


// FFT size, should be power of 2,3,4,5
#define kFFTSIZE                        16384L

// Buffer of input must be <= kFFTSIZE
#define kBUFFERSIZE                     130712L

#define DISPLAY_FREQ                    10
#define NR_OF_DISPLAYED_FREQ            (kFFTSIZE/(2* DISPLAY_FREQ))
// Hardware sample rate
#define kSAMPLERATE                     11025

#define PEAK_FOUND_ERROR        -1
#define CAL_ERROR               -2
#define FINAL_STEP_ERROR        -3
#define LEVEL_STEP_ERROR        -4
#define FOUND_LEVEL_ERROR       -5
#define FFT_ERROR               -6

@interface SCListener : NSObject{
    @public
	double freq_db[kFFTSIZE/2];
    
    @protected
	AudioQueueLevelMeterState *levels;
	AudioQueueRef queue;
	AudioStreamBasicDescription format;
	Float64 sampleRate;

	// Audio Buffer
	short audio_data[kBUFFERSIZE];
    short outputBuf[kBUFFERSIZE + 10];
//    short outputTmpBuf[kBUFFERSIZE];
	UInt32 audio_data_len;
    UInt32 outputLen;
    BOOL    finishedOutputBuf;
	
    double      mainFreq;
    NSInteger   mainFreqIndex;
	// Buffers for fft
	kiss_fft_scalar in_fft[kFFTSIZE];
	kiss_fft_cpx out_fft[kFFTSIZE];
    double filtered_db[kFFTSIZE/2];
    double derivative_db[kFFTSIZE/2];
    double filtered_derivative_db[kFFTSIZE/2];
    double derivative_maxLevel;
    
    double     maxDerivativeLevel;
    NSInteger  maxDerivativeIndex;
    NSInteger  levelIndex;
    double     levelValue;
    
    NSInteger  minLevelIndex;
    NSInteger  maxLevelIndex;
    double     derivativePeaks[ 1000 ] ;
    NSInteger  derivativePeaksIndices[ 1000 ] ;
    
    NSInteger peaks[kFFTSIZE/2];
    NSInteger nrOfPeaks;
    NSInteger nrOfCrossings;
	double  freq_db_harmonic[kFFTSIZE/2];
    
	double freq_henriHarmonic[NR_OF_DISPLAYED_FREQ+1];
    BOOL filterOn;

    NSInteger         finalStepIndex;
    float             finalStepLevel;
    float           finalStepAvg;

	kiss_fft_scalar in_fft_smooth[kFFTSIZE];
    
#ifdef TEST_CSV
    int fileCounter;
    int dirCounter;
    NSMutableArray * csvDirectories;
    NSMutableArray * csvFiles;
#endif

    
}

+ (SCListener *)sharedListener;

- (void)listen;
- (BOOL)isListening;
- (void)pause;
- (void)stop;

// Fetch the buffer, used for drawing graphs etc
- (double*) filtered_db;
- (double*) derivative_db;
- (double*) filtered_derivative_db;
- (NSInteger*) peaks;
- (NSInteger) nrOfPeaks;
- (NSInteger) nrOfCrossings;

- (double*) freq_db;
- (double*) freq_db_harmonic;
- (double*) freq_henriHarmonic;
- (double)      mainFreq;
- (NSInteger)   mainFreqIndex;
// Getters, frequency calculation is done in this getter, not in the audio thread.
// If you are calling it from your UI thread and you have a large FFT size then
// it will block your UI.
- (Float32)frequency;
- (Float32)averagePower;
- (Float32)peakPower;
- (AudioQueueLevelMeterState *)levels;


@property (nonatomic) BOOL              filterOn;
@property (nonatomic) BOOL              useOptimumFreq;
@property (nonatomic) NSInteger         minLevelIndex;
@property (nonatomic) NSInteger         maxLevelIndex;
@property (nonatomic) NSInteger         fivePercentLevel;

@property (nonatomic) NSInteger finalStartIndex;
@property (nonatomic) NSInteger finalEndIndex;
@property (nonatomic) Float32 finalLevel;
@property (nonatomic) Float32 finalWarningLevel;
@property (nonatomic) Float32 warningLevel;

@property (nonatomic) NSInteger         first_level;
@property (nonatomic) NSInteger         second_level;
@property (nonatomic) NSInteger         third_level;
@property (nonatomic) Float32           firstPartMax;
@property (nonatomic) Float32           secPartMax;
@property (nonatomic) Float32           thirdPartMax;

#ifdef TEST_CSV
@property (nonatomic) int nrOfMeasurements;
@property (nonatomic) int nrOfFails;
@property (nonatomic) int nrOfCorrect;
@property (nonatomic) int nrOfErrors;
@property (nonatomic) int setupValue;
@property (nonatomic) int finalFile;
#endif


@end
