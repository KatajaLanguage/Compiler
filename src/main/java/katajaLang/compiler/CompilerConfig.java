/*
 * Copyright (C) 2024 Xaver Weste
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

import java.nio.file.Path;

public final class CompilerConfig {

    public enum TargetType{
        Class52,
        Class55,
        Class61,
        Jar52,
        Jar55,
        Jar61;

        public static TargetType ofString(String type) throws IllegalArgumentException{
            switch(type){
                case "class":
                case "jar":
                    return TargetType.ofString(type+" "+getSupportedLTSVersion());
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

        private static int getSupportedLTSVersion(){
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

    public static TargetType targetType = null;
    public static Path outFolder = null;
    public static boolean debug = false;
}
