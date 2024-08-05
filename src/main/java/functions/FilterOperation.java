package functions;

import commands.AppBotCommand;

public class FilterOperation {
    @AppBotCommand(name = "greyscale", description = "Image will be shown in greyscale.", showInKeyboard = true)
    public static float[] greyScale(float[] rgb) { return new GreyScale().execute(rgb); }

    @AppBotCommand(name = "red", description = "Image will be shown in red.", showInKeyboard = true)
    public static float[] red(float[] rgb) {
        return new Red().execute(rgb);
    }

    @AppBotCommand(name = "green", description = "Image will be shown in green.", showInKeyboard = true)
    public static float[] green(float[] rgb) {
        return new Green().execute(rgb);
    }

    @AppBotCommand(name = "blue", description = "Image will be shown in blue.", showInKeyboard = true)
    public static float[] blue(float[] rgb) {
        return new Blue().execute(rgb);
    }

    @AppBotCommand(name = "sepia", description = "Image will be shown in sepia.", showInKeyboard = true)
    public static float[] sepia(float[] rgb) {
        return new Sepia().execute(rgb);
    }

    @AppBotCommand(name = "light", description = "Image will be shown in light.", showInKeyboard = true)
    public static float[] light(float[] rgb) {
        return new Lighter().execute(rgb);
    }

    @AppBotCommand(name = "dark", description = "Image will be shown in dark.", showInKeyboard = true)
    public static float[] dark(float[] rgb) {
        return new Darker().execute(rgb);
    }

    @AppBotCommand(name = "negative", description = "Image will be shown in negative.", showInKeyboard = true)
    public static float[] negative(float[] rgb) {
        return new Negative().execute(rgb);
    }

    @AppBotCommand(name = "red & green", description = "Image will be shown in red&green.", showInKeyboard = true)
    public static float[] redGreen(float[] rgb) {
        return new RedGreen().execute(rgb);
    }

    @AppBotCommand(name = "green & blue", description = "Image will be shown in green&blue.", showInKeyboard = true)
    public static float[] greenBlue(float[] rgb) {
        return new GreenBlue().execute(rgb);
    }

    @AppBotCommand(name = "greyscale & negative", description = "Image will be shown in greyscale negative.", showInKeyboard = true)
    public static float[] greyScaleNegative(float[] rgb) {
        return new GreyScaleNegative().execute(rgb);
    }

    @AppBotCommand(name = "blue & red", description = "Image will be shown in blue&red.", showInKeyboard = true)
    public static float[] blueRed(float[] rgb) {
        return new BlueRed().execute(rgb);
    }

    @AppBotCommand(name = "copy", description = "Image will be copied.", showInKeyboard = true)
    public static float[] copy(float[] rgb) {
        return new Copy().execute(rgb);
    }
}