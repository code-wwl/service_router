package com.humble.router;

import com.humble.router.adapter.BaseCallAdapter;
import com.humble.router.adapter.LocalCallAdapter;
import com.humble.router.adapter.RemoteCallAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 */
class ServiceInvocationHandler implements InvocationHandler {

    private HumbleRouter<?> router;

    private BaseCallAdapter adapter;

    ServiceInvocationHandler(HumbleRouter<?> router) {
        this.router = router;
        initializeAdapter();
    }

    private void initializeAdapter() {
        if (!router.isRemote()) {
            adapter = new LocalCallAdapter(router);
        } else {
            adapter = new RemoteCallAdapter(router);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return adapter.call(method, args);
    }

}

