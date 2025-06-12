package org.khr.proxydemo;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理工厂类，用于生成接口的代理对象
 */
public class ServiceProxyFactory {


    @SuppressWarnings("unchecked")
    public static <T> T getProxyByJavassist(Class<T> targetClass) throws Exception {
        ClassPool pool = ClassPool.getDefault();

        boolean isInterface = targetClass.isInterface();
        String proxyClassName = targetClass.getName() + (isInterface ? "JavassistProxyImpl" : "JavassistProxySubclass");

        CtClass proxyClass = pool.makeClass(proxyClassName);

        if (isInterface) {
            // 代理接口：实现接口
            proxyClass.addInterface(pool.get(targetClass.getName()));

            // 给接口的每个方法生成一个简单实现
            for (Method method : targetClass.getMethods()) {
                String methodName = method.getName();
                CtClass returnType = pool.get(method.getReturnType().getName());

                CtClass[] paramTypes = new CtClass[method.getParameterCount()];
                for (int i = 0; i < paramTypes.length; i++) {
                    paramTypes[i] = pool.get(method.getParameterTypes()[i].getName());
                }

                CtMethod ctMethod = new CtMethod(returnType, methodName, paramTypes, proxyClass);

                StringBuilder methodBody = new StringBuilder();
                methodBody.append("{\n");
                methodBody.append("  System.out.println(\"[Proxy] 调用接口方法: " + methodName + "\");\n");

                if (!returnType.getName().equals("void")) {
                    if (returnType.isPrimitive()) {
                        if ("boolean".equals(returnType.getName())) {
                            methodBody.append("  return false;\n");
                        } else if ("char".equals(returnType.getName())) {
                            methodBody.append("  return '\\0';\n");
                        } else {
                            methodBody.append("  return 0;\n");
                        }
                    } else if ("java.lang.String".equals(returnType.getName())) {
                        methodBody.append("  return \"hello\" + $1;\n");
                    } else {
                        methodBody.append("  return null;\n");
                    }
                }
                methodBody.append("}");
                ctMethod.setBody(methodBody.toString());
                proxyClass.addMethod(ctMethod);
            }
        } else {
            // 代理类：继承类，重写方法

            CtClass superClass = pool.get(targetClass.getName());
            proxyClass.setSuperclass(superClass);

            // 重写所有非final非static且可见方法
            for (Method method : targetClass.getMethods()) {
                int modifiers = method.getModifiers();
                // 忽略 static、final、private 方法
                if (java.lang.reflect.Modifier.isStatic(modifiers) || java.lang.reflect.Modifier.isFinal(modifiers) ||
                    java.lang.reflect.Modifier.isPrivate(modifiers)) {
                    continue;
                }

                String methodName = method.getName();
                CtClass returnType = pool.get(method.getReturnType().getName());

                CtClass[] paramTypes = new CtClass[method.getParameterCount()];
                for (int i = 0; i < paramTypes.length; i++) {
                    paramTypes[i] = pool.get(method.getParameterTypes()[i].getName());
                }

                CtMethod ctMethod = new CtMethod(returnType, methodName, paramTypes, proxyClass);
                ctMethod.setModifiers(Modifier.PUBLIC);

                // 构建调用父类方法的代码，并添加打印
                StringBuilder methodBody = new StringBuilder();
                methodBody.append("{\n");
                methodBody.append("  System.out.println(\"[Proxy] 调用父类方法: " + methodName + "\");\n");

                // 返回值处理
                if (returnType.getName().equals("void")) {
                    methodBody.append("  super." + methodName + "($$);\n");
                } else {
                    methodBody.append("  return super." + methodName + "($$);\n");
                }

                methodBody.append("}");
                ctMethod.setBody(methodBody.toString());

                proxyClass.addMethod(ctMethod);
            }
        }

        // 加载生成的代理类
        Class<?> proxyClazz = proxyClass.toClass();

        // 创建实例（默认使用无参构造器）
        return (T) proxyClazz.getDeclaredConstructor().newInstance();
    }


    @SuppressWarnings("unchecked")
    public static <T> T getProxyByJDK(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
            interfaceClass.getClassLoader(),
            new Class[]{interfaceClass},
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return "通过代理啦...***  hello" + args[0]; // 模拟返回值
                }
            }
        );
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProxyByCGLIB(Class<T> interfaceClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(interfaceClass);  // 设置代理的目标类
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("[CGLIB代理] 方法调用前: " + method.getName());
                Object result = proxy.invokeSuper(obj, args); // 调用目标类的方法
                System.out.println("[CGLIB代理] 方法调用后: " + method.getName());
                return "hello" + args[0];
            }
        });
        return (T) enhancer.create();
    }

}
