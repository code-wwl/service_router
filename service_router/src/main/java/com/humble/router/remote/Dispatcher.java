package com.humble.router.remote;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * @author wenlong wang
 * @date 2020/11/27 15:34
 * 转发调用 调用实现类
 */
class Dispatcher {
    static HashMap<WeakReference<IHostService>, WeakReference<?>> iHostServiceMap = new HashMap<>();

    public static Serializable dispatch(WeakReference<IHostService> service, String classAndMethod, Object... params) throws Exception {
        RemoteData data = RemoteData.createRemoteData(classAndMethod, params);
        Object instance = getInstance(service, data);
        return (Serializable) data.invokeMethod(instance);
    }

    private static Object getInstance(WeakReference<IHostService> service, RemoteData data) throws Exception {
        WeakReference<?> object = iHostServiceMap.get(service);
        if (object != null) {
            return object.get();
        }
        Object instance = data.createInstance();
        iHostServiceMap.put(service, new WeakReference<>(instance));
        return instance;
    }


}
