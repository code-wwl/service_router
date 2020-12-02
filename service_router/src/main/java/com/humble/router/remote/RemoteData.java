package com.humble.router.remote;

import android.os.IBinder;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author wenlong wang
 * @date 2020/11/30 14:50
 */
class RemoteData {
    private Object[] params;
    private Class<?> clazz;
    private Method method;
    public static HashMap<String, Class<?>> cacheClassMap = new HashMap<>();

    RemoteData(Class<?> clazz, Method method, Object... params) {
        this.clazz = clazz;
        this.method = method;
        this.params = params;
    }

    public static RemoteData createRemoteData(String classAndMethodName, Object... params) throws Exception {
        String[] array = classAndMethodName.split(Constants.Provider.KEY_SEPARATE);
        Class<?> clazz = createClass(array[0]);
        String methodName = array[1];
        convertParams(params);
        Method method = getMethod(clazz, methodName, params);
        return new RemoteData(clazz, method, params);
    }

    private static void convertParams(Object... params) {
        for (int i = 0; i < params.length; i++) {
            //远程的服务
            if (params[i] instanceof IBinder) {
                params[i] = new IRemoteCallback.Proxy(IRemoteInterface.Stub.asInterface((IBinder) params[i]));
            }
        }
    }

    private static Class<?> createClass(String className) throws Exception {
        Class<?> clazz = cacheClassMap.get(className);
        if (clazz == null) {
            String classImplName = RouterStore.getInstance().getServiceImplName(className);
            clazz = Class.forName(classImplName);
            cacheClassMap.put(className, clazz);
        }
        return clazz;
    }


    public static Method getMethod(Class<?> clazz, String methodName, Object... params) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            // TODO: 2020/11/30 需要优化
            Class<?>[] paramTypes = method.getParameterTypes();
            int paramLength = paramTypes.length;
            if (paramLength != params.length) {
                continue;
            }
            int i = 0;
            for (; i < paramLength; i++) {
                Class<?> paramClazz = params[i].getClass();
                if (paramTypes[i] == paramClazz) {
                    continue;
                }
                if (paramTypes[i].isPrimitive()) {
                    continue;
                }
                if (paramTypes[i] == IRemoteCallback.class
                        && paramClazz == IRemoteCallback.Proxy.class) {
                    continue;
                }
                break;
            }
            if (i == paramLength) {
                //缓存起来
                return method;
            }
        }
        return null;
    }

    Object invokeMethod(Object service) throws Exception {
        return method.invoke(service, params);
    }

    Object createInstance() throws Exception {
        return this.clazz.newInstance();
    }
}
