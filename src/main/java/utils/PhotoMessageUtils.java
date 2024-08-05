package utils;
import functions.FilterOperation;
import functions.ImageModification;
import functions.ImageOperation;
import org.telegram.telegrambots.meta.api.objects.File;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PhotoMessageUtils {
    public static List<String> savePhotos(List<File> files, String botToken) throws IOException {
        Random random = new Random();
        ArrayList<String> paths = new ArrayList<>();

        for(File file : files){
            final String imageUrl = "https://api.telegram.org/file/bot" + botToken + "/" + file.getFilePath();
            final String localFileName = "images/" + new Date().getTime() + random.nextLong()+".jpg";

            saveImage(imageUrl, localFileName);
            paths.add(localFileName);
        }
        return paths;
    }

    public static void saveImage(String url, String fileName) throws IOException {
        URL UrlModel = new URL(url);
        InputStream inputStream = UrlModel.openStream();
        OutputStream outputStream = new FileOutputStream(fileName);

        byte[] bytes = new byte[2048];
        int length;

        while((length = inputStream.read(bytes)) != -1){
            outputStream.write(bytes, 0, length);
        }
        System.out.println(url + " is saved to "+fileName);

        inputStream.close();
        outputStream.close();
    }

    public static void processingImage(String fileName, ImageOperation operation) throws Exception {
        final BufferedImage image = ImageUtils.getImage(fileName);
        final RGBMaster master = new RGBMaster(image);
        master.changeImage(operation);
        ImageUtils.saveImage(master.getImage(), fileName);
    }
}
