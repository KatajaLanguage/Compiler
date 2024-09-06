package katajaLang.jvm.bytecode.constant;

public class FieldRefInfo implements ConstantInfo{
    public static final short tag = 9;
    public final short class_index;
    public final short name_and_type_index;

    public FieldRefInfo(short class_index, short name_and_type_index) {
        this.class_index = class_index;
        this.name_and_type_index = name_and_type_index;
    }
}
