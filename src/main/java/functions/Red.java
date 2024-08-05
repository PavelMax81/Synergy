package functions;

public class Red implements ImageOperation{
    @Override
    public float[] execute(float[] rgb) {return new float[]{rgb[0], 0, 0};
    }
}
