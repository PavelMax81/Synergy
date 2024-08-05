package utils;

import functions.ImageOperation;

import java.awt.image.BufferedImage;

public class RGBMaster {
    private BufferedImage image;
    private int width;
    private int height;
    private int[] pixels;
    private boolean hasAlphaChannel;

    public RGBMaster(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        hasAlphaChannel = image.getAlphaRaster() != null;
        pixels = image.getRGB(0, 0, width, height, null, 0, width);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void changeImage(ImageOperation operation) throws Exception {
        for(int i = 0; i < pixels.length; i++) {
            float[] pixel = ImageUtils.rgbIntToArray(pixels[i]);
            float[] newPixel = operation.execute(pixel);
            pixels[i] = ImageUtils.arrayToRgbInt(newPixel);
        }
        image.setRGB(0, 0 , width, height, pixels, 0, width);
    }
}
