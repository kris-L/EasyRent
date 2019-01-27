package com.rent.kris.easyrent.event;

import org.greenrobot.eventbus.EventBus;

public class EventManager {

    public static void register(Object object) {
        if (!EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }

    public static void unregister(Object object) {
        if (EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
        }
    }

    public static void sendEvent(Object object) {
        EventBus.getDefault().post(object);
    }

}
