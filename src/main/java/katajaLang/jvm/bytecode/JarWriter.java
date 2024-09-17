package katajaLang.jvm.bytecode;

import katajaLang.compiler.CompilerConfig;
import katajaLang.compiler.CompilingException;
import katajaLang.model.Compilable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class JarWriter {

    private final JarOutputStream stream;
    private final ByteCodeWriter writer;

    public JarWriter(String name) throws IOException {
        File file = new File((CompilerConfig.outFolder == null ? "" : CompilerConfig.outFolder+"/")+name.replace(".", "/")+".jar");

        if(file.exists()) if(!file.delete()) throw new CompilingException("Failed to delete "+file.getAbsolutePath());
        else if(file.getParentFile() != null && !file.getParentFile().exists() && !file.getParentFile().mkdirs()) throw new CompilingException("Failed to create "+file.getParentFile().getAbsolutePath());

        if(!file.createNewFile()) throw new CompilingException("Failed to create "+file.getAbsolutePath());

        stream = new JarOutputStream(Files.newOutputStream(file.toPath()));
        writer = new ByteCodeWriter();
    }

    public void writeClass(Compilable clazz, String className) throws IOException, CompilingException {
        stream.putNextEntry(new JarEntry(className+".class"));
        writer.writeClass(stream, clazz, className);
        stream.closeEntry();
    }

    public void close() throws IOException {
        stream.close();
    }
}
