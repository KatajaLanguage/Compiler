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

package katajaLang.jvm;

import katajaLang.compiler.CompilerConfig;
import katajaLang.jvm.bytecode.Flag;
import katajaLang.model.*;
import katajaLang.model.Class;
import katajaLang.model.type.DataType;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class JVMLibHandler {

    public static Compilable getClass(String name){
        for(String lib: CompilerConfig.libs){
            try {
                Enumeration<JarEntry> entries = new JarFile(new File(lib)).entries();

                while(entries.hasMoreElements()){
                    JarEntry entry = entries.nextElement();

                    if(entry.getName().equals(name+".class")) return classFor(new URLClassLoader(new URL[]{new File(lib).toURI().toURL()}).loadClass(name.replace("/", ".")));
                }
            }catch(Exception ignored){}
        }

        return null;
    }

    private static Compilable classFor(java.lang.Class<?> clazz){
        if(clazz.isInterface()){
            Interface result = new Interface(null, null, getModifier(clazz.getModifiers()), new ArrayList<>());
            
            return result;
        }else {
            Class result = new Class(null, null, getModifier(clazz.getModifiers()), new ArrayList<>());

            for (Field field : clazz.getFields())
                result.fields.put(field.getName(), new katajaLang.model.Field(null, getModifier(field.getModifiers()), DataType.ofString(field.getType().getTypeName().replace(".", "/"))));

            return result;
        }
    }

    private static Modifier getModifier(int mod){
        return new Modifier((mod & Flag.PROTECTED) != 0 ? AccessFlag.PROTECTED : (mod & Flag.PRIVATE) != 0 ? AccessFlag.PRIVATE : AccessFlag.PUBLIC,
                (mod & Flag.ABSTRACT) != 0, (mod & Flag.FINAL) != 0);
    }
}
