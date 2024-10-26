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

import java.util.ArrayList;

public final class CodeAttribute implements Attribute{
    public final int attribute_name_index;
    public int max_stack = 0;
    public int max_locals = 0;
    public final ArrayList<Integer> code = new ArrayList<>();

    public CodeAttribute(int attribute_name_index){
        this.attribute_name_index = attribute_name_index;
    }
}
