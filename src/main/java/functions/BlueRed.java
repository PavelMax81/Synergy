package functions;

public class BlueRed implements ImageOperation{
    @Override
    public float[] execute(float[] rgb) {return new float[]{rgb[0], 0, rgb[2]};
    }
}
