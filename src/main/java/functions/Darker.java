package functions;

public class Darker implements ImageOperation {
    @Override
    public float[] execute(float[] rgb) {
        float[] newRGB = new float[rgb.length];

        for(int i = 0; i < rgb.length; i++){
            newRGB[i] = rgb[i] * FACTOR;
        }
        return newRGB;
    }
}
