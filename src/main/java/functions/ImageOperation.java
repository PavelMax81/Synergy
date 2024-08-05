package functions;

import java.lang.reflect.InvocationTargetException;

public interface ImageOperation {
    float FACTOR = (float)0.5;
    float[] execute(float[] rgb) throws InvocationTargetException, IllegalAccessException;
}
