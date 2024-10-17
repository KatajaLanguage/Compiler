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

public final class Field {

    public final Uses uses;
    public final Modifier mod;
    public final DataType type;

    public Field(Uses uses, Modifier mod, DataType type){
        this.uses = uses;
        this.mod = mod;
        this.type = type;
    }

    public void validateType(String className){
        DataType dataType = type.ignoreArray();

        if(dataType instanceof ComplexType){
            ((ComplexType) dataType).validate(uses, className);
        }
    }
}
