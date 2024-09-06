package katajaLang.jvm.bytecode.constant;

public class PackageInfo implements ConstantInfo{
    public static final short tag = 20;
    public final short name_index;

    public PackageInfo(short name_index){
        this.name_index = name_index;
    }
}
