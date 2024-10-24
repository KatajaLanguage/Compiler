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

package katajaLang.jvm.attribute;

public final class MethodParametersAttribute implements Attribute{
    public final int attribute_name_index;
    public final int[] name_indexes;
    public final int[] access_flags;

    public MethodParametersAttribute(int attribute_name_index, int[] name_indexes, int[] access_flags){
        this.attribute_name_index = attribute_name_index;
        this.name_indexes = name_indexes;
        this.access_flags = access_flags;
    }
}
