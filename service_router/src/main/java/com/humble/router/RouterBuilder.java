package com.humble.router;

import android.content.Context;

/**
 * @author wenlong wang
 * @date 2020/11/28 17:45
 */
public class RouterBuilder<T> {
    /**
     * 远程调用时候使用authorities
     */
    String mAuthorities;
    /**
     * 接口class
     */
    Class<T> mInterfaceClazz;
    /**
     * 远程调用时候使用 通过context查询provider
     */
    Context mContext;

    public static <T> RouterBuilder<T> create(Class<T> clazz) {
        RouterBuilder<T> routerBuilder = new RouterBuilder<>(clazz);
        return routerBuilder;
    }

    private RouterBuilder(Class<T> clazz) {
        this.mInterfaceClazz = clazz;
    }

    public RouterBuilder<T> authorities(String authorities) {
        this.mAuthorities = authorities;
        return this;
    }

    public RouterBuilder<T> context(Context context) {
        this.mContext = context;
        return this;
    }

    public HumbleRouter<T> build() {
        HumbleRouter<T> router = new HumbleRouter<>(this);
        return router;
    }
}
