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
import java.util.ArrayList;
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
        if(path.startsWith(folder.replace("/", "\\"))) path = path.substring(folder.length()+1);
        else if(folder.endsWith(".ktj")) path = "";
        name = file.getName().substring(0, file.getName().length() - 4);

        while (th.hasNext()){
            if(th.isNext("use")) parseUse();
            else parseMod();
        }

        if(classes.size() == 1 && classes.keySet().toArray(new String[0])[0].equals(name)){
            uses.addUse(name, name);
            return classes;
        }else{
            HashMap<String, Compilable> result = new HashMap<>();
            String path = (this.path.isEmpty() ? "" : this.path+"/")+this.name+"/";

            for(String clazz: classes.keySet()){
                result.put(path+clazz, classes.get(clazz));
                uses.addUse(clazz, path+clazz);
            }

            return result;
        }
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
                if(current != null) err("Expected Method or Field");
                parseClass(mod);
                break;
            case "interface":
                if(current != null) err("Expected Method or Field");
                parseInterface(mod);
                break;
            default:
                th.last();
                parseMethodOrField(mod);
                break;
        }
    }

    private void parseClass(Modifier mod){
        String name = th.assertToken(TokenType.IDENTIFIER).value;

        if(mod.isInvalidForClass()) err("Illegal Modifier for class "+name);

        ArrayList<String> superClasses = new ArrayList<>();

        if(th.isNext("extends")){
            do{
                String superClass = th.assertToken(TokenType.IDENTIFIER).value;

                if(superClasses.contains(superClass)) err("Class "+superClass+" is already extended");

                superClasses.add(superClass);
            }while(th.isNext(","));
        }

        th.assertToken("{");

        Class clazz = new Class(uses, getFileName(), mod, superClasses);
        current = clazz;

        while(!th.isNext("}")){
            parseMod();
        }

        current = null;
        th.assertEndOfStatement();

        if(!classes.containsKey(name)) classes.put(name, clazz);
        else err("Class "+name+" is already defined");
    }

    private void parseInterface(Modifier mod){
        String name = th.assertToken(TokenType.IDENTIFIER).value;

        if(mod.isInvalidForInterface()) err("Illegal Modifier for interface "+name);

        ArrayList<String> superInterfaces = new ArrayList<>();

        if(th.isNext("extends")){
            do{
                String superInterface = th.assertToken(TokenType.IDENTIFIER).value;

                if(superInterfaces.contains(superInterface)) err("Class "+superInterface+" is already extended");

                superInterfaces.add(superInterface);
            }while(th.isNext(","));
        }

        Interface clazz = new Interface(uses, getFileName(), mod, superInterfaces);
        current = clazz;

        th.assertToken("{");
        while(!th.isNext("}")) parseMod();
        th.assertEndOfStatement();

        current = null;

        if(!classes.containsKey(name)) classes.put(name, clazz);
        else err("Class "+name+" is already defined");
    }

    private void parseMethodOrField(Modifier mod){
        DataType type = parseDataType();
        String name = th.assertToken(TokenType.IDENTIFIER).value;

        if(th.isNext("(")){
            // Method
            th.assertToken(")");

            if(current == null) err("Expected Class");

            if(current instanceof Interface){
                if(!mod.abstrakt) err("Method should be abstract");
                if(((Interface) current).methods.containsKey(name)) err("Method is already defined");

                ((Interface) current).methods.put(name, new Method(uses, mod, type));
            }else if(current instanceof Class){
                if(((Class) current).methods.containsKey(name)) err("Method is already defined");
                if(mod.abstrakt && !current.mod.abstrakt) err("Class should be abstract");

                ((Class) current).methods.put(name, new Method(uses, mod, type));
            }else err("");
        }else{
            //Field
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
    }

    private DataType parseDataType(){
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

    private String getFileName(){
        return (path.isEmpty() ? "" : path+"/")+name+".ktj";
    }

    private void err(String message) throws ParsingException{
        throw new ParsingException(message+" at "+(path.isEmpty() ? "" : path+"/")+name+".ktj:"+th.getLine());
    }
}
