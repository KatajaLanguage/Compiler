/*
 * Copyright (C) 2024 ni271828mand
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
import katajaLang.jvm.constpool.ConstPool;
import katajaLang.jvm.infos.FieldInfo;
import katajaLang.model.Field;
import katajaLang.model.Modifier;

import java.util.ArrayList;

final class JVMWritableClass {

    private final ArrayList<FieldInfo> fields = new ArrayList<>();

    private final Modifier mod;
    private final ConstPool constPool;
    private final boolean isInterface;

    public final int this_class;
    public final int super_class;

    JVMWritableClass(Modifier mod, String name, boolean isInterface){
        this.mod = mod;
        this.isInterface = isInterface;

        constPool = new ConstPool();

        this_class = constPool.addClassInfo(name);
        super_class = constPool.addClassInfo("java/lang/Object");
    }

    void addField(String name, Field field){
        fields.add(new FieldInfo(Flag.getAccessFlag(field.mod), constPool.addUtf8Info(name), constPool.addTypeDescriptor(field.type)));
    }

    int getAccessFlag(){
        int acc = Flag.getAccessFlag(mod);

        if(isInterface) acc += Flag.INTERFACE;

        return acc;
    }

    ConstPool getConstPool(){
        return constPool;
    }

    FieldInfo[] getFields(){
        return fields.toArray(new FieldInfo[0]);
    }
}
