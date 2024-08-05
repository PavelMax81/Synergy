package utils;

import commands.AppBotCommand;
import commands.BotCommonCommands;
import functions.FilterOperation;
import functions.ImageOperation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ImageUtils  {
    public static BufferedImage getImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    public static void saveImage(BufferedImage image, String path) throws IOException {
        ImageIO.write(image, "jpg", new File(path));
    }

    public static float[] rgbIntToArray(int pixel) {
        Color color = new Color(pixel);
        return color.getRGBColorComponents(null);
    }

    public static int arrayToRgbInt(float[] pixel) throws Exception {
        Color color = null;

        try {
            if (pixel.length == 3) {
                color = new Color(pixel[0], pixel[1], pixel[2]);
            } else if (pixel.length == 4) {
                color = new Color(pixel[0], pixel[1], pixel[2], pixel[3]);
            } else {
                throw new Exception("Colour must have 3 or 4 components!");
            }
        } catch(Exception e){
            System.out.println(pixel[0]+" "+pixel[1]+" "+pixel[2]);
            e.printStackTrace();
        }

        if(color != null){
            return color.getRGB();
        } else {
            return -1;
        }
    }

    public static ImageOperation getOperation(String operationName) {
        FilterOperation filterOperation = new FilterOperation();
        Method[] classMethods = filterOperation.getClass().getDeclaredMethods();

        for(Method method : classMethods){
            if(method.isAnnotationPresent(AppBotCommand.class)){
                AppBotCommand command = method.getAnnotation(AppBotCommand.class);

                if(command.name().equals(operationName)){
                    ImageOperation operation = (f) -> (float[])method.invoke(filterOperation, f);
                    return operation;
                }
            }
        }
        return null;
    }
}
