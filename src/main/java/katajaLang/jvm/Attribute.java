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

package katajaLang.jvm;

public final class Attribute{
    public final short attribute_name_index;
    public final int attribute_length;
    public final short signature_index;

    public Attribute(short attribute_name_index, int attribute_length, short signature_index){
        this.attribute_name_index = attribute_name_index;
        this.attribute_length = attribute_length;
        this.signature_index = signature_index;
    }
}
