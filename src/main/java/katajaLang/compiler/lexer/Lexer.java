package katajaLang.compiler.lexer;

import katajaLang.compiler.parsing.TokenHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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

    private ArrayList<Token[]> result;
    private ArrayList<Token> currentLine;
    private TokenBuilder builder;
    private String filepath;
    private char[][] buffer;
    private int index;
    private int line;

    public Lexer(){
        builder = new TokenBuilder();
    }

    public TokenHandler lexFile(String filepath) throws FileNotFoundException {
        StringBuilder codeBuilder = new StringBuilder();
        Scanner sc = new Scanner(new File(filepath));

        while(sc.hasNextLine()){
            if(codeBuilder.length() > 0) codeBuilder.append("\n");
            codeBuilder.append(sc.nextLine().trim());
        }

        return lexCode(codeBuilder.toString(), filepath);
    }

    public TokenHandler lexCode(String code, String filepath){
        this.filepath = filepath;
        currentLine = new ArrayList<>();
        result = new ArrayList<>();
        index = 0;
        line = 0;

        bufferCode(code);

        return new TokenHandler(result.toArray(new Token[0][0]), filepath, 0);
    }

    private void bufferCode(String code){
        ArrayList<char[]> bufferBuilder = new ArrayList<>();

        for(String line:code.split("\n")) bufferBuilder.add(line.trim().toCharArray());

        buffer = bufferBuilder.toArray(new char[0][0]);
    }
}
