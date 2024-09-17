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

import katajaLang.compiler.lexer.Lexer;
import katajaLang.compiler.lexer.TokenType;
import katajaLang.model.*;
import katajaLang.model.Class;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public final class Parser {

    private final Lexer lexer;

    private HashMap<String, Compilable> classes;
    private TokenHandler th;
    private String path;
    private String name;

    public Parser(){
        lexer = new Lexer();
    }

    public HashMap<String, Compilable> parseFile(File file, String folder) throws FileNotFoundException {
        th = lexer.lexFile(file);
        classes = new HashMap<>();

        if(0 < file.getPath().length() - file.getName().length() - 1) path = file.getPath().substring(0, file.getPath().length() - file.getName().length() - 1);
        else path = "";
        if(path.startsWith(folder.replace("/", "\\"))) path = path.substring(folder.length());
        else if(folder.endsWith(".ktj")) path = "";
        name = file.getName().substring(0, file.getName().length() - 4);

        while (th.hasNext()){
            parseMod();
        }

        return classes;
    }

    private void parseMod(){
        AccessFlag acc;

        if(th.isNext("public")) acc = AccessFlag.PUBLIC;
        else if(th.isNext("private")) acc = AccessFlag.PRIVATE;
        else if(th.isNext("protected")) acc = AccessFlag.PROTECTED;
        else acc = AccessFlag.PACKAGE;

        Modifier mod = new Modifier(acc);

        switch(th.assertToken(TokenType.IDENTIFIER).value){
            case "class":
                parseClass(mod);
                break;
            case "interface":
                parseInterface(mod);
                break;
            default:
                err("Illegal argument '"+th.current().value+"'");
        }
    }

    private void parseClass(Modifier mod){
        String name = parseName();

        if(mod.isInvalidForClass()) err("Illegal Modifier for class "+name);

        th.assertToken("{");
        th.assertToken("}");

        if(!classes.containsKey(name)) classes.put(name, new Class(mod));
        else throw new ParsingException("Class "+name+" is already defined");
    }

    private void parseInterface(Modifier mod){
        String name = parseName();

        if(mod.isInvalidForInterface()) err("Illegal Modifier for interface "+name);

        th.assertToken("{");
        th.assertToken("}");

        if(!classes.containsKey(name)) classes.put(name, new Interface(mod));
        else throw new ParsingException("Class "+name+" is already defined");
    }

    private String parseName(){
        return (path.isEmpty() ? "" : path+"/")+this.name+"/"+th.assertToken(TokenType.IDENTIFIER).value;
    }

    private void err(String message) throws ParsingException{
        throw new ParsingException(message+" at "+(path.isEmpty() ? "" : path+"/")+name+".ktj:"+th.getLine());
    }
}
