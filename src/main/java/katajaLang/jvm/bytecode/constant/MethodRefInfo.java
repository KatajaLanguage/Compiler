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

package katajaLang.jvm.bytecode.constant;

public class MethodRefInfo implements ConstantInfo{
    public static final short tag = 10;
    public final short class_index;
    public final short name_and_type_index;

    public MethodRefInfo(short class_index, short name_and_type_index) {
        this.class_index = class_index;
        this.name_and_type_index = name_and_type_index;
    }
}
