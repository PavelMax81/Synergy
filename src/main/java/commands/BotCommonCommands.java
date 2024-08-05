package commands;

public class BotCommonCommands {
    @AppBotCommand(name = "/hello", description = "when request hello", showInHelp = true)
    public String hello(){
        return "Hello, user!";
    }

    @AppBotCommand(name = "/bye", description = "when request bye", showInHelp = true)
    public String bye(){
        return "Bye, user!";
    }

    @AppBotCommand(name = "/help", description = "when request help", showInKeyboard = true)
    public String help(){
        return "Help!";
    }
}
