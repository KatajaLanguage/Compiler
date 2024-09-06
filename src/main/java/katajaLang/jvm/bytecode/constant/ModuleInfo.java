package katajaLang.jvm.bytecode.constant;

public class ModuleInfo implements ConstantInfo{
    public static final short tag = 19;
    public final short name_index;

    public ModuleInfo(short name_index){
        this.name_index = name_index;
    }
}
