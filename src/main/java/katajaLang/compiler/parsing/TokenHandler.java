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

package katajaLang.compiler.parsing;

import katajaLang.compiler.lexer.Token;
import katajaLang.compiler.lexer.TokenType;

import java.util.Arrays;

/**
 * Class for handling tokens
 */
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

    /**
     * Advances to the next Token and returns it
     */
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

    /**
     * Returns the current Token
     */
    public Token current(){
        if(index == -1 || index >= token[line].length) err("Expected Token got nothing");
        return token[line][index];
    }

    /**
     * Gets back to the last Token and returns it
     */
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

    /**
     * Assert that the next Token equals one of the given values. If method succeeded, it returns the Token
     */
    public Token assertToken(String...strings){
        Token t = next();

        for(String string:strings) if(t.equals(string)) return t;

        err("Expected one of "+Arrays.toString(strings)+" got "+t.value);
        return new Token(null, null); // unreachable statement
    }

    /**
     * Assert that the next Token has one of the given Types. If method succeeded, it returns the Token
     */
    public Token assertTokenTypes(TokenType...types){
        Token t = next();

        for(TokenType type:types) if(t.equals(type)) return t;

        err("Expected one of "+ Arrays.toString(types)+" got "+t.type);
        return new Token(null, null); // unreachable statement
    }

    /**
     * Assert that the next Token equals one of the given types or the given value. If method succeeded, it returns the Token
     */
    public Token assertToken(TokenType type, String...strings){
        Token t = next();

        if(t.equals(type)) return t;

        for(String string:strings) if(t.equals(string)) return t;

        err("Expected one of "+type+(strings.length != 0 ? ", "+Arrays.toString(strings) : "")+" got "+t.value);
        return new Token(null, null); // unreachable statement
    }

    /**
     * Assert that the next Token equals one of the given types or one of the given values. If method succeeded, it returns the Token
     */
    public Token assertToken(TokenType type1, TokenType type2,String...strings){
        Token t = next();

        if(t.equals(type1)) return t;
        if(t.equals(type2)) return t;

        for(String string:strings) if(t.equals(string)) return t;

        err("Expected one of "+type1+", "+type2+(strings.length != 0 ? ", "+Arrays.toString(strings) : "")+" got "+t.value);
        return new Token(null, null); // unreachable statement
    }

    /**
     * Assert that the next Token is a ';' or that the Handler reached the end of the Line
     */
    public void assertEndOfStatement(){
        if(index + 1 != token[line].length) assertToken(";");
    }

    /**
     * Assert that a next Token is available
     */
    public void assertHasNext(){
        next();
        last();
    }

    /**
     * Returns whether the next Token, if it is available, has the given value
     */
    public boolean isNext(String string){
        if(!hasNext()) return false;
        if(next().equals(string)) return true;

        last();
        return false;
    }

    /**
     * Returns whether the next Token, if it is available, has the given type
     */
    public boolean isNext(TokenType type){
        if(next().equals(type)) return true;

        last();
        return false;
    }

    /**
     * Returns whether the next Token is a ';' or that the Handler reached the end of the Line
     */
    public boolean isEndOfStatement(){
        if(index + 1 == token[line].length) return true;

        return isNext(";");
    }

    /**
     * Returns whether the handler has a next Token
     */
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

    /**
     * Returns a String that represent the current Position of the Handler
     */
    public String getIndex(){
        return line +":"+index;
    }

    /**
     * Restores the position of the Handler to the given Position
     */
    public void setIndex(String index){
        String[] s = index.split(":");
        line = Integer.parseInt(s[0]);
        this.index = Integer.parseInt(s[1]);
    }

    /**
     * Returns the Line of the current Position of the Handler, within the original File
     */
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
