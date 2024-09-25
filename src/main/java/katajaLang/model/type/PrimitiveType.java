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

package katajaLang.model.type;

import java.util.HashSet;
import java.util.Set;

public class PrimitiveType extends DataType{
    public static final Set<String> PRIMITIVES = new HashSet<>(8);

    public static final PrimitiveType INT = new PrimitiveType();
    public static final PrimitiveType SHORT = new PrimitiveType();
    public static final PrimitiveType LONG = new PrimitiveType();
    public static final PrimitiveType FLOAT = new PrimitiveType();
    public static final PrimitiveType DOUBLE = new PrimitiveType();
    public static final PrimitiveType BOOLEAN = new PrimitiveType();
    public static final PrimitiveType CHAR = new PrimitiveType();
    public static final PrimitiveType BYTE = new PrimitiveType();

    static{
        PRIMITIVES.add("int");
        PRIMITIVES.add("short");
        PRIMITIVES.add("long");
        PRIMITIVES.add("float");
        PRIMITIVES.add("double");
        PRIMITIVES.add("boolean");
        PRIMITIVES.add("char");
        PRIMITIVES.add("byte");
    }

    @Override
    public boolean equals(DataType type) {
        return type == this;
    }
}
