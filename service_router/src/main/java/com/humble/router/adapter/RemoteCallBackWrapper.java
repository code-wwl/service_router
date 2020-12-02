package com.humble.router.adapter;

import android.os.RemoteException;

import com.humble.router.remote.IRemoteCallback;
import com.humble.router.remote.IRemoteInterface;

/**
 * @author wenlong wang
 * @date 2020/11/29 16:08
 */
public class RemoteCallBackWrapper extends IRemoteInterface.Stub {
    IRemoteCallback callback;

    public RemoteCallBackWrapper(IRemoteCallback callback) {
        this.callback = callback;
    }

    @Override
    public void callBack(Object... params) throws RemoteException {
        this.callback.call(params);
    }
}
