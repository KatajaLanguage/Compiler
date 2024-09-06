package katajaLang.jvm.bytecode.constant;

public class StringInfo implements ConstantInfo{
    public static final short tag = 8;
    public final short string_index;

    public StringInfo(short string_index){
        this.string_index = string_index;
    }
}
