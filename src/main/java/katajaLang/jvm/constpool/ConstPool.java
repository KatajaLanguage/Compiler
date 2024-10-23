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

package katajaLang.jvm.constpool;

import katajaLang.model.Method;
import katajaLang.model.type.ArrayType;
import katajaLang.model.type.ComplexType;
import katajaLang.model.type.DataType;
import katajaLang.model.type.PrimitiveType;

import java.util.ArrayList;

public final class ConstPool {

    private final ArrayList<ConstantInfo> entries = new ArrayList<>();

    public int add(ConstantInfo info){
        entries.add(info);
        return entries.size();
    }

    public int addTypeDescriptor(DataType type){
        return addUtf8Info(toDesc(type));
    }

    public int addMethodDescriptor(Method.MethodDesc desc){
        StringBuilder result = new StringBuilder("(");

        for(Method.Parameter parameter: desc.parameters) result.append(toDesc(parameter.type));

        result.append(")").append(toDesc(desc.type));

        return addUtf8Info(result.toString());
    }

    private String toDesc(DataType type){
        StringBuilder desc = new StringBuilder();

        while(type instanceof ArrayType){
            desc.append("[");
            type = ((ArrayType) type).type;
        }

        if(type instanceof ComplexType) desc.append("L").append(((ComplexType) type).type).append(";");
        else if(type instanceof PrimitiveType){
            if(type.equals(PrimitiveType.BYTE)) desc.append("B");
            else if(type.equals(PrimitiveType.CHAR)) desc.append("C");
            else if(type.equals(PrimitiveType.INT)) desc.append("I");
            else if(type.equals(PrimitiveType.DOUBLE)) desc.append("D");
            else if(type.equals(PrimitiveType.FLOAT)) desc.append("F");
            else if(type.equals(PrimitiveType.LONG)) desc.append("J");
            else if(type.equals(PrimitiveType.SHORT)) desc.append("S");
            else if(type.equals(PrimitiveType.BOOLEAN)) desc.append("Z");
            else if(type.equals(PrimitiveType.VOID)) desc.append("V");
        }

        return desc.toString();
    }

    public int addClassInfo(String clazz){
        return entries.stream().filter(ClassInfo.class::isInstance).map(ClassInfo.class::cast).filter(obj -> ((Utf8Info)entries.get(obj.name_index - 1)).value.equals(clazz)).findFirst().map(entries::indexOf).orElseGet(() -> add(new ClassInfo((short) addUtf8Info(clazz))));
    }

    public int addUtf8Info(String string){
        return entries.stream().filter(Utf8Info.class::isInstance).map(Utf8Info.class::cast).filter(obj -> obj.value.equals(string)).findFirst().map(entries::indexOf).orElseGet(() -> add(new Utf8Info(string)));
    }

    public ConstantInfo[] getEntries(){
        return entries.toArray(new ConstantInfo[0]);
    }

    public int getSize(){
        return entries.size() + 1;
    }
}
