package com.icubespace.cordova_esptouch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.List;

import com.ogemray.smartcofig_tcp.model.EGetDevice;
import com.ogemray.smartcofig_tcp.task.TCPSetupTask;
import com.ogemray.smartconfig4.EsptouchTask;
import com.ogemray.smartconfig4.IEsptouchListener;
import com.ogemray.smartconfig4.IEsptouchResult;
import com.ogemray.smartconfig4.IEsptouchTask;
import com.ogemray.smartconfig4.task.__IEsptouchTask;
import com.ogemray.smartconfig4.util.BytesUtil;
import com.ogemray.smartconfig4demo.utils.BytesIO;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class esptouchPlugin extends CordovaPlugin {
	
	CallbackContext receivingCallbackContext = null;
	IEsptouchTask mEsptouchTask;
    private TCPSetupTask mTCPSetupTask;

	@Override
    public boolean execute(String action, final JSONArray args,final CallbackContext callbackContext) throws JSONException{
        receivingCallbackContext = callbackContext;    
        if (action.equals("smartConfig")) {
            final String apSsid = args.getString(0);
            final String apPassword = args.getString(1);
            final Object mLock = new Object();
            cordova.getThreadPool().execute(
            new Runnable() {
                public void run() {
                    synchronized (mLock) {
                        mEsptouchTask = new EsptouchTask(apSsid, apPassword, null, (byte) 0x09, cordova.getActivity());
                        mEsptouchTask.setEsptouchListener(myListener);
                    }
                    List<IEsptouchResult> resultList = mEsptouchTask.executeForResults(taskResultCount);
                    IEsptouchResult firstResult = resultList.get(0);
                    if (!firstResult.isCancelled()) {
                        int count = 0;
                        final int maxDisplayCount = taskResultCount;
                        if (firstResult.isSuc()) {

                            BytesIO io = new BytesIO(firstResult.getUserData());
                            
                            io.getBytes(18);
                            int did = io.getInt();

                            io.getBytes(18);
                            int recoveryVersion = io.getShort();

                            int majorVersion = io.get()&0xFF;
                            int minorVersion  =  io.get()&0xFF;

                            String ip = io.getIPString();

                            String macString = io.getMacString();

                            byte[] deviceSpecial = io.getBytes(8);

                            int configFlag = io.getInt();

                            int len = io.getShort();

                            //Estado, para ver por ejemplo bateria baja
                            byte[] deviceState = io.getBytes(len);
                            
                            EGetDevice eGetDevice = new EGetDevice();
                            eGetDevice.setDid(did);
                            eGetDevice.setIp(ip);
                            eGetDevice.setDmac(macString);
                            
                            //Custom
                            eGetDevice.setUserMarking("1");
                            eGetDevice.setOrderMarking("GAS15N");
                            eGetDevice.setDeviceName("DASH_BUTTON");

                            JSONObject output = new JSONObject();

                            output.put("res", "OK");
                            output.put("did", did);
                            output.put("ip", ip);
                            output.put("mac", macString);

                            mTCPSetupTask = new TCPSetupTask(eGetDevice,cordova.getActivity());
                            EGetDevice eGetDeviceResult = mTCPSetupTask.executeForResult();

                            PluginResult result = new PluginResult(PluginResult.Status.OK, output.toString());
                            result.setKeepCallback(true); 
                            receivingCallbackContext.sendPluginResult(result);
                            //receivingCallbackContext.success("finished");
                        } else {
                            JSONObject output = new JSONObject();

                            output.put("res", "ERR");

                            PluginResult result = new PluginResult(PluginResult.Status.ERROR, output.toString());
                            result.setKeepCallback(true);           // keep callback after this call
                            receivingCallbackContext.sendPluginResult(result);
                        }
                    }
                }
            }//end runnable
            );
            return true;
        }
        else if (action.equals("cancelConfig")) {
            mEsptouchTask.interrupt();
            PluginResult result = new PluginResult(PluginResult.Status.OK, "cancel success");
            result.setKeepCallback(true);           // keep callback after this call
            receivingCallbackContext.sendPluginResult(result);
            return true;
        }
        else{
            callbackContext.error("can not find the function "+action);
            return false;
        }
    }

    //listener to get result
    private IEsptouchListener myListener = new IEsptouchListener() {
        @Override
        public void onEsptouchResultAdded(final IEsptouchResult result) {
            String text = "bssid="+result.toString();//+ result.getBssid()+",InetAddress="+result.getInetAddress().getHostAddress();
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, text);
            pluginResult.setKeepCallback(true);           // keep callback after this call
            //receivingCallbackContext.sendPluginResult(pluginResult);    //modified by lianghuiyuan
        }
    };



}