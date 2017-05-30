package com.app1;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;


/**
 * Created by urbano on 2/20/17.
 */

public class MyNativeModule extends ReactContextBaseJavaModule {

    private final MainActivity activity;

    public MyNativeModule(ReactApplicationContext reactContext, MainActivity activity) {
        super(reactContext);
        this.activity = activity;
    }

    @Override
    public String getName() {
        return "MyNativeModule";
    }

    /*  Você está expondo essa função para o mundo React Native.
     *   Ela irá receber três parametros.
     *   Sera algo como MyNativeModule.setMessageToApp1('String_qualquer', () => { console.log('deu certo') }, () => {console.log('deu errado') });
     */
    @ReactMethod
    public void setMessageToApp1(final String message, final Callback REACT_successCallback, final Callback REACT_errorCallBack) {
        ReactInstanceManager reactInstanceManager = activity.getApplicationByIndex(0);
        WritableMap map = Arguments.createMap();
        map.putString("message", message);

        try{
            reactInstanceManager.getCurrentReactContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("ListingApp2", map);
            REACT_successCallback.invoke(true);
            return;
        } catch(Exception e){
            REACT_errorCallBack.invoke(false);
            return;
        }
    }

    /*
    * Semalhante a setMessageToApp1. Utilizada para fins didáticos.
    * */
    @ReactMethod
    public void setMessageToApp2(final String message, final Callback REACT_successCallback, final Callback REACT_errorCallBack) {
        ReactInstanceManager reactInstanceManager = activity.getApplicationByIndex(1);

        WritableMap map = Arguments.createMap();
        map.putString("message", message);

        if(this.getReactApplicationContext() == reactInstanceManager.getCurrentReactContext()){
            map.putString("error", "Não mande mensagem para você mesmo....");
            REACT_errorCallBack.invoke(map);
            return;
        }

        try{
            reactInstanceManager.getCurrentReactContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("ListingApp1", map);
            REACT_successCallback.invoke(true);
            return;
        } catch(Exception e){
            map.putString("error", e.getMessage());
            REACT_errorCallBack.invoke(map);
            return;
        }
    }
}
