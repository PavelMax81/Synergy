package functions;

public class Green implements ImageOperation{
    @Override
    public float[] execute(float[] rgb) {return new float[]{0, rgb[0], 0};
    }
}