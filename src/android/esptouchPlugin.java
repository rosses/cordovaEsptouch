package com.icubespace.cordova_esptouch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.List;


//import com.espressif.iot.esptouch.EsptouchTask;
//import com.espressif.iot.esptouch.IEsptouchListener;
//import com.espressif.iot.esptouch.IEsptouchResult;
//import com.espressif.iot.esptouch.IEsptouchTask;
//import com.espressif.iot.esptouch.task.__IEsptouchTask;



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
    //EsptouchAsyncTask3 esptouchAsyncTask3;
    private TCPSetupTask mTCPSetupTask;

	@Override
    public boolean execute(String action, final JSONArray args,final CallbackContext callbackContext) throws JSONException{
        receivingCallbackContext = callbackContext;    //modified by lianghuiyuan
        if (action.equals("smartConfig")) {
            final String apSsid = args.getString(0);
            final String apBssid = args.getString(1);
            final String apPassword = args.getString(2);
            final String isSsidHiddenStr = args.getString(3);
            final String taskResultCountStr = args.getString(4);
            final int taskResultCount = Integer.parseInt(taskResultCountStr);
            final Object mLock = new Object();
            cordova.getThreadPool().execute(
            new Runnable() {
                public void run() {
                    synchronized (mLock) {
                        boolean isSsidHidden = false;
                        if (isSsidHiddenStr.equals("YES")) {
                            isSsidHidden = true;
                        }

                        //mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, isSsidHidden, cordova.getActivity());
                        mEsptouchTask = new EsptouchTask(apSsid, apPassword, null, (byte) 0x09, cordova.getActivity());
                        mEsptouchTask.setEsptouchListener(myListener);
                    }
                    List<IEsptouchResult> resultList = mEsptouchTask.executeForResults(taskResultCount);
                    IEsptouchResult firstResult = resultList.get(0);
                    if (!firstResult.isCancelled()) {
                        int count = 0;
                        final int maxDisplayCount = taskResultCount;
                        if (firstResult.isSuc()) {
                            // StringBuilder sb = new StringBuilder();
                            // for (IEsptouchResult resultInList : resultList) {
                            	// sb.append("device"+count+",bssid="
                            			// + resultInList.getBssid()
                            			// + ",InetAddress="
                            			// + resultInList.getInetAddress()
                            					// .getHostAddress() + ".");
                            	// count++;
                            	// if (count >= maxDisplayCount) {
                            		// break;
                            	// }
                            // }
                            // if (count < resultList.size()) {
                            	// sb.append("\nthere's " + (resultList.size() - count)
                            			// + " more resultList(s) without showing\n");
                            // }
                            //TCPAsyncTask3 tcpAsyncTask3;
                            BytesIO io = new BytesIO(firstResult.getUserData());
                            //jump to 18 position
                            io.getBytes(18);
                            //did is in relation ids,did is the device id
                            int did = io.getInt();
                            //jump to contnent
                            io.getBytes(18);

                            //device recovery version
                            int recoveryVersion = io.getShort();

                            //major version must be 0x09
                            int majorVersion = io.get()&0xFF;
                            //minor version must be 0x02
                            int minorVersion  =  io.get()&0xFF;

                            //the ip of the device
                            String ip = io.getIPString();

                            //the mac of the device
                            String macString = io.getMacString();

                            //the special of device ,now is useless
                            byte[] deviceSpecial = io.getBytes(8);

                            //the status of the device
                            int configFlag = io.getInt();

                            int len = io.getShort();

                            //the device status ,is lower battery
                            byte[] deviceState = io.getBytes(len);
                            
                            EGetDevice eGetDevice = new EGetDevice();
                            eGetDevice.setDid(did);
                            eGetDevice.setIp(ip);
                            eGetDevice.setDmac(macString);
                            
                            //there set the markings use fix data,you can set you own data which you custom
                            eGetDevice.setUserMarking("1");
                            eGetDevice.setOrderMarking("16");
                            eGetDevice.setDeviceName("eg003");
                            
                            //tcpAsyncTask3 = new TCPAsyncTask3(this);
                            //tcpAsyncTask3.execute(eGetDevice);
                            mTCPSetupTask = new TCPSetupTask(eGetDevice,cordova.getActivity());
                            EGetDevice eGetDeviceResult = mTCPSetupTask.executeForResult();

                            PluginResult result = new PluginResult(PluginResult.Status.OK, macString);
                            result.setKeepCallback(true);           // keep callback after this call
                            receivingCallbackContext.sendPluginResult(result);
                            //receivingCallbackContext.success("finished");
                        } else {
                            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Esptouch fail");
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