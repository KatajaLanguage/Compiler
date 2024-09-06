package katajaLang.jvm.bytecode.constant;

public class MethodRefInfo implements ConstantInfo{
    public static final short tag = 10;
    public final short class_index;
    public final short name_and_type_index;

    public MethodRefInfo(short class_index, short name_and_type_index) {
        this.class_index = class_index;
        this.name_and_type_index = name_and_type_index;
    }
}
