/*
 * Copyright (C) 2024 ni271828mand
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License 3.0 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package katajaLang.compiler;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Class for Compiler Configuration
 */
public final class CompilerConfig {

    /**
     * Representation of the Compiler Target Type
     */
    public enum TargetType{
        Class52,
        Class55,
        Class61,
        Jar52,
        Jar55,
        Jar61;

        /**
         * Converts the given String to the corresponding Target Type
         */
        public static TargetType ofString(String type) throws IllegalArgumentException{
            switch(type){
                case "class":
                case "jar":
                    return TargetType.ofString(type+" "+ getSupportedJavaLTSVersion());
                case "class 52":
                    return Class52;
                case "class 55":
                    return Class55;
                case "class 61":
                    return Class61;
                case "jar 52":
                    return Jar52;
                case "jar 55":
                    return Jar55;
                case "jar 61":
                    return Jar61;
                default:
                    return null;
            }
        }

        /**
         * Returns the highest supported Java LTS version that is supported on the Computer of the User. If no Java LTS Version is supported -1 will be returned
         */
        private static int getSupportedJavaLTSVersion(){
            String versionString = System.getProperty("java.version");
            int version;

            if(versionString.startsWith("1."))
                version = Integer.parseInt(versionString.split("\\.")[1]);
            else
                version = Integer.parseInt(versionString.split("\\.")[0]);

            // Not supported versions
            if(version < 8) return -1;

            // Supported versions
            if(version >= 17) return 61;
            if(version >= 11) return 55;
            return 52;
        }
    }

    public static ArrayList<String> libs = new ArrayList<>();
    public static TargetType targetType = null;
    public static Path outFolder = null;
    public static boolean clearOut = false;
    public static boolean debug = false;

    public static void addLib(String path){
        if(!libs.contains(path)){
            if(new File(path).exists()) System.err.println(path+" does not exist");
            else if(!path.endsWith(".jar")) System.err.println("."+path.split("\\.")[path.split("\\.").length - 1]+" is not supported");
            else libs.add(path);
        }
    }
}
