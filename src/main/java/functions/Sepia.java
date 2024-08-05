package functions;

import java.util.Random;

public class Sepia implements ImageOperation {
    @Override
    public float[] execute(float[] rgb) {
        Random random = new Random();
        float diff = (float)0.4;
        float randomValue = - diff + random.nextFloat()*2*diff;
        float[] newRGB = new float[rgb.length];

        for(int i = 0; i < rgb.length; i++){
            newRGB[i] = rgb[i] + randomValue;

            if(newRGB[i] < 0) {
                newRGB[i] = 0;
            } else if(newRGB[i] > 1) {
                newRGB[i] = 1;
            }
        }
        return newRGB;
    }
}
