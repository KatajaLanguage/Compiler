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
import katajaLang.model.type.ArrayType;
import katajaLang.model.type.ComplexType;
import katajaLang.model.type.DataType;
import katajaLang.model.type.PrimitiveType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public final class Parser {

    private final Lexer lexer;

    private HashMap<String, Compilable> classes;
    private TokenHandler th;
    private Compilable current;
    private String path;
    private String name;
    private Uses uses;

    public Parser(){
        lexer = new Lexer();
    }

    public HashMap<String, Compilable> parseFile(File file, String folder) throws FileNotFoundException {
        th = lexer.lexFile(file);
        classes = new HashMap<>();
        current = null;
        uses = new Uses();

        if(0 < file.getPath().length() - file.getName().length() - 1) path = file.getPath().substring(0, file.getPath().length() - file.getName().length() - 1);
        else path = "";
        if(path.startsWith(folder.replace("/", "\\"))) path = path.substring(folder.length());
        else if(folder.endsWith(".ktj")) path = "";
        name = file.getName().substring(0, file.getName().length() - 4);

        while (th.hasNext()){
            if(th.isNext("use")) parseUse();
            else parseMod();
        }

        return classes;
    }

    private void parseUse(){
        boolean statik = th.isNext("$");
        StringBuilder sb = new StringBuilder(th.assertToken(TokenType.IDENTIFIER).value);
        while (th.isNext("/")) sb.append("/").append(th.assertToken(TokenType.IDENTIFIER).value);

        String alias;

        if(th.isNext("as")) alias = th.assertToken(TokenType.IDENTIFIER).value;
        else alias = sb.toString().split("/")[sb.toString().split("/").length - 1];

        if(uses.containsAlias(alias)) err(alias+" is already defined");

        if(statik) uses.addStatic(alias, sb.toString());
        else uses.addUse(alias, sb.toString());

        th.assertEndOfStatement();
    }

    private void parseMod(){
        AccessFlag acc;

        if(th.isNext("public")) acc = AccessFlag.PUBLIC;
        else if(th.isNext("private")) acc = AccessFlag.PRIVATE;
        else if(th.isNext("protected")) acc = AccessFlag.PROTECTED;
        else acc = AccessFlag.PACKAGE;

        Modifier mod = null;

        boolean abstrakt = false;
        boolean finaly = false;

        while(mod == null){
            switch(th.assertToken(TokenType.IDENTIFIER).value){
                case "abstract":
                    if(abstrakt) err("Is already abstract");
                    abstrakt = true;
                    break;
                case "final":
                    if(finaly) err("Is already final");
                    finaly = true;
                    break;
                default:
                    th.last();
                    mod = new Modifier(acc, abstrakt, finaly);
                    break;
            }
        }

        switch(th.assertToken(TokenType.IDENTIFIER).value){
            case "class":
                parseClass(mod);
                break;
            case "interface":
                parseInterface(mod);
                break;
            default:
                th.last();
                parseField(mod);
                break;
        }
    }

    private void parseClass(Modifier mod){
        String name = parseName();

        if(mod.isInvalidForClass()) err("Illegal Modifier for class "+name);

        th.assertToken("{");

        Class clazz = new Class(uses, mod);
        current = clazz;

        while(!th.isNext("}")){
            parseMod();
        }

        current = null;
        th.assertEndOfStatement();

        if(!classes.containsKey(name)) classes.put(name, clazz);
        else throw new ParsingException("Class "+name+" is already defined");
    }

    private void parseInterface(Modifier mod){
        String name = parseName();

        if(mod.isInvalidForInterface()) err("Illegal Modifier for interface "+name);

        th.assertToken("{");
        th.assertToken("}");
        th.assertEndOfStatement();

        if(!classes.containsKey(name)) classes.put(name, new Interface(uses, mod));
        else throw new ParsingException("Class "+name+" is already defined");
    }

    private String parseName(){
        return (path.isEmpty() ? "" : path+"/")+this.name+"/"+th.assertToken(TokenType.IDENTIFIER).value;
    }

    private void parseField(Modifier mod){
        DataType type = parseType();
        String name = th.assertToken(TokenType.IDENTIFIER).value;
        th.assertEndOfStatement();

        if(mod.isInvalidForField()) err("Illegal Modifier for field "+name);

        Field field = new Field(uses, mod, type);

        if(current == null){
            err("field "+name+" must be in a class");
        }else{
            if(current instanceof Class){
                if(((Class) current).fields.containsKey(name)) err("field "+name+" is already defined");

                ((Class) current).fields.put(name, field);
            }else err("field "+name+" must be in a class");
        }
    }

    private DataType parseType(){
        th.assertToken(TokenType.IDENTIFIER);

        DataType type;

        if(PrimitiveType.PRIMITIVES.contains(th.current().value)) type = PrimitiveType.ofString(th.current().value);
        else type = new ComplexType(th.current().value);

        while(th.isNext("[")){
            th.assertToken("]");
            type = new ArrayType(type);
        }

        return type;
    }

    private void err(String message) throws ParsingException{
        throw new ParsingException(message+" at "+(path.isEmpty() ? "" : path+"/")+name+".ktj:"+th.getLine());
    }
}
