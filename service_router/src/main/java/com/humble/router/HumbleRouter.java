package com.humble.router;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.humble.router.remote.Constants;
import com.humble.router.remote.RouterStore;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wenlong wang
 * @date 2020/11/27 15:36
 * 请求处理类
 */
public class HumbleRouter<T> {
    private String authorities;
    private Class<T> clazz;
    private Context context;

    HumbleRouter(RouterBuilder<T> builder) {
        authorities = builder.mAuthorities;
        clazz = builder.mInterfaceClazz;
        context = builder.mContext;
    }

    public T create() {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{clazz},
                new ServiceInvocationHandler(this));
    }

    public String getServiceName() {
        return clazz.getName();
    }

    public String getRemoteServiceName(Method method) {
        return clazz.getName() + Constants.Provider.KEY_SEPARATE + method.getName();
    }

    public boolean isRemote() {
        return authorities != null && !authorities.isEmpty();
    }

    public Context getContext() {
        return context;
    }

    public Bundle getRemoteBundle() {
        return getContext().getContentResolver().call(Uri.parse(authorities), "", "", new Bundle());
    }

    public static void init() {
        RouterStore.getInstance().init();
    }
}
