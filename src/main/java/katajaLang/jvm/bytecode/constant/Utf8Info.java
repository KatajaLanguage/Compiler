package katajaLang.jvm.bytecode.constant;

public class Utf8Info implements ConstantInfo{
    public static final short tag = 1;
    public final String value;

    public Utf8Info(String value){
        this.value = value;
    }
}
