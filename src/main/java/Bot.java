import commands.AppBotCommand;
import functions.FilterOperation;
import functions.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.ImageUtils;
import utils.PhotoMessageUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    ArrayList<Message> messages = new ArrayList<>();
    @Override
    public String getBotUsername() {
    return "JavaTest20240520Bot";
}
    @Override
    public String getBotToken() {
        return "6849029042:AAHWTSpX9GO9Z8il0DK2DKS1BRIwU_CcHOs";
    }
    private static ArrayList<AppBotCommand> commands;

    @Override
    public void onUpdateReceived(Update update) {
        // Message from Telegram
        Message message = update.getMessage();
        String chatId = message.getChatId().toString();

        try {
            SendMessage runPhotoMessage = runPhotoMessage(message);

            if (runPhotoMessage != null) {
                execute(runPhotoMessage);
            } else if (messages.isEmpty()) {
                SendMessage sendMessage = getStartMessage(chatId);
                execute(sendMessage);
            }

            if (!messages.isEmpty()) {
                ImageOperation operation = ImageUtils.getOperation(message.getText());
                int imageNumber = 0;

                for(Message nextMessage : messages){
                    if(!nextMessage.hasText()){
                        nextMessage.setText(message.getText());
                    }
                    Object responseMediaMessage = runPhotoFilter(nextMessage, operation);

                    if (responseMediaMessage != null) {
                        if (responseMediaMessage instanceof SendMediaGroup) {
                            SendMediaGroup group = (SendMediaGroup) responseMediaMessage;
                            imageNumber += group.getMedias().size();
                            execute(group);
                        } else if(responseMediaMessage instanceof SendPhoto){
                            imageNumber++;
                            SendPhoto sendPhoto = (SendPhoto) responseMediaMessage;
                            sendPhoto.setCaption("Image # "+imageNumber+" "+sendPhoto.getCaption());
                            execute(sendPhoto);
                        } else if (responseMediaMessage instanceof SendMessage) {
                            execute((SendMessage) responseMediaMessage);
                        }
                    }
                }

                if(operation != null){
                    SendMessage sendMessage = getFinalMessage(chatId, imageNumber);
                    execute(sendMessage);
                    messages.clear();
                }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        //   setMembers(message, 5);
    }

    private static void setMembers(Message message, int number) {
        List<User> members = message.getNewChatMembers();

        for(int i = 0; i < number; i++){
            User user = new User();
            user.setUserName("Member # "+i);
            members.add(user);
        }
        message.setNewChatMembers(members);
        System.out.println(message.getChat());
        System.out.println(members.size());

        for(User member : members){
            System.out.println(member.getUserName());
        }
    }

    private static SendMessage getStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("No images available. Please send an image to use the filter.");
        return sendMessage;
    }

    private static SendMessage getFinalMessage(String chatId, int imageNumber) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (imageNumber > 1) {
            sendMessage.setText("All "+ imageNumber +" images are modified. You can continue.");
        } else {
            sendMessage.setText("You can continue.");
        }
        return sendMessage;
    }

    private static ArrayList<KeyboardRow> getKeyboardRows(Class someClass){
        Method[] classMethods = someClass.getMethods();
        commands = new ArrayList<>();

        for(Method method : classMethods){
            method.trySetAccessible();

            if(method.isAnnotationPresent(AppBotCommand.class)){
                AppBotCommand command = method.getAnnotation(AppBotCommand.class);
                commands.add(command);
            }
        }
        ArrayList<KeyboardRow> keyBoardRows = new ArrayList<>();

        int commandNumber = commands.size();
        int colNumber = 4;
        int rowNumber = commandNumber/colNumber + ((commandNumber % colNumber == 0) ? 0 : 1);

        for(int i = 0; i < rowNumber; i++){
            KeyboardRow row = new KeyboardRow();

            for(int j = 0; j < colNumber; j++){
                if (i * colNumber + j < commandNumber) {
                    AppBotCommand command = commands.get(i * colNumber + j);
                    KeyboardButton button = new KeyboardButton(command.name());
                    row.add(button);
                } else break;
            }
            keyBoardRows.add(row);
        }
        return keyBoardRows;
    }

    private SendMessage runPhotoMessage(Message message) throws TelegramApiException {
        String chatId = message.getChatId().toString();

        if(message.hasPhoto()) {
            if (messages.isEmpty()) {
                messages.add(message);
                ReplyKeyboardMarkup markup = makeKeyboardMarkup();
                return getMessage(markup, chatId, "Choose the filter.");
            }
            messages.add(message);
        }
        return null;
    }

    private static SendMessage getMessage(ReplyKeyboardMarkup markup, String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(markup);
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }

    private static ReplyKeyboardMarkup makeKeyboardMarkup() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> allKeyboardRows = new ArrayList<>(getKeyboardRows(FilterOperation.class));
        markup.setKeyboard(allKeyboardRows);
        markup.setOneTimeKeyboard(true);
        return markup;
    }

    private Object runPhotoFilter(Message newMessage, ImageOperation operation) throws TelegramApiException {
        String chatId = newMessage.getChatId().toString();

        if(!newMessage.hasPhoto()){
            return getMessage(null, chatId, "Please send an image to use the filter.");
        }
        String operationName = newMessage.getText();

        if(operation == null) {
            operation = ImageUtils.getOperation(operationName);
        }
        if(operation == null) {
            return null;
        }
        System.out.println("operation: "+operationName);

        try {
            List<File> files = getFilesByMessage(newMessage);
            List<String> paths = PhotoMessageUtils.savePhotos(files, getBotToken());
            SendMediaGroup photos = preparePhotoMessage(paths, operation, chatId);
            int size = photos.getMedias().size();

            if (size != 4) {
                return getSendMediaGroup(size, photos, operationName, chatId);
            } else {
                return getSendPhoto(files, photos, chatId, operationName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SendPhoto getSendPhoto(List<File> files, SendMediaGroup photos, String chatId, String operationName) {
        int maxFileIndex = getMaximalFileIndex(files);
        java.io.File file = photos.getMedias().get(maxFileIndex).getNewMediaFile();

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(file));
        sendPhoto.setCaption("is modified by filter \"" + operationName + "\".");
        return sendPhoto;
    }

    private static SendMediaGroup getSendMediaGroup(int size, SendMediaGroup photos, String operationName, String chatId) {
        SendMediaGroup mediaGroup = new SendMediaGroup();
        ArrayList<InputMedia> medias = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            java.io.File file = photos.getMedias().get(i).getNewMediaFile();
            InputMedia inputMedia = new InputMediaPhoto();
            inputMedia.setMedia(file, "file");
            inputMedia.setCaption("Modified by filter \"" + operationName + "\".");
            medias.add(inputMedia);
        }
        mediaGroup.setChatId(chatId);
        mediaGroup.setMedias(medias);
        return mediaGroup;
    }

    private int getMaximalFileIndex(List<File> files){
        int maxSize = 0;
        int maxFileIndex = -1;

        for(int i = 0; i < files.size(); i++){
            File file = files.get(i);
            int size = file.getFileSize();

            if(size > maxSize) {
                maxSize = size;
                maxFileIndex = i;
            }
        }
        return maxFileIndex;
    }

    private List<File> getFilesByMessage(Message message) throws TelegramApiException {
        // Photo from Telegram
        List<PhotoSize> photos = message.getPhoto();

        if(photos == null){
            return new ArrayList<>();
        }
        ArrayList<File> files = new ArrayList<>();

        // Saving of photo from Telegram
        for (PhotoSize photoSize : photos) {
            String photoFileId = photoSize.getFileId();
            File file = sendApiMethod(new GetFile(photoFileId));
            files.add(file);
        }

        return files;
    }

    private SendMediaGroup preparePhotoMessage(List<String> localPaths, ImageOperation operation, String chatId) throws Exception {
        SendMediaGroup mediaGroup = new SendMediaGroup();
        ArrayList<InputMedia> medias = new ArrayList<>();
        InputMedia inputMedia;

        for(String path : localPaths){
            inputMedia = new InputMediaPhoto();
            PhotoMessageUtils.processingImage(path, operation);
            inputMedia.setMedia(new java.io.File(path), "file");
            medias.add(inputMedia);
        }
        mediaGroup.setMedias(medias);
        mediaGroup.setChatId(chatId);
        return mediaGroup;
    }
}
