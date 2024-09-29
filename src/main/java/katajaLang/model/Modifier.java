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

public class Modifier {
    public final AccessFlag acc;
    public final boolean abstrakt;
    public final boolean finaly;

    public Modifier(AccessFlag acc, boolean abstrakt, boolean finaly){
        this.acc = acc;
        this.abstrakt = abstrakt;
        this.finaly = finaly;
    }

    public boolean isInvalidForClass(){
        return acc == AccessFlag.PROTECTED || (abstrakt && finaly);
    }

    public boolean isInvalidForInterface(){
        return acc == AccessFlag.PROTECTED || finaly;
    }

    public boolean isInvalidForField(){
        return finaly || abstrakt;
    }
}
