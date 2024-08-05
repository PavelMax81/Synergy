package functions;

public class GreyScale implements ImageOperation{
    @Override
    public float[] execute(float[] rgb) {
        final float mean = (rgb[0] + rgb[1] + rgb[2])/3;
        return new float[]{mean, mean, mean};
    }
}
