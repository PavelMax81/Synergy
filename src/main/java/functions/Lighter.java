package functions;

public class Lighter implements ImageOperation {
    @Override
    public float[] execute(float[] rgb) {
        float[] newRGB = new float[rgb.length];

        for(int i = 0; i < rgb.length; i++){
            newRGB[i] = 1 - (1-rgb[i])*FACTOR;
        }
        return newRGB;
    }
}
