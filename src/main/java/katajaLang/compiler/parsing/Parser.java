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

package katajaLang.compiler.parsing;

import katajaLang.compiler.CompilingException;
import katajaLang.compiler.lexer.Lexer;
import katajaLang.compiler.lexer.TokenType;
import katajaLang.model.AccessFlag;
import katajaLang.model.Class;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public final class Parser {

    private final Lexer lexer;

    private HashMap<String, Class> classes;
    private TokenHandler th;
    private String path;
    private String name;

    public Parser(){
        lexer = new Lexer();
    }

    public HashMap<String, Class> parseFile(File file, String folder) throws FileNotFoundException {
        th = lexer.lexFile(file);
        classes = new HashMap<>();

        if(0 < file.getPath().length() - file.getName().length() - 1) path = file.getPath().substring(0, file.getPath().length() - file.getName().length() - 1);
        else path = "";
        if(path.startsWith(folder.replace("/", "\\"))) path = path.substring(folder.length());
        else if(folder.endsWith(".ktj")) path = "";
        name = file.getName().substring(0, file.getName().length() - 4);

        while (th.hasNext()){

        }

        return classes;
    }
}