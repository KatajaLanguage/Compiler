package katajaLang.jvm.bytecode.constant;

public class DynamicInfo implements ConstantInfo{
    public static final short tag = 17;
    public final short bootstrap_method_attr_index;
    public final short name_and_type_index;

    public DynamicInfo(short bootstrap_method_attr_index, short name_and_type_index){
        this.bootstrap_method_attr_index = bootstrap_method_attr_index;
        this.name_and_type_index = name_and_type_index;
    }
}
