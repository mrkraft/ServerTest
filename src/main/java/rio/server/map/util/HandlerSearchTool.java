package rio.server.map.util;

import org.reflections.Reflections;
import rio.server.handler.UriHandlerBased;
import rio.server.map.Mapped;
import rio.server.map.MappedMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class HandlerSearchTool {

    public static Map<String, Method> getHandlerMethods(String packageName) {
        Map<String, Method> result = new HashMap<String, Method>();

        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends UriHandlerBased>> classes = reflections.getSubTypesOf(UriHandlerBased.class);

        for (Class clazz : classes.toArray(new Class[classes.size()])) {
            Annotation classAnnotation = clazz.getAnnotation(Mapped.class);
            if (classAnnotation != null) {

                for (Method method : clazz.getMethods()) {
                    Annotation methodAnnotation = method.getAnnotation(MappedMethod.class);
                    if (methodAnnotation != null) {
                        String key = ((Mapped) classAnnotation).uri() + "/" + ((MappedMethod) methodAnnotation).type();
                        method.setAccessible(true);
                        result.put(key, method);
                    }
                }
            }
        }

        return result;
    }
}
