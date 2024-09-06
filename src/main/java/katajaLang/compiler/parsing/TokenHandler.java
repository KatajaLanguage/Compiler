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

package katajaLang.compiler.parsing;

import katajaLang.compiler.lexer.Token;
import katajaLang.compiler.lexer.TokenType;

import java.util.Arrays;

public final class TokenHandler {

    private final Token[][] token;
    private final String file;
    private final int lineOffset;
    private int index;
    private int line;

    public TokenHandler(Token[][] token, String file, int lineOffset){
        this.token = token;
        this.file = file;
        this.lineOffset = lineOffset;
        line = 0;
        index = -1;
    }

    public Token next(){
        if(index + 1 < token[line].length){
            index++;
            return token[line][index];
        }else{
            while(++line < token.length){
                if(token[line].length > 0){
                    index = 0;
                    return token[line][index];
                }
            }
        }
        err("Expected Token got nothing");
        return new Token(null, null); // unreachable statement
    }

    public Token current(){
        if(index == -1 || index >= token[line].length) err("Expected Token got nothing");
        return token[line][index];
    }

    public Token last(){
        if(index > 0){
            index--;
            return token[line][index];
        }else{
            while(--line >= 0){
                if(token[line].length > 0){
                    index = token[line].length - 1;
                    return token[line][index];
                }
            }
        }
        index = -1;
        line = 0;
        return new Token(null, null);
    }

    public Token assertToken(String...strings){
        Token t = next();

        for(String string:strings) if(t.equals(string)) return t;

        err("Expected one of "+Arrays.toString(strings)+" got "+t.value);
        return new Token(null, null); // unreachable statement
    }

    public Token assertTokenTypes(TokenType...types){
        Token t = next();

        for(TokenType type:types) if(t.equals(type)) return t;

        err("Expected one of "+ Arrays.toString(types)+" got "+t.type);
        return new Token(null, null); // unreachable statement
    }

    public Token assertToken(TokenType type, String...strings){
        Token t = next();

        if(t.equals(type)) return t;

        for(String string:strings) if(t.equals(string)) return t;

        err("Expected one of "+type+(strings.length != 0 ? ", "+Arrays.toString(strings) : "")+" got "+t.value);
        return new Token(null, null); // unreachable statement
    }

    public Token assertToken(TokenType type1, TokenType type2,String...strings){
        Token t = next();

        if(t.equals(type1)) return t;
        if(t.equals(type2)) return t;

        for(String string:strings) if(t.equals(string)) return t;

        err("Expected one of "+type1+", "+type2+(strings.length != 0 ? ", "+Arrays.toString(strings) : "")+" got "+t.value);
        return new Token(null, null); // unreachable statement
    }

    public void assertEndOfStatement(){
        if(index + 1 != token[line].length) assertToken(";");
    }

    public void assertHasNext(){
        next();
        last();
    }

    public boolean isNext(String string){
        if(!hasNext()) return false;
        if(next().equals(string)) return true;

        last();
        return false;
    }

    public boolean isNext(TokenType type){
        if(next().equals(type)) return true;

        last();
        return false;
    }

    public boolean isEndOfStatement(){
        if(index + 1 == token[line].length) return true;

        return isNext(";");
    }

    public boolean hasNext(){
        String index = getIndex();
        if(line < token.length && this.index + 1 < token[line].length){
            setIndex(index);
            return true;
        }else{
            while(++line < token.length){
                if(token[line].length > 0){
                    setIndex(index);
                    return true;
                }
            }
        }
        setIndex(index);
        return false;
    }

    public String getIndex(){
        return line +":"+index;
    }

    public void setIndex(String index){
        String[] s = index.split(":");
        line = Integer.parseInt(s[0]);
        this.index = Integer.parseInt(s[1]);
    }

    public int getLine(){
        if(lineOffset > 0) return lineOffset + line;
        return line + 1;
    }

    private void err(String message) throws ParsingException{
        int line = getLine();

        StringBuilder sb = new StringBuilder(message);
        sb.append(" near ");

        String index = getIndex();
        try{
            if(last() == null) throw new ParsingException("");
            sb.append(current());
            next();
        }catch(ParsingException ignored){
            setIndex(index);
        }

        if(line < 0 || line >= token.length || this.index == -1 || this.index >= token[line].length) throw new ParsingException(sb+" at "+file+":"+getLine());

        sb.append(" ").append(current()).append(" ");
        if(hasNext()) sb.append(next());

        throw new ParsingException(sb+" at "+file+":"+getLine());
    }
}
