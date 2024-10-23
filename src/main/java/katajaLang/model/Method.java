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

package katajaLang.model;

import katajaLang.model.type.ComplexType;
import katajaLang.model.type.DataType;

public final class Method {

    public static class MethodDesc{
        public final String name;
        public final DataType type;
        public final DataType[] parameters;

        public MethodDesc(String name, DataType type, DataType[] parameters){
            this.name = name;
            this.type = type;
            this.parameters = parameters;
        }

        public boolean equals(MethodDesc obj) {
            if(!name.equals(obj.name)) return false;
            if(parameters.length != obj.parameters.length) return false;
            for(int i=0;i<parameters.length;i++) if(!parameters[i].equals(obj.parameters[i])) return false;
            return true;
        }
    }

    public final Uses uses;
    public final Modifier mod;
    public final MethodDesc desc;

    public Method(Uses uses, Modifier mod, MethodDesc desc){
        this.uses = uses;
        this.mod = mod;
        this.desc = desc;
    }

    public void validateType(String className){
        DataType dataType = desc.type.ignoreArray();

        if(dataType instanceof ComplexType)
            ((ComplexType) dataType).validate(uses, className);


    }
}
