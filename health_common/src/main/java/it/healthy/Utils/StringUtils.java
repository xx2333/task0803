package it.healthy.Utils;

public class StringUtils {
    public static boolean IsEmpty(String s){
        if("null".equals(s)) return true;
        if("".equals(s)) return true;
        if(s==null) return true;
        return false;
    }
}
