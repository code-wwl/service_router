package com.humble.router.adapter;

import com.humble.router.HumbleRouter;
import com.humble.router.remote.RouterStore;

import java.lang.reflect.Method;

/**
 * @author wenlong wang
 * @date 2020/11/29 15:35
 */
public class LocalCallAdapter extends BaseCallAdapter {

    private Object service;

    public LocalCallAdapter(HumbleRouter<?> router) {
        super(router);
        initService();
    }

    private void initService() {
        String className = RouterStore.getInstance().getServiceImplName(router.getServiceName());
        try {
            Class<?> clazz = Class.forName(className);
            service = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object call(Method method, Object[] args) throws Exception {
        setMethodAcc(method);
        if (service == null) {
            throw new IllegalAccessException("未找到实现类");
        }
        return method.invoke(service, args);
    }

    private void setMethodAcc(Method method) {
        if (method.isAccessible()) {
            return;
        }
        method.setAccessible(true);
    }
}
