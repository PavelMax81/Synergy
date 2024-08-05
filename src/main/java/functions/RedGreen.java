package functions;

public class RedGreen implements ImageOperation{
    @Override
    public float[] execute(float[] rgb) {return new float[]{rgb[0], rgb[1], 0};
    }
}