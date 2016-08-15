package com.svc.note.Utility;

/**
 * Created by PRIYASH on 10-07-2016.
 */
public class ConstantUtil {


    public static String DateKey = "Date";
    public static String TimeKey = "Time";
    public static String DescKey = "Description";
    public static String LabelKey = "Label";
    public static String ColorKey = "Color";
    public static String NotificationKey = "Notification";
    public static String PositionKey = "Position";

    public static String Description = "Desc";

    public static String BLUE = "#48B3FF";
    public static String ACCENT = "#FF4081";
    public static String RED = "#FF0000";
    public static String GREEN = "#008000";
    public static String MAGENTA = "#FF00FF";
    public static String BLACK = "#000000";
    public static String WHITE = "#FFFFFF";
    public static String ORANGE = "#FF4500";
    public static String GRAY = "#708090";
    public static String TEAL = "#008080";
    public static String VIOLET = "#9400D3";
    public static String YELLOW = "#FFFF00";
    public static String LIME = "#00FF00";
    public static String CYAN = "#00FFFF";
    public static String OLIVE = "#808000";
    public static String CRIMSON = "#DC143C";
    public static String CHARTREUSE  = "#7FFF00";


    public static String getColorName(String hexString)
    {
        String colorName = "";

        if(ConstantUtil.BLUE.equals(hexString))
        {
            colorName = "Blue";
        }
        else if(ConstantUtil.ACCENT.equals(hexString))
        {
            colorName = "Accent";
        }
        else if(ConstantUtil.RED.equals(hexString))
        {
            colorName = "Red";
        }
        else if(ConstantUtil.GREEN.equals(hexString))
        {
            colorName = "Green";
        }
        else if(ConstantUtil.MAGENTA.equals(hexString))
        {
            colorName = "Magenta";
        }
        else if(ConstantUtil.BLACK.equals(hexString))
        {
            colorName = "Black";
        }
        else if(ConstantUtil.WHITE.equals(hexString))
        {
            colorName = "White";
        }
        else if(ConstantUtil.ORANGE.equals(hexString))
        {
            colorName = "Orange";
        }
        else if(ConstantUtil.GRAY.equals(hexString))
        {
            colorName = "Gray";
        }
        else if(ConstantUtil.TEAL.equals(hexString))
        {
            colorName = "Teal";
        }
        else if(ConstantUtil.VIOLET.equals(hexString))
        {
            colorName = "Violet";
        }
        else if(ConstantUtil.YELLOW.equals(hexString))
        {
            colorName = "Yellow";
        }
        else if(ConstantUtil.LIME.equals(hexString))
        {
            colorName = "Lime";
        }
        else if(ConstantUtil.CYAN.equals(hexString))
        {
            colorName = "Cyan";
        }
        else if(ConstantUtil.OLIVE.equals(hexString))
        {
            colorName = "Olive";
        }
        else if(ConstantUtil.CRIMSON.equals(hexString))
        {
            colorName = "Crimson";
        }
        else if(ConstantUtil.CHARTREUSE.equals(hexString))
        {
            colorName = "Chartreuse";
        }

        return colorName;
    }
}
