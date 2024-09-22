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

package katajaLang.compiler.lexer;

public final class Token {
    public final TokenType type;
    public final String value;

    public Token(String value, TokenType type){
        this.type = type;
        this.value = value;
    }

    public boolean equals(Token token){
        return token.type == type && token.value.equals(value);
    }

    public boolean equals(TokenType tokenType){
        return tokenType == type;
    }

    public boolean equals(String string){
        return value.equals(string);
    }

    @Override
    public String toString() {
        return value;
    }
}
