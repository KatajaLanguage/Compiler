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

import katajaLang.model.Class;
import katajaLang.model.Compilable;
import katajaLang.model.Interface;
import katajaLang.model.Method;

final class JVMClassBuilder {

    static JVMWritableClass buildWritableClass(Compilable compilable, String clazzName){
        JVMWritableClass clazz = new JVMWritableClass(compilable.mod, clazzName, compilable instanceof Interface, compilable.superClass, compilable.interfaces.toArray(new String[0]), compilable.src);

        if(compilable instanceof Class){
            for(String fieldName:((Class) compilable).fields.keySet()) clazz.addField(fieldName, ((Class) compilable).fields.get(fieldName));
            for(Method.MethodDesc desc: ((Class) compilable).methods.keySet()) clazz.addMethod(desc.name, ((Class) compilable).methods.get(desc));
        }else if(compilable instanceof Interface){
            for(Method.MethodDesc desc: ((Interface) compilable).methods.keySet()) clazz.addMethod(desc.name, ((Interface) compilable).methods.get(desc));
        }

        return clazz;
    }
}
