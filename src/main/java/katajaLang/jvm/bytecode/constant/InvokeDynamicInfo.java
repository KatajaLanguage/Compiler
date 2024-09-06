package katajaLang.jvm.bytecode.constant;

public class InvokeDynamicInfo implements ConstantInfo{
    public static final short tag = 18;
    public final short bootstrap_method_attr_index;
    public final short name_and_type_index;

    public InvokeDynamicInfo(short bootstrap_method_attr_index, short name_and_type_index){
        this.bootstrap_method_attr_index = bootstrap_method_attr_index;
        this.name_and_type_index = name_and_type_index;
    }
}
