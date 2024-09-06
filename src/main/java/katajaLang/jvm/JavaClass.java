package katajaLang.jvm;

import katajaLang.jvm.bytecode.Flag;
import katajaLang.model.AccessFlag;
import katajaLang.model.Class;

public class JavaClass extends Class {

    public JavaClass(AccessFlag accessFlag) {
        super(accessFlag);
    }

    public int getAccessFlag(){
        int accessFlag = 0;

        switch(this.accessFlag){
            case PUBLIC:
                accessFlag += Flag.PUBLIC;
                break;
            case PROTECTED:
                accessFlag += Flag.PROTECTED;
                break;
            case PRIVATE:
                accessFlag += Flag.PRIVATE;
                break;
        }

        return accessFlag;
    }
}
