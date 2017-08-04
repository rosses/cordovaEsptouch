# abastible-cordova

## Installation

##### Install abastible plugin
`$ cordova plugin add ../abastible-cordova --nofetch --save`
Point to the folder where the plugin is installed. For example `../abastible-cordova`

Can be removed by doing: `cordova plugin remove com.abastible.cordova --nofetch --save`
##### Important before using
- Make sure your Ionic app has permission to use the microphone
- If permission to use the microphone is not given nothing will happen when the method is called.
- It is expected that the creator of the Ionic app checks if the user as permission before making a measurement.
- Suggestion for this is to use the [Diagnostics](https://ionicframework.com/docs/native/diagnostic/, "Ionic Native - Diagnostics") plugin

##### Optional Ionic 2: Setting up the plugin inside the application
1. Add a `declaration.d.ts` file to the `src` folder in your Ionic project folder.
2. Add this line to this file:
```
declare namespace Abastible {
function startMeasurement(weight, tarra, type, id, full, success: (result: any) => void, error: (error: any) => void);
}
```
This tells the project that the Method startMeasument from Abastible is able to be used.
3. Now the plugin is ready to be used and Abastible.startMeasurement can be called.

### Supported platforms
iOS
Android
