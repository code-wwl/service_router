package com.humble.router.remote;

import android.os.RemoteException;

/**
 * @author wenlong wang
 * @date 2020/11/29 15:57
 * 回调类
 */
public interface IRemoteCallback {
    /**
     * 回调方法
     * @param object 参数
     * @throws RemoteException 远程实现类找不到
     */
    void call(Object... object) throws RemoteException;

    class Proxy implements IRemoteCallback {
        IRemoteInterface callback;

        Proxy(IRemoteInterface callback) {
            this.callback = callback;
        }

        @Override
        public void call(Object... object) throws RemoteException {
            this.callback.callBack(object);
        }
    }
}
