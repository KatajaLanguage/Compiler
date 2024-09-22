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

package katajaLang.model;

import java.util.HashSet;
import java.util.Set;

public final class Type {

    public final static Set<String> PRIMITIVES = new HashSet<>();

    static{
        PRIMITIVES.add("int");
        PRIMITIVES.add("short");
        PRIMITIVES.add("long");
        PRIMITIVES.add("double");
        PRIMITIVES.add("float");
        PRIMITIVES.add("char");
        PRIMITIVES.add("byte");
        PRIMITIVES.add("boolean");
    }

    public final String clazz;
    public final boolean primitive;

    public Type(String clazz){
        this.clazz = clazz;
        this.primitive = PRIMITIVES.contains(clazz);
    }
}
