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

    public static final class MethodDesc{
        public final String name;
        public final DataType type;
        public final Parameter[] parameters;

        public MethodDesc(String name, DataType type, Parameter[] parameters){
            this.name = name;
            this.type = type;
            this.parameters = parameters;
        }

        public boolean equals(MethodDesc obj) {
            if(!name.equals(obj.name)) return false;
            if(parameters.length != obj.parameters.length) return false;
            for(int i=0;i<parameters.length;i++) if(!parameters[i].type.equals(obj.parameters[i].type)) return false;
            return true;
        }
    }

    public static final class Parameter{
        public final String name;
        public final DataType type;

        public Parameter(String name, DataType type){
            this.name = name;
            this.type = type;
        }

        public boolean equals(Parameter obj) {
            return name.equals(obj.name);
        }
    }

    public final Uses uses;
    public final Modifier mod;
    public final MethodDesc desc;
    public final String code;

    public Method(Uses uses, Modifier mod, MethodDesc desc, String code){
        this.uses = uses;
        this.mod = mod;
        this.desc = desc;
        this.code = code;
    }

    public void validateType(String className){
        DataType dataType = desc.type.ignoreArray();

        if(dataType instanceof ComplexType)
            ((ComplexType) dataType).validate(uses, className);


    }
}
