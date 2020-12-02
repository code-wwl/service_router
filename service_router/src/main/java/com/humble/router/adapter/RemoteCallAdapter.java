package com.humble.router.adapter;

import android.os.Bundle;

import com.humble.router.HumbleRouter;
import com.humble.router.remote.Constants;
import com.humble.router.remote.IHostService;
import com.humble.router.remote.IRemoteCallback;

import java.lang.reflect.Method;

/**
 * @author wenlong wang
 * @date 2020/11/29 15:46
 */
public class RemoteCallAdapter extends BaseCallAdapter {
    private IHostService service;

    public RemoteCallAdapter(HumbleRouter<?> router) {
        super(router);
        initializeRemoteService();
    }

    public void initializeRemoteService() {
        Bundle bundle = router.getRemoteBundle();
        if (bundle != null) {
            service = IHostService.Stub.asInterface(bundle.getBinder(Constants.Provider.KEY_BINDER));
        }
    }


    @Override
    public Object call(Method method, Object[] args) throws Exception {
        String methodName = router.getRemoteServiceName(method);
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof IRemoteCallback) {
                args[i] = new RemoteCallBackWrapper((IRemoteCallback) args[i]);
            }
        }
        return service.call(methodName, args);
    }
}
