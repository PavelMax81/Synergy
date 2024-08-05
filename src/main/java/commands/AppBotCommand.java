package commands;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AppBotCommand {
    String name();
    String description();
    boolean showInHelp() default false;
    boolean showInKeyboard() default false;
}
