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
    public static Path outFolder = null;
    public static boolean debug = false;
}
