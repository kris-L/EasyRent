package com.xw.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import timber.log.Timber;

/**
 * Created by XWCHQ on 2017/9/19-13:47
 */

public class AppMaintenance {

    private static long launcherTime = 0;
    private static OnAppRestartCallback callback;
    public static Map<String,Activity> createdActivities;
    private static int activeCount = 0;
    private static List<OnAppActiveCallback> appActiveCallbacks;

    public static long getLauncherTime() {
        return launcherTime;
    }

    private static void setLauncherTime(long launcherTime) {
        AppMaintenance.launcherTime = launcherTime;
    }

    public static void onLaunched() {
        Timber.d("onLaunched");
        setLauncherTime(System.currentTimeMillis());
    }

    public static boolean isAppAlive() {
        return getLauncherTime() != 0;
    }

    public static void restartApp(Context context) {
        Timber.d("restartApp");
        context.startActivity(IntentUtil.actionLauncher(context));
    }

    private static Application app;

    public static void registerApp(Application app) {
        registerApp(app, null);
    }

    public static void unregister(){
        if(app != null) {
            if(lifecycleCallback != null) {
                app.unregisterActivityLifecycleCallbacks(lifecycleCallback);
            }
            app = null;
        }
        if(createdActivities != null) {
            createdActivities.clear();
            createdActivities = null;
        }
    }
    private static Application.ActivityLifecycleCallbacks lifecycleCallback;
    public static void registerApp(Application app, OnAppRestartCallback onAppRestartCallback) {
        AppMaintenance.callback = onAppRestartCallback;
        lifecycleCallback = new MaintenanceActivityLifeCallback();
        app.registerActivityLifecycleCallbacks(lifecycleCallback);
        createdActivities = new HashMap<>();
    }

    public static boolean isLauncher(Activity activity) {
        String action = activity.getIntent().getAction();
        Set<String> categories = activity.getIntent().getCategories();
        if (categories == null) {
            return Intent.ACTION_MAIN.equals(action);
        }
        return Intent.ACTION_MAIN.equals(action) && categories.contains(Intent.CATEGORY_LAUNCHER);
    }

    public static void finishIfCreated(String name) {
        Activity cratedActivity = getIfCratedActivity(name);
        if(cratedActivity != null){
            cratedActivity.finish();
        }
    }

    private static class MaintenanceActivityLifeCallback implements Application.ActivityLifecycleCallbacks ,OnAppActiveCallback{

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Timber.v(activity.getLocalClassName() + ":::::: %s", "onActivityCreated");
            if (isLauncher(activity)) {
                onLaunched();
            } else if (!isAppAlive()) {
                if (callback != null) {
                    callback.beforeAppRestart();
                }
                AppMaintenance.restartApp(activity);
            }
            createdActivities.put(getClassName(activity),activity);
        }

        private String getClassName(Object obj) {
            return obj.getClass().getName();
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Timber.v(activity.getLocalClassName() + ":::::: %s", "onActivityStarted");
            if (activeCount == 0){
                onAppActive();
            }
            activeCount++;
        }

        @Override
        public void onAppActive() {
            Timber.v("App is active");
            if(appActiveCallbacks != null){
                for (OnAppActiveCallback activeCallback : appActiveCallbacks) {
                    activeCallback.onAppActive();
                }
            }
        }

        @Override
        public void onAppInactive(){
            Timber.v("App is inactive");
            if(appActiveCallbacks != null){
                for (OnAppActiveCallback activeCallback : appActiveCallbacks) {
                    activeCallback.onAppInactive();
                }
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Timber.v(activity.getLocalClassName() + ":::::: %s", "onActivityResumed");

        }

        @Override
        public void onActivityPaused(Activity activity) {
            Timber.v(activity.getLocalClassName() + ":::::: %s", "onActivityPaused");
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Timber.v(activity.getLocalClassName() + ":::::: %s", "onActivityStopped");
            activeCount--;
            if (activeCount == 0) {
//            isForground = false;
//            backgroundTimestamp = System.currentTimeMillis();
                onAppInactive();
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Timber.v(activity.getLocalClassName() + ":::::: %s", "onActivitySaveInstanceState");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Timber.v(activity.getLocalClassName() + ":::::: %s", "onActivityDestroyed");
            createdActivities.remove(getClassName(activity));
        }
    }

    public static interface OnAppRestartCallback {
        void beforeAppRestart();
    }

    public static boolean isActivityCreate(String activityClassName){
        if(createdActivities != null) {
            return createdActivities.containsKey(activityClassName);
        }else{
            return false;
        }
    }

    public static Activity getIfCratedActivity(String activityClassName){
        if(createdActivities != null) {
            return createdActivities.get(activityClassName);
        }else{
            return null;
        }
    }

    public static void addAppActiveCallback(OnAppActiveCallback callback){
        if(appActiveCallbacks == null){
            appActiveCallbacks = new ArrayList<>();
        }
        if(!appActiveCallbacks.contains(callback)) {
            appActiveCallbacks.add(callback);
        }
    }

    public static void removeAppActiveCalblack(OnAppActiveCallback callback){
        if(appActiveCallbacks != null){
            if(appActiveCallbacks.contains(callback)){
                appActiveCallbacks.remove(callback);
            }
        }
    }

    public static interface OnAppActiveCallback{
        void onAppActive();
        void onAppInactive();
    }

}
