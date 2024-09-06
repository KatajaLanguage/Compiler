package katajaLang.jvm.bytecode.constant;

public class NameAndTypeInfo implements ConstantInfo{
    public static final short tag = 10;
    public final short name_index;
    public final short descriptor_index;

    public NameAndTypeInfo(short name_index, short descriptor_index){
        this.name_index = name_index;
        this.descriptor_index = descriptor_index;
    }
}
