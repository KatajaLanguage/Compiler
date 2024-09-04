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
}
