package xyz.jxmm.tools;

public class HasString {
    public static Boolean hasString(String old, String New){
        char c = New.charAt(0);
        String TF = "";
        for (int i = 0; i < old.length(); i++) {
            char c1 = old.charAt(i);
            if (c1 == c){
                int m = New.length();
                TF = old.substring(i,i+m);
                break;
            }
        }
        return TF.equals(New);
    }
}
