package katajaLang.compiler;

import java.io.File;

public final class CompilerConfig {

    public enum TargetType{
        Class,
        Jar;

        public static TargetType ofString(String type) throws IllegalArgumentException{
            switch(type){
                case "class":
                    return Class;
                case "jar":
                    return Jar;
                default:
                    return null;
            }
        }
    }

    public static TargetType targetType = null;
    public static File outFolder = null;
    public static boolean debug = false;
}
