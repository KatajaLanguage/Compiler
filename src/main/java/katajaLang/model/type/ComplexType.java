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

package katajaLang.model.type;

import katajaLang.compiler.Compiler;
import katajaLang.compiler.parsing.ParsingException;
import katajaLang.model.AccessFlag;
import katajaLang.model.Compilable;
import katajaLang.model.Uses;

public class ComplexType extends DataType{

    public String type;

    public ComplexType(String type){
        this.type = type;
    }

    public void validate(Uses uses, String clazz){
        if(!uses.containsAlias(type)) throw new ParsingException("Unknown Type "+type);
        type = uses.get(type);
        Compilable c = Compiler.getInstance().getClass(type);
        if(c == null) throw new ParsingException("Class "+type+" does not exist");
        if(!AccessFlag.canAccess(clazz, type, c.mod.acc)) throw new ParsingException("Class "+type+" is out of its scope");
    }

    @Override
    public boolean equals(DataType type) {
        return type instanceof ComplexType && ((ComplexType) type).type.equals(this.type);
    }
}
