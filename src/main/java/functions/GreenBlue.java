package functions;

public class GreenBlue implements ImageOperation{
    @Override
    public float[] execute(float[] rgb) {return new float[]{0, rgb[1], rgb[2]};
    }
}