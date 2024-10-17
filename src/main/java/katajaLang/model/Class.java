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

import java.util.HashMap;

public class Class extends Compilable{

    public final HashMap<String, Field> fields = new HashMap<>();

    public Class(Uses uses, Modifier mod){
        super(uses, mod);
    }

    @Override
    public void validateTypes(String className) {
        for(Field field: fields.values()) field.validateType(className);
    }
}
