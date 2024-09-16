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

import katajaLang.compiler.parsing.TokenHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public final class Lexer {

    private final class TokenBuilder{
        private StringBuilder valueBuilder;
        private TokenType type;

        TokenBuilder(){
            valueBuilder = new StringBuilder();
            type = null;
        }

        void setType(TokenType type){
            this.type = type;
        }

        void append(char c){
            valueBuilder.append(c);
        }

        Token build(){
            Token token = new Token(valueBuilder.toString(), type);
            valueBuilder = new StringBuilder();
            type = null;
            return token;
        }

        boolean isEmpty(){
            return type == null && valueBuilder.length() == 0;
        }
    }

    public static final Set<Character> OPERATORS;

    static{
        OPERATORS = new HashSet<>();
        OPERATORS.add('+');
        OPERATORS.add('-');
        OPERATORS.add('*');
        OPERATORS.add('/');
        OPERATORS.add('^');
        OPERATORS.add('%');
        OPERATORS.add('=');
        OPERATORS.add('&');
        OPERATORS.add('|');
        OPERATORS.add('<');
        OPERATORS.add('>');
        OPERATORS.add('!');
        OPERATORS.add('~');
    }

    private ArrayList<Token[]> result;
    private ArrayList<Token> currentLine;
    private TokenBuilder builder;
    private String filepath;
    private char[][] buffer;
    private int index;
    private int line;
    private int lineOffset;

    public Lexer(){
        builder = new TokenBuilder();
    }

    public TokenHandler lexFile(File file) throws FileNotFoundException, LexingException {
        StringBuilder codeBuilder = new StringBuilder();
        Scanner sc = new Scanner(file);

        while(sc.hasNextLine()){
            if(codeBuilder.length() > 0) codeBuilder.append("\n");
            codeBuilder.append(sc.nextLine().trim());
        }

        return lexCode(codeBuilder.toString(), file.getPath(), 0);
    }

    public TokenHandler lexCode(String code, String filepath, int lineOffset) throws LexingException{
        this.filepath = filepath;
        this.lineOffset = lineOffset;
        currentLine = new ArrayList<>();
        result = new ArrayList<>();
        index = 0;
        line = 0;

        bufferCode(code);

        return lexBuffer();
    }

    private TokenHandler lexBuffer() throws LexingException{
        while(hasNext()){
            if(is(' ') || is('\t') || is('\r')){
                skipChar();
            }else if(is('#')){
                skipChar();
                if(is('#')){
                    while(hasNext()){
                        if(is('#')){
                            skipChar();
                            if(is('#')){
                                skipChar();
                                break;
                            }
                        }else skipChar();
                    }
                }else{
                    while(hasNext() && !is('#') && !is('\n')) skipChar();
                    if(is('#')) skipChar();
                }
            }else if(isOperator()){
                while (isOperator()) consumeChar();
                setType(TokenType.OPERATOR);
                buildToken();
            }else if(isLetter()){
                while (isLetter() || isOperator() || is('_')) consumeChar();
                setType(TokenType.IDENTIFIER);
                buildToken();
            }else if(isDigit()){
                while(isDigit()) consumeChar();

                if(is('.')){
                    consumeChar();
                    while(isDigit()) consumeChar();
                    setType(TokenType.DOUBLE);
                }else setType(TokenType.INTEGER);

                if(is('d')){
                    consumeChar();
                    setType(TokenType.DOUBLE);
                }else if(is('f')){
                    consumeChar();
                    setType(TokenType.FLOAT);
                }else if(is('s')){
                    consumeChar();
                    if(builder.type == TokenType.DOUBLE) err("illegal type for value "+builder.valueBuilder.toString());
                    setType(TokenType.SHORT);
                }else if(is('i')){
                    consumeChar();
                    if(builder.type == TokenType.DOUBLE) err("illegal type for value "+builder.valueBuilder.toString());
                    setType(TokenType.INTEGER);
                }else if(is('l')){
                    consumeChar();
                    if(builder.type == TokenType.DOUBLE) err("illegal type for value "+builder.valueBuilder.toString());
                    setType(TokenType.LONG);
                }else if(is('b')){
                    consumeChar();
                    if(builder.type == TokenType.DOUBLE) err("illegal type for value "+builder.valueBuilder.toString());
                    setType(TokenType.BYTE);
                }else if(is('c')){
                    consumeChar();
                    if(builder.type == TokenType.DOUBLE) err("illegal type for value "+builder.valueBuilder.toString());
                    setType(TokenType.CHAR);
                }

                buildToken();
            }else if(is('\n')){
                skipChar();
                newLine();
            }else if(is('\'')){
                consumeChar();
                consumeChar();
                if(!is('\'')) err("Expected ' after "+builder.valueBuilder.toString());
                consumeChar();
                buildToken();
            }else if(is('"')){
                consumeChar();
                while(hasNext() && !is('"') && !is('\n')) consumeChar();

                if(is('\n') || !hasNext()) err("Expected \"");
                consumeChar();
                setType(TokenType.STRING);
                buildToken();
            }else{
                consumeChar();
                setType(TokenType.SINGLE);
                buildToken();
            }
        }
        return new TokenHandler(result.toArray(new Token[0][0]), filepath, lineOffset);
    }

    private void bufferCode(String code){
        ArrayList<char[]> bufferBuilder = new ArrayList<>();

        for(String line:code.split("\n")) bufferBuilder.add((line.trim() + "\n").toCharArray());

        buffer = bufferBuilder.toArray(new char[0][0]);
    }

    private boolean hasNext(){
        return line < buffer.length && index < buffer[line].length;
    }

    private boolean isOperator(){
        return hasNext() && OPERATORS.contains(buffer[line][index]);
    }

    private boolean isDigit(){
        return hasNext() && Character.isDigit(buffer[line][index]);
    }

    private boolean isLetter(){
        return hasNext() && Character.isLetter(buffer[line][index]);
    }

    private boolean is(char c){
        return hasNext() && buffer[line][index] == c;
    }

    private void consumeChar(){
        builder.append(buffer[line][index]);
        index++;

        if(index == buffer[line].length){
            line++;
            index = 0;
        }
    }

    private void skipChar(){
        index++;

        if(index == buffer[line].length){
            line++;
            index = 0;
        }
    }

    private void setType(TokenType type){
        builder.setType(type);
    }

    private void buildToken(){
        currentLine.add(builder.build());
    }

    private void newLine(){
        result.add(currentLine.toArray(new Token[0]));
        currentLine = new ArrayList<>();
    }

    private void err(String message) throws LexingException{
        throw new LexingException(message+" at "+filepath+":"+(lineOffset+line));
    }
}
