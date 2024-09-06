package katajaLang.jvm.bytecode.constant;

public class DoubleInfo implements ConstantInfo{
    public static final short tag = 6;
    public final double value;

    public DoubleInfo(double value){
        this.value = value;
    }
}
