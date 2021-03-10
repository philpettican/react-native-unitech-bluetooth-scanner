package com.unitech.bluetooth.scanner;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class UnitechBluetoothScannerModule extends ReactContextBaseJavaModule {

    private static final String REACT_CLASS = "UnitechBluetoothScanner";
    public final ReactApplicationContext reactContext;

    UnitechBluetoothScannerModule(ReactApplicationContext reactContext) {
      super(reactContext);
      this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void multiply(int a, int b, Promise promise) {
      promise.resolve(a * b);
    }
}

