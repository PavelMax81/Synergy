package functions;

public class GreyScaleNegative implements ImageOperation{
    @Override
    public float[] execute(float[] rgb) {
        final float mean = (float)1.0 - (rgb[0] + rgb[1] + rgb[2])/3;
        return new float[]{mean, mean, mean};
    }
}
