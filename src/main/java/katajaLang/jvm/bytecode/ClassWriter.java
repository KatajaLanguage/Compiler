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

package katajaLang.jvm.bytecode;

import katajaLang.compiler.CompilerConfig;
import katajaLang.compiler.CompilingException;
import katajaLang.model.Compilable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class ClassWriter {

    private final ByteCodeWriter writer;

    public ClassWriter(){
        writer = new ByteCodeWriter();
    }

    public void writeClass(Compilable clazz, String className) throws IOException, CompilingException {
        FileOutputStream stream = createFile(className);
        writer.writeClass(stream, clazz, className);
        stream.flush();
        stream.close();
    }

    private FileOutputStream createFile(String className) throws IOException, CompilingException {
        File file = new File((CompilerConfig.outFolder == null ? "" : CompilerConfig.outFolder+"/")+className.replace(".", "/")+".class");

        if(file.exists()){
            if(!file.delete()) throw new CompilingException("Failed to delete "+file.getAbsolutePath());
        }else{
            if(file.getParentFile() != null && !file.getParentFile().exists() && !file.getParentFile().mkdirs()) throw new CompilingException("Failed to create "+file.getParentFile().getAbsolutePath());
        }

        if(!file.createNewFile()) throw new CompilingException("Failed to create "+file.getAbsolutePath());

        return new FileOutputStream(file);
    }
}
