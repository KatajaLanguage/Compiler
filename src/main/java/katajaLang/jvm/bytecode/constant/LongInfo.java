package katajaLang.jvm.bytecode.constant;

public class LongInfo implements ConstantInfo{
    public static final short tag = 5;
    public final long value;

    public LongInfo(long value){
        this.value = value;
    }
}
