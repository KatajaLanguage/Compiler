package katajaLang.model;

public class Modifier {
    public final AccessFlag acc;

    public Modifier(AccessFlag acc){
        this.acc = acc;
    }

    public boolean isInvalidForClass(){
        return acc == AccessFlag.PROTECTED;
    }

    public boolean isInvalidForInterface(){
        return acc == AccessFlag.PROTECTED;
    }
}
