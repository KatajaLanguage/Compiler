package katajaLang.jvm.bytecode.constant;

public class MethodHandleInfo implements ConstantInfo{
    public static final short tag = 15;
    public final short reference_kind;
    public final short reference_index;

    public MethodHandleInfo(short reference_kind, short reference_index){
        this.reference_kind = reference_kind;
        this.reference_index = reference_index;
    }
}
