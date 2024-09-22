/*
 * Copyright (C) 2024 Xaver Weste
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License 3.0 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package katajaLang.jvm.writing;

import katajaLang.jvm.bytecode.Flag;
import katajaLang.jvm.constpool.ClassInfo;
import katajaLang.jvm.constpool.ConstantInfo;
import katajaLang.jvm.constpool.Utf8Info;
import katajaLang.model.Modifier;

import java.util.ArrayList;

final class JVMWritableClass {

    private final Modifier mod;
    private final ArrayList<ConstantInfo> constPool;
    private final boolean isInterface;

    JVMWritableClass(Modifier mod, String name, boolean isInterface){
        this.mod = mod;
        this.isInterface = isInterface;

        constPool = new ArrayList<>();
        constPool.add(new ClassInfo((short) 3));
        constPool.add(new ClassInfo((short) 4));
        constPool.add(new Utf8Info(name));
        constPool.add(new Utf8Info("java/lang/Object"));
    }

    int getAccessFlag(){
        int acc = 0;

        switch(mod.acc){
            case PUBLIC:
                acc += Flag.PUBLIC;
                break;
            case PRIVATE:
                acc += Flag.PRIVATE;
                break;
            case PROTECTED:
                acc += Flag.PROTECTED;
                break;
        }

        if(isInterface) acc += Flag.INTERFACE;
        if(mod.abstrakt) acc += Flag.ABSTRACT;
        if(mod.finaly) acc += Flag.FINAL;

        return acc;
    }

    int getConstPoolSize(){
        return constPool.size() + 1;
    }

    ConstantInfo[] getConstPool(){
        return constPool.toArray(new ConstantInfo[0]);
    }
}
