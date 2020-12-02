/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.humble.router.remote;
// Declare any non-default types here with import statements

import android.os.IBinder;

import java.io.Serializable;
public interface IHostService extends android.os.IInterface {
    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends android.os.Binder implements com.humble.router.remote.IHostService {
        private static final String DESCRIPTOR = "com.humble.map.aidl.IHostService";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.humble.map.aidl.IHostService interface,
         * generating a proxy if needed.
         */
        public static com.humble.router.remote.IHostService asInterface(IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof com.humble.router.remote.IHostService))) {
                return ((com.humble.router.remote.IHostService) iin);
            }
            return new com.humble.router.remote.IHostService.Stub.Proxy(obj);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_call: {
                    data.enforceInterface(descriptor);
                    String _arg0;
                    _arg0 = data.readString();
                    Object params[] = data.readArray(getClass().getClassLoader());
                    Serializable result = this.call(_arg0, params);
                    reply.writeNoException();
                    reply.writeSerializable(result);
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements com.humble.router.remote.IHostService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                mRemote = remote;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            /**
             * Demonstrates some basic types that you can use as parameters
             * and return values in AIDL.
             * , int callBackLength, com.humble.map.base.IRemoteCallback callBacks[]
             */
            @Override
            public Serializable call(String methodName, Object[] params) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                Serializable result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(methodName);
                    _data.writeArray(params);
                    mRemote.transact(Stub.TRANSACTION_call, _data, _reply, 0);
                    _reply.readException();
                    result = _reply.readSerializable();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return result;
            }
        }

        static final int TRANSACTION_call = (IBinder.FIRST_CALL_TRANSACTION + 0);
    }

    /**
     * 感觉能优化成序列化和binder共同使用的
     */
    Serializable call(String methodName, Object[] params) throws android.os.RemoteException;
}
