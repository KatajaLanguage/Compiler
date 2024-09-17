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
import katajaLang.jvm.bytecode.constant.*;
import katajaLang.model.Compilable;
import katajaLang.model.Interface;
import katajaLang.model.Modifier;

import java.io.IOException;
import java.io.OutputStream;

public final class ByteCodeWriter {
    public static final int magic = 0xCAFEBABE;

    private OutputStream stream;
    private Compilable clazz;

    public ByteCodeWriter(){

    }

    public void writeClass(OutputStream stream, Compilable clazz, String className) throws IOException, CompilingException {
        this.stream = stream;
        this.clazz = clazz;

        writeConstPool(className);
        writeClassInfo();
        writeInterfaces();
        writeFields();
        writeMethods();
        writeAttributes();
    }

    private void writeVersion() throws IOException {
        write4(magic);
        write2(0);
        switch(CompilerConfig.targetType){
            case Class52:
                write2(52);
                break;
        }
    }

    private void writeConstPool(String className) throws IOException {
        write2(5);
        write(7);
        write2(3);
        write(7);
        write2(4);
        writeUtf8(new Utf8Info(className));
        writeUtf8(new Utf8Info("java/lang/Object"));
    }

    private void writeUtf8(Utf8Info info) throws IOException {
        write(Utf8Info.tag);
        write2(info.value.length());
        for(char c:info.value.toCharArray()) write(c);
    }

    private void writeClass(ClassInfo info) throws IOException {
        write(ClassInfo.tag);
        write2(info.name_index);
    }

    private void writeFieldRef(FieldRefInfo info) throws IOException {
        write(FieldRefInfo.tag);
        write2(info.class_index);
        write2(info.name_and_type_index);
    }

    private void writeMethodRef(MethodRefInfo info) throws IOException {
        write(MethodRefInfo.tag);
        write2(info.class_index);
        write2(info.name_and_type_index);
    }

    private void writeInterfaceMethodRef(InterfaceMethodRefInfo info) throws IOException {
        write(FieldRefInfo.tag);
        write2(info.class_index);
        write2(info.name_and_type_index);
    }

    private void writeString(StringInfo info) throws IOException {
        write(StringInfo.tag);
        write2(info.string_index);
    }

    private void writeInteger(IntegerInfo info) throws IOException {
        write(IntegerInfo.tag);
        write4(info.value);
    }

    private void writeFloat(FloatInfo info) throws IOException {
        write(FloatInfo.tag);
        write4(Float.floatToIntBits(info.value));
    }

    private void writeLong(LongInfo info) throws IOException {
        write(LongInfo.tag);
        write8(info.value);
    }

    private void writeDouble(DoubleInfo info) throws IOException {
        write(DoubleInfo.tag);
        write8(Double.doubleToLongBits(info.value));
    }

    private void writeNameAndType(NameAndTypeInfo info) throws IOException {
        write(NameAndTypeInfo.tag);
        write2(info.name_index);
        write2(info.descriptor_index);
    }

    private void writeMethodHandle(MethodHandleInfo info) throws IOException {
        write(MethodHandleInfo.tag);
        write(info.reference_kind);
        write2(info.reference_index);
    }

    private void writeMethodType(MethodTypeInfo info) throws IOException {
        write(MethodTypeInfo.tag);
        write2(info.descriptor_index);
    }

    private void writeDynamic(DynamicInfo info) throws IOException {
        write(DynamicInfo.tag);
        write2(info.bootstrap_method_attr_index);
        write2(info.name_and_type_index);
    }

    private void writeInvokeDynamic(InvokeDynamicInfo info) throws IOException {
        write(InvokeDynamicInfo.tag);
        write2(info.bootstrap_method_attr_index);
        write2(info.name_and_type_index);
    }

    private void writeModule(ModuleInfo info) throws IOException {
        write(ModuleInfo.tag);
        write2(info.name_index);
    }

    private void writePackage(PackageInfo info) throws IOException {
        write(PackageInfo.tag);
        write2(info.name_index);
    }

    private void writeClassInfo() throws IOException {
        write2(getFlag(clazz.mod) + (clazz instanceof Interface ? Flag.INTERFACE : 0));
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

    private int getFlag(Modifier mod){
        int acc = 0;

        switch(mod.acc){
            case PUBLIC:
                acc += Flag.PUBLIC;
                break;
            case PRIVATE:
                acc += Flag.PRIVATE;
                break;
            case PROTECTED:
                acc += Flag.PROTECTED;
                break;
        }

        return acc;
    }

    private void write8(long i) throws IOException {
        stream.write(new byte[] {
                (byte) ((i >> 56) & 0xFF),
                (byte) ((i >> 48) & 0xFF),
                (byte) ((i >> 40) & 0xFF),
                (byte) ((i >> 32) & 0xFF),
                (byte) ((i >> 24) & 0xFF),
                (byte) ((i >> 16) & 0xFF),
                (byte) ((i >> 8) & 0xFF),
                (byte) (i & 0xFF)
        });
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
