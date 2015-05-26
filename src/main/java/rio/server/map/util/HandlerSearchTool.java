package rio.server.map.util;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rio.server.handler.UriHandlerBased;
import rio.server.map.Mapped;
import rio.server.map.MappedMethod;
import rio.server.util.HandlerPait;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * This is util class, that searches handler methods
 */
public class HandlerSearchTool {

    private static final Logger logger = LoggerFactory.getLogger(HandlerSearchTool.class);

    /**
     * Search all classes with Mapped annotation and all methods in this classes with
     * MappedMethod annotation and create handler map
     *
     * @param packageName search package path
     * @return map of handlers
     */
    public static Map<String, HandlerPait> getHandlerMethods(String packageName) {
        Map<String, HandlerPait> result = new HashMap<String, HandlerPait>();

        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends UriHandlerBased>> classes = reflections.getSubTypesOf(UriHandlerBased.class);

        for (Class clazz : classes.toArray(new Class[classes.size()])) {
            Annotation classAnnotation = clazz.getAnnotation(Mapped.class);
            if (classAnnotation != null) {

                Object classObject = null;
                try {
                    classObject = clazz.newInstance();
                } catch (InstantiationException e) {
                    logger.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage());
                }

                for (Method method : clazz.getMethods()) {
                    Annotation methodAnnotation = method.getAnnotation(MappedMethod.class);
                    if (methodAnnotation != null) {
                        String key = ((Mapped) classAnnotation).uri() + "/" + ((MappedMethod) methodAnnotation).type();
                        method.setAccessible(true);
                        result.put(key, new HandlerPait(classObject, method));
                    }
                }
            }
        }

        return result;
    }
}
