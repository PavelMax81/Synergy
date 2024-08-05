package functions;

public class Blue implements ImageOperation{
    @Override
    public float[] execute(float[] rgb) {return new float[]{0, 0, rgb[0]};
    }
}