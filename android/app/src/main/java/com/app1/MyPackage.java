package com.app1;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by urbano on 2/7/17.
 */

public class MyPackage implements ReactPackage {

    private final MainActivity activity;

    public MyPackage(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        //Lista de ViewManager utilizada, caso você solicitando um componente visual.
        //Imagine você criando algo assim no JS
        //<View> <MyNativeComponent /> </View>
        return Collections.emptyList();
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new MyNativeModule(reactContext, activity)); //<--- Modulo criado
                                                                 // para enviar mensagens
        return modules;
    }
}
