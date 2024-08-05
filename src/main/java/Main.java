import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.io.File;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        createDirectory("images");
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        TelegramLongPollingBot bot = new Bot();
        api.registerBot(bot);
    }

    public static void createDirectory(String dirName) {
        File directory = new File(dirName);

        if (!directory.exists()) {
            directory.mkdir();
        } else {
            for(File file : directory.listFiles()) {
                file.delete();
            }
        }
    }
}
