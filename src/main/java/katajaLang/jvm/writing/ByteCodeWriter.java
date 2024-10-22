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
import katajaLang.jvm.Attribute;
import katajaLang.jvm.constpool.*;
import katajaLang.jvm.infos.FieldInfo;
import katajaLang.jvm.infos.MethodInfo;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Class to write java bytecode
 */
final class ByteCodeWriter {
    static final int magic = 0xCAFEBABE;

    private OutputStream stream;
    private JVMWritableClass clazz;

    void writeClass(OutputStream stream, JVMWritableClass clazz) throws IOException, CompilingException {
        this.stream = stream;
        this.clazz = clazz;

        writeVersion();
        writeConstPool();
        writeClassInfo();
        writeInterfaces();
        writeFields();
        writeMethods();
        writeAttributes();
    }

    void writeVersion() throws IOException {
        write4(magic);
        write2(0);
        switch(CompilerConfig.targetType){
            case Class52:
            case Jar52:
                write2(52);
                break;
            case Class55:
            case Jar55:
                write2(55);
                break;
            case Class61:
            case Jar61:
                write2(61);
                break;
        }
    }

    private void writeConstPool() throws IOException {
        ConstPool cp = clazz.getConstPool();
        write2(cp.getSize());
        for(ConstantInfo constInfo: cp.getEntries()){
            if(constInfo instanceof Utf8Info) writeUtf8((Utf8Info) constInfo);
            else if(constInfo instanceof ClassInfo) writeClass((ClassInfo) constInfo);
            else if(constInfo instanceof FieldRefInfo) writeFieldRef((FieldRefInfo) constInfo);
            else if(constInfo instanceof MethodRefInfo) writeMethodRef((MethodRefInfo) constInfo);
            else if(constInfo instanceof InterfaceMethodRefInfo) writeInterfaceMethodRef((InterfaceMethodRefInfo) constInfo);
            else if(constInfo instanceof StringInfo) writeString((StringInfo) constInfo);
            else if(constInfo instanceof IntegerInfo) writeInteger((IntegerInfo) constInfo);
            else if(constInfo instanceof FloatInfo) writeFloat((FloatInfo) constInfo);
            else if(constInfo instanceof DoubleInfo) writeDouble((DoubleInfo) constInfo);
            else if(constInfo instanceof LongInfo) writeLong((LongInfo) constInfo);
            else if(constInfo instanceof NameAndTypeInfo) writeNameAndType((NameAndTypeInfo) constInfo);
            else if(constInfo instanceof MethodHandleInfo) writeMethodHandle((MethodHandleInfo) constInfo);
            else if(constInfo instanceof MethodTypeInfo) writeMethodType((MethodTypeInfo) constInfo);
            else if(constInfo instanceof DynamicInfo) writeDynamic((DynamicInfo) constInfo);
            else if(constInfo instanceof InvokeDynamicInfo) writeInvokeDynamic((InvokeDynamicInfo) constInfo);
            else if(constInfo instanceof PackageInfo) writePackage((PackageInfo) constInfo);
            else if(constInfo instanceof ModuleInfo) writeModule((ModuleInfo) constInfo);
        }
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
        write2(clazz.getAccessFlag());
        write2(clazz.this_class);
        write2(clazz.super_class);
    }

    private void writeInterfaces() throws IOException {
        write2(clazz.interfaces.length);
        for(int index:clazz.interfaces) write2(index);
    }

    private void writeFields() throws IOException {
        FieldInfo[] fields = clazz.getFields();
        write2(fields.length);

        for(FieldInfo fInfo:fields){
            write2(fInfo.access_flag);
            write2(fInfo.name_index);
            write2(fInfo.descriptor_index);
            write2(0);
        }
    }

    private void writeMethods() throws IOException {
        MethodInfo[] methods = clazz.getMethods();
        write2(methods.length);

        for(MethodInfo mInfo:methods){
            write2(mInfo.access_flag);
            write2(mInfo.name_index);
            write2(mInfo.descriptor_index);
            write2(0);
        }
    }

    private void writeAttributes() throws IOException {
        write2(clazz.attributes.size());

        for(Attribute attribute: clazz.attributes){
            write2(attribute.attribute_name_index);
            write4(attribute.attribute_length);
            write2(attribute.signature_index);
        }
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
