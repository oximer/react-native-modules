package com.app1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private ReactInstanceManager appReactInstanceManager;
    private ReactInstanceManager appReactInstanceManager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Criando Aplicação React Native 1
        ReactRootView app1RootView = new ReactRootView(getApplication());
        ReactInstanceManagerBuilder builder = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setDefaultHardwareBackBtnHandler(this)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .addPackage(new MainReactPackage())
                .addPackage(new MyPackage(this))    //<------ Adicionando aqui o Pacote
                                                    // que expõem seus modulos nativos.
                .setCurrentActivity(this)
                .setBundleAssetName("app1.jsbundle")
                .setJSMainModuleName("app1")
                .setUseDeveloperSupport(false);
        appReactInstanceManager = builder.build();

        app1RootView.startReactApplication(appReactInstanceManager, "app1", null);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.app1);
        linearLayout.addView(app1RootView);

        //Criando Aplicação React Native 2
        ReactRootView app2RootView = new ReactRootView(getApplication());
        builder = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setDefaultHardwareBackBtnHandler(this)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .addPackage(new MainReactPackage())
                .addPackage(new MyPackage(this))    //<------ Adicionando aqui o Pacote que expõem seus modulos nativos.
                .setCurrentActivity(this)
                .setBundleAssetName("app2.jsbundle")
                .setJSMainModuleName("app2")
                .setUseDeveloperSupport(false);
        appReactInstanceManager2 = builder.build();

        app2RootView.startReactApplication(appReactInstanceManager2, "app2", null);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.app2);
        frameLayout.addView(app2RootView);
    }

    @Override
    public void invokeDefaultOnBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        appReactInstanceManager.onHostResume(this,this);
        appReactInstanceManager2.onHostResume(this,this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        appReactInstanceManager.onHostPause();
        appReactInstanceManager2.onHostPause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        appReactInstanceManager.onHostDestroy();
        appReactInstanceManager2.onHostDestroy();
    }

    //Metodo simples para retornar os gerenciadores de instancias utilizados para iniciar as aplicações.
    public ReactInstanceManager getApplicationByIndex(int index){
        if(index > 1 || index < 0) return null;

        if(index == 0) return appReactInstanceManager;
        else return appReactInstanceManager2;
    }

}
