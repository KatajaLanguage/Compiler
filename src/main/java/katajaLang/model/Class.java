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

import katajaLang.compiler.Compiler;
import katajaLang.compiler.parsing.ParsingException;

import java.util.ArrayList;
import java.util.HashMap;

public class Class extends Compilable{

    public final HashMap<String, Field> fields = new HashMap<>();

    public Class(Uses uses, String src, Modifier mod, ArrayList<String> interfaces){
        super(uses, src, mod, interfaces);
    }

    @Override
    public void validateTypes(String className) {
        if(!interfaces.isEmpty()){
            String first = interfaces.get(0);

            if(!uses.containsAlias(first)) throw new ParsingException("Class "+first+" is not defined");

            Compilable c = Compiler.getInstance().getClass(uses.get(first));
            if(c instanceof Class){
                superClass = uses.get(first);
                interfaces.remove(0);

                if(!AccessFlag.canAccess(className, superClass, c.mod.acc)) throw new ParsingException("Class "+superClass+" is used out side of its scope");
                if(c.mod.finaly) throw new ParsingException(superClass+" is final and can't be extended");
            }
        }

        super.validateTypes(className);

        for(Field field: fields.values()) field.validateType(className);
    }
}
