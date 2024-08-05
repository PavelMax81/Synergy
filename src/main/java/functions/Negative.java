package functions;

public class Negative implements ImageOperation{
    @Override
    public float[] execute(float[] rgb) {
        return new float[]{(float)1.0 - rgb[0], (float)1.0 - rgb[1], (float)1.0 - rgb[2]};
    }
}