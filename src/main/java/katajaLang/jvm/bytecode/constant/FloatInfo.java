package katajaLang.jvm.bytecode.constant;

public class FloatInfo implements ConstantInfo{
    public static final short tag = 3;
    public final float value;

    public FloatInfo(float value){
        this.value = value;
    }
}
