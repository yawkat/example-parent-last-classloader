package at.yawk.test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author yawkat
 */
public final class ParentMain {
    public static void main(String[] args) throws Exception {
        ChildFirstURLClassLoader plcl = new ChildFirstURLClassLoader(new URL[]{
                ParentMain.class.getProtectionDomain().getCodeSource().getLocation(),
                Paths.get("child-module/target/child-module-1.0-SNAPSHOT.jar").toUri().toURL()
        }, ParentMain.class.getClassLoader());

        Class<?> remoteApi = plcl.loadClass(Api.class.getName());
        Class<?> remoteImpl = plcl.loadClass(Impl.class.getName());
        Object remoteObject = remoteImpl.getConstructor().newInstance();

        Api localApi = (Api) Proxy.newProxyInstance(
                Api.class.getClassLoader(),
                new Class[]{ Api.class },
                (proxy, method, args1) -> {
                    Method remoteMethod = remoteApi.getMethod(method.getName(), method.getParameterTypes());
                    return remoteMethod.invoke(remoteObject, args1);
                });
        localApi.run();
    }
}
