package functions;

import utils.ImageUtils;
import utils.RGBMaster;

import java.awt.image.BufferedImage;

public interface ImageModification {
    static void execute(String inputImageFileName, String[] outputImageFileNames, ImageOperation[] functions) throws Exception {
        if(outputImageFileNames.length == 0 || functions.length == 0){
            System.out.println("Error! The number of output files or the number of functions equals 0.");
            return;
        }
        int minNumber = (outputImageFileNames.length < functions.length) ? outputImageFileNames.length : functions.length;

        for(int i = 0; i < minNumber; i++) {
            BufferedImage image = ImageUtils.getImage(inputImageFileName);
            RGBMaster rgbMaster = new RGBMaster(image);
            rgbMaster.changeImage(functions[i]);
            ImageUtils.saveImage(rgbMaster.getImage(), outputImageFileNames[i]);
            System.out.println("The result of operation "+functions[i]+" is saved to "+outputImageFileNames[i]);
        }
    }
}