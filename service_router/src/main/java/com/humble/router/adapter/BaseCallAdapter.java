package com.humble.router.adapter;

import com.humble.router.HumbleRouter;

import java.lang.reflect.Method;

/**
 * @author wenlong wang
 * @date 2020/11/29 15:34
 */
public abstract class BaseCallAdapter {
    protected HumbleRouter<?> router;

    public BaseCallAdapter(HumbleRouter<?> router) {
        this.router = router;
    }

    /**
     * 交给实现类处理方法的调用
     * @param method 调用方法
     * @param args   参数
     * @return 返回值
     * @throws Exception 未找到实现类
     */
    public abstract Object call(Method method, Object[] args) throws Exception;
}
