package katajaLang.jvm.bytecode.constant;

public class MethodTypeInfo implements ConstantInfo{
    public static final short tag = 16;
    public final short descriptor_index;

    public MethodTypeInfo(short descriptor_index){
        this.descriptor_index = descriptor_index;
    }
}
