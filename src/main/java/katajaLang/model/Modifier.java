package katajaLang.model;

public class Modifier {
    public final AccessFlag acc;
    public final boolean abstrakt;
    public final boolean finaly;

    public Modifier(AccessFlag acc, boolean abstrakt, boolean finaly){
        this.acc = acc;
        this.abstrakt = abstrakt;
        this.finaly = finaly;
    }

    public boolean isInvalidForClass(){
        return acc == AccessFlag.PROTECTED || (abstrakt && finaly);
    }

    public boolean isInvalidForInterface(){
        return acc == AccessFlag.PROTECTED || finaly;
    }
}
