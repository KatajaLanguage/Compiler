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
    public final ArrayList<String> interfaces;
    public String superClass;

    public Class(Uses uses, Modifier mod, ArrayList<String> interfaces){
        super(uses, mod);
        this.interfaces = interfaces;
    }

    @Override
    public void validateTypes(String className) {
        for(int i = 0;i < interfaces.size();i++){
            String clazz = interfaces.get(i);
            if(!uses.containsAlias(clazz)) throw new ParsingException("Class "+clazz+" is not defined");

            interfaces.remove(i);
            interfaces.add(i, uses.get(clazz));

            Compilable c = Compiler.getInstance().getClass(interfaces.get(i));

            if(!(c instanceof Interface)) throw new ParsingException("Expected interface got Class "+c);
        }

        for(Field field: fields.values()) field.validateType(className);
    }
}
