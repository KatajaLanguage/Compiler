package katajaLang.jvm.bytecode;

import katajaLang.compiler.CompilerConfig;
import katajaLang.compiler.CompilingException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class ClassWriter {
    public static final int magic = 0xCAFEBABE;

    private FileOutputStream stream;

    public ClassWriter(){

    }

    public void writeClass(String className) throws IOException, CompilingException {
        createFile(className);

        writeConstPool();
        writeClassInfo();
        writeInterfaces();
        writeFields();
        writeMethods();
        writeAttributes();

        stream.flush();
        stream.close();
    }

    private void createFile(String className) throws IOException, CompilingException {
        File file = new File((CompilerConfig.outFolder == null ? "" : CompilerConfig.outFolder+"/")+className.replace(".", "/")+".class");

        if(file.exists()){
            if(!file.delete()) throw new CompilingException("Failed to delete "+file.getAbsolutePath());
        }else{
            if(!file.getParentFile().mkdirs()) throw new CompilingException("Failed to create "+file.getParentFile().getAbsolutePath());
        }

        if(!file.createNewFile()) throw new CompilingException("Failed to create "+file.getAbsolutePath());

        stream = new FileOutputStream(file);

        write4(magic);
        write2(0);
        write2(52);
    }

    private void writeConstPool() throws IOException {
        write2(5);
        write(7);
        write2(3);
        write(7);
        write2(4);
        write(1);
        write2(4);
        write('T');
        write('e');
        write('s');
        write('t');
        write(1);
        write2(16);
        write('j');
        write('a');
        write('v');
        write('a');
        write('/');
        write('l');
        write('a');
        write('n');
        write('g');
        write('/');
        write('O');
        write('b');
        write('j');
        write('e');
        write('c');
        write('t');
    }

    private void writeClassInfo() throws IOException {
        write2(Flag.PUBLIC);
        write2(1);
        write2(2);
    }

    private void writeInterfaces() throws IOException {
        write2(0);
    }

    private void writeFields() throws IOException {
        write2(0);
    }

    private void writeMethods() throws IOException {
        write2(0);
    }

    private void writeAttributes() throws IOException {
        write2(0);
    }

    private void write4(int i) throws IOException {
        stream.write(new byte[] {
                (byte) ((i >> 24) & 0xFF),
                (byte) ((i >> 16) & 0xFF),
                (byte) ((i >> 8) & 0xFF),
                (byte) (i & 0xFF)
        });
    }

    private void write2(int i) throws IOException {
        stream.write(new byte[] {
                (byte) ((i >> 8) & 0xFF),
                (byte) (i & 0xFF)
        });
    }

    private void write(int b) throws IOException {
        stream.write(b);
    }
}
