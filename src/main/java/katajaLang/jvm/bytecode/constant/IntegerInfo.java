package katajaLang.jvm.bytecode.constant;

public class IntegerInfo implements ConstantInfo{
    public static final short tag = 3;
    public final int value;

    public IntegerInfo(int value){
        this.value = value;
    }
}
