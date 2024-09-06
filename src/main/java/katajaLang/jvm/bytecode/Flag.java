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

package katajaLang.jvm.bytecode;

public interface Flag {
    int PUBLIC       = 0x0001;
    int PRIVATE      = 0x0002;
    int PROTECTED    = 0x0004;
    int STATIC       = 0x0008;
    int FINAL        = 0x0010;
    int SUPER        = 0x0020;
    int SYNCHRONIZED = 0x0020;
    int VOLATILE     = 0x0040;
    int BRIDGE       = 0x0040;
    int TRANSIENT    = 0x0080;
    int VARARGS      = 0x0080;
    int NATIVE       = 0x0100;
    int INTERFACE    = 0x0200;
    int ABSTRACT     = 0x0400;
    int STRICT       = 0x0800;
    int SYNTHETIC    = 0x1000;
    int ANNOTATION   = 0x2000;
    int ENUM         = 0x4000;
    int MODULE       = 0x8000;
}
