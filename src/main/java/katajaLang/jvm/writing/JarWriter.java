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

package katajaLang.jvm.writing;

import katajaLang.compiler.CompilerConfig;
import katajaLang.compiler.CompilingException;
import katajaLang.model.Compilable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

/**
 * Class to write Jar Files
 */
public class JarWriter {

    private final JarOutputStream stream;
    private final ByteCodeWriter writer;

    /**
     * Creates a new Jar file and opens an OutputStream
     */
    public JarWriter(String name) throws IOException {
        File file = new File((CompilerConfig.outFolder == null ? "" : CompilerConfig.outFolder+"/")+name.replace(".", "/")+".jar");

        if(file.exists()) if(!file.delete()) throw new CompilingException("Failed to delete "+file.getAbsolutePath());
        else if(file.getParentFile() != null && !file.getParentFile().exists() && !file.getParentFile().mkdirs()) throw new CompilingException("Failed to create "+file.getParentFile().getAbsolutePath());

        if(!file.createNewFile()) throw new CompilingException("Failed to create "+file.getAbsolutePath());

        stream = new JarOutputStream(Files.newOutputStream(file.toPath()));
        writer = new ByteCodeWriter();
    }

    /**
     * writes the given class in to the
     */
    public void writeClass(Compilable clazz, String className) throws IOException, CompilingException {
        stream.putNextEntry(new JarEntry(className+".class"));
        writer.writeClass(stream, JVMClassBuilder.buildWritableClass(clazz, className));
        stream.closeEntry();
    }

    /**
     * Closes the OutputStream
     */
    public void close() throws IOException {
        stream.close();
    }
}
