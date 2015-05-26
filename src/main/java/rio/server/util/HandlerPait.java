package rio.server.util;

import java.lang.reflect.Method;

public class HandlerPait {

    private Object classObject;
    private Method method;

    public HandlerPait(Object classObject, Method method) {
        this.classObject = classObject;
        this.method = method;
    }

    public Object getClassObject() {
        return classObject;
    }

    public void setClassObject(Object classObject) {
        this.classObject = classObject;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
