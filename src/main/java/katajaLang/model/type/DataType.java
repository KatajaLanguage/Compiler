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

import static katajaLang.model.type.PrimitiveType.*;

public abstract class DataType {

    public abstract boolean equals(DataType type);

    public DataType ignoreArray(){
        if(this instanceof ArrayType) return ((ArrayType) this).type.ignoreArray();

        return this;
    }

    public static DataType ofString(String type){
        switch(type){
            case "int":
                return INT;
            case "short":
                return SHORT;
            case "long":
                return LONG;
            case "float":
                return FLOAT;
            case "double":
                return DOUBLE;
            case "boolean":
                return BOOLEAN;
            case "char":
                return CHAR;
            case "byte":
                return BYTE;
            default:
                return new ComplexType(type);
        }
    }
}
