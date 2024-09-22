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

import katajaLang.compiler.parsing.Parser;
import katajaLang.jvm.writing.ClassWriter;
import katajaLang.jvm.writing.JarWriter;
import katajaLang.model.Compilable;
import sun.security.pkcs.ParsingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public final class Compiler {

    private final HashMap<String, Compilable> classes;
    private final Parser parser;

    public Compiler(){
        classes = new HashMap<>();
        parser = new Parser();
    }

    public void compile(String...files) throws IOException {
        long startTime = System.nanoTime();

        for(String f:files){
            File file = new File(f);

            if(!file.exists()) throw new FileNotFoundException("Unable to find "+f);

            if(file.isDirectory()) compileFolder(file, f);
            else{
                if(!f.endsWith(".ktj")) throw new IllegalArgumentException("Expected "+f+" to be .ktj");

                compileFile(file, file.getPath());
            }
        }

        switch(CompilerConfig.targetType){
            case Class52:
                ClassWriter cw = new ClassWriter();
                for(String name: classes.keySet()) cw.writeClass(classes.get(name), name);
                break;
            case Jar52:
                JarWriter jw = new JarWriter("Program");
                for(String name: classes.keySet()) jw.writeClass(classes.get(name), name);
                jw.close();
                break;
        }
    }

    private void compileFolder(File folder, String relative) throws FileNotFoundException, ParsingException {
        for(File file:folder.listFiles()){
            if(file.isDirectory()) compileFolder(file, relative);
            else if(file.getName().endsWith(".ktj")) compileFile(file, relative);
        }
    }

    private void compileFile(File file, String relative) throws FileNotFoundException, ParsingException {
        HashMap<String, Compilable> parsed = parser.parseFile(file, relative);

        for(String name: parsed.keySet()){
            if(classes.containsKey(name)) throw new ParsingException("Class "+name+" is already defined");
            else classes.put(name, parsed.get(name));
        }
    }
}
