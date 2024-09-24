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

public class ComplexType extends DataType{

    public final String type;

    public ComplexType(String type){
        this.type = type;
    }

    @Override
    public boolean equals(DataType type) {
        return type instanceof ComplexType && ((ComplexType) type).type.equals(this.type);
    }
}
