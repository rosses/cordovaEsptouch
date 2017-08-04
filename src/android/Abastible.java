package com.abastible.cordova;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

import com.move4mobile.abastiblesdk.CylinderApi;
import com.move4mobile.abastiblesdk.CylinderType;
import com.move4mobile.abastiblesdk.FillingType;

import android.content.Context;

import android.util.Log;

/**
 * This class echoes a string called from JavaScript.
 */
public class Abastible extends CordovaPlugin {
    private Context mContext;
    private CallbackContext cbContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        cbContext = callbackContext;

        Log.e("JSON_ARRAY", args.toString());

        if (action.equals("startMeasurement")) {
            this.startMeasurement(args.getInt(0), args.getInt(1), args.getString(2), args.getInt(3), args.getBoolean(4));
            return true;
        }
        return false;
    }

    private void startMeasurement(int weight, int tarra, String type, int id, boolean full){
        CylinderType ct;
        FillingType ft;

        //check weight
        switch (weight) {
            case 5:
                ct = CylinderType.WEIGHT_5KG;
                break;
            case 11:
                ct = CylinderType.WEIGHT_11KG;
                break;
            case 15:
                ct = CylinderType.WEIGHT_15KG;
                break;
            case 45:
                ct = CylinderType.WEIGHT_45KG;
                break;
            default:
                cbContext.error("Wrong weight input!");
                return;
        }

        //check tarra
        if(tarra <= 0){
            cbContext.error("Wrong tarra. Should be higher than 0!");
            return;
        }

        //check type
        if (type.equals("butane")){
            ft = FillingType.BUTANE;
        }
        else if(type.equals("propane")) {
            ft = FillingType.PROPANE;
        }
        else {
            cbContext.error("Wrong gas type!");
            return;
        }

        //check id
        if(id < 0){
            cbContext.error("The id should NOT be negative!");
            return;
        }

        mContext = this.cordova.getActivity();

        //Do measurement (10 seconds)
        CylinderApi.get(mContext).startMeasurement(ct, tarra, ft, id, full, new CylinderApi.OnMeasurementListener() {
            @Override
            public void onMeasurementFinished(final int i) {
                //send result back
                cbContext.success(i);
            }
        });
    }
}
