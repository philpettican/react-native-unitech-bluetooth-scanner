package com.unitech.bluetooth.scanner;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.unitech.scanner.utility.IMyAidlInterface_IsMainActiviyRunning;

import java.util.Arrays;

public class UnitechBluetoothScannerModule extends ReactContextBaseJavaModule {

  private static final String TAG = "UnitechBluetoothScanner";
  public final ReactApplicationContext reactContext;
  private BluetoothManager bluetoothManager;

  private IMyAidlInterface_IsMainActiviyRunning myAidlInterface_isMainActiviyRunning;
  private ServiceConnection mConnection = null;
  final String ACTION = "com.unitech.scanner.utility.IMainActivityRunning";

  private BroadcastReceiver mReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (ScanManager.API_DATAALL.equals(action)) {
        byte[] dataByte = intent.getByteArrayExtra("databyte");
        String barcode = Arrays.toString(dataByte);
        Log.d(TAG, "API_DATAALL databyte: " + barcode);

        int length = intent.getIntExtra("databytelength", 0);
        Log.d(TAG, "API_DATAALL databytelength: " + length);

        WritableMap map = new WritableNativeMap();
        map.putString("barcode", barcode);
        map.putInt("length", length);
        emitEvent("scanner-barcode", map);
        return;
      }
    }
  };

  private void emitEvent(String eventName, WritableMap map) {
    try{
      this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit(eventName, map);
    }
    catch(Exception e){
      Log.d(TAG,"Exception in sendEvent:" + e.toString());
    }
  }

  private final LifecycleEventListener lifecycleEventListener = new LifecycleEventListener() {
    @Override
    public void onHostResume() {
      Log.d(TAG, "onHostResume");
      IntentFilter filter = new IntentFilter();
      filter.addAction(BluetoothManager.API_GET_PAIRING_BARCODE_REPLY);
      filter.addAction(BluetoothManager.API_GET_TARGET_SCANNER_STATUS_REPLY);
      filter.addAction(BluetoothManager.API_GET_CONNECTED_SCANNERS_REPLY);
      filter.addAction(BluetoothManager.API_GET_SCANNER_SERIAL_NUMBER_REPLY);
      filter.addAction(BluetoothManager.API_GET_SCANNER_BLUETOOTH_NAME_REPLY);
      filter.addAction(BluetoothManager.API_GET_SCANNER_BLUETOOTH_MAC_REPLY);
      filter.addAction(BluetoothManager.API_GET_SCANNER_FIRMWARE_VERSION_REPLY);
      filter.addAction(BluetoothManager.API_GET_BATTERY_REPLY);
      filter.addAction(BluetoothManager.API_GET_TRIG_REPLY);
      filter.addAction(BluetoothManager.API_GET_DATA_ACK_REPLY);
      filter.addAction(BluetoothManager.API_GET_AUTO_CONNECTION_REPLY);
      filter.addAction(BluetoothManager.API_GET_CONFIG_REPLY);
      filter.addAction(ScanManager.API_DATA);
      filter.addAction(ScanManager.API_DATALENGTH);
      filter.addAction(ScanManager.API_DATABYTE);
      filter.addAction(ScanManager.API_DATABYTELENGTH);
      filter.addAction(ScanManager.API_DATAALL);
      filter.addAction(BluetoothManager.API_GET_BLUETOOTH_SIGNAL_CHECKING_LEVEL_REPLY);
      filter.addAction(BluetoothManager.API_DATA_CODETYPE);
      filter.addAction(BluetoothManager.API_GET_FORMAT_REPLY);
      filter.addAction(BluetoothManager.API_TARGET_SCANNER);
      reactContext.getCurrentActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public void onHostPause() {
      Log.d(TAG, "onHostPause");
      reactContext.getCurrentActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void onHostDestroy() {
      Log.d(TAG, "onHostDestroy");
      stopUSU();
    }
  };

    UnitechBluetoothScannerModule(ReactApplicationContext reactContext) {
      super(reactContext);
      this.reactContext = reactContext;
      Activity currentActivity = reactContext.getCurrentActivity();
      bluetoothManager = new BluetoothManager(currentActivity);
      this.reactContext.addLifecycleEventListener(lifecycleEventListener);

      mConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
          // Following the example above for an AIDL interface,
          // this gets an instance of the IRemoteInterface, which we can use to call on the service
          myAidlInterface_isMainActiviyRunning = IMyAidlInterface_IsMainActiviyRunning.Stub.asInterface(service);
          // We want to monitor the service for as long as we are
          // connected to it.
          startUSU();
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
          Log.e(TAG, "Service has unexpectedly disconnected");
          myAidlInterface_isMainActiviyRunning = null;
        }
      };

      Intent intent = new Intent();
      intent.setAction(ACTION);
      intent.setPackage("com.unitech.scanner.utility");
      getReactApplicationContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

      //startUSU();
    }

    public void startUSU(){
      if(myAidlInterface_isMainActiviyRunning!=null)
      {
        boolean isRunning=false;
        if (myAidlInterface_isMainActiviyRunning.asBinder().isBinderAlive())
          try {
            isRunning=myAidlInterface_isMainActiviyRunning.isRunning();
          } catch (RemoteException e) {
            e.printStackTrace();
          }
        if(!isRunning)
        {
          Intent sendIntent = getReactApplicationContext().getPackageManager().getLaunchIntentForPackage("com.unitech.scanner.utility");
          sendIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
          sendIntent.putExtra("ismoveTaskToBack", true);
          getReactApplicationContext().startActivity(sendIntent);
        }
      }
    }

    public void stopUSU() {
      getReactApplicationContext().unbindService(mConnection);
      Intent intent = new Intent();
      intent.setAction(BluetoothManager.API_FINISH_USU);
      getReactApplicationContext().sendBroadcast(intent);
    }

    @Override
    public String getName() {
        return "UnitechBluetoothScanner";
    }

    @ReactMethod
    public void multiply(int a, int b, Promise promise) {
      promise.resolve(a * b);
    }
}

