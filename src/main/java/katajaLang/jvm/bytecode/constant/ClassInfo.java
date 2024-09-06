package katajaLang.jvm.bytecode.constant;

public class ClassInfo implements ConstantInfo{
    public static final short tag = 7;
    public final short name_index;

    public ClassInfo(short name_index){
        this.name_index = name_index;
    }
}
