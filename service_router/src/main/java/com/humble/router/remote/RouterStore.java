package com.humble.router.remote;

import com.humble.annotation.util.Constants;

import java.util.HashMap;

/**
 * @author wenlong wang
 * @date 2020/12/1 14:29
 */
public class RouterStore {
    private HashMap<String, String> serviceMap = new HashMap<>();

    private static class LazyHolder {
        private static RouterStore INSTANCE = new RouterStore();
    }

    public static RouterStore getInstance() {
        return LazyHolder.INSTANCE;
    }

    private RouterStore() {
    }

    public void register(String key, String value) {
        //通过反射 注册进来
        serviceMap.put(key, value);
    }

    public void init() {
        try {
            Class clazz = Class.forName(Constants.SERVICE_LOADER_INIT);
            clazz.getMethod(Constants.INIT_METHOD).invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getServiceImplName(String key) {
        return serviceMap.get(key);
    }
}
