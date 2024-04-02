package johnengine.extra.jegmd.tokenizer;

import java.util.ArrayList;
import java.util.List;

import johnengine.extra.jegmd.Elements;
import johnengine.extra.jegmd.Properties;
import johnengine.extra.jegmd.SpecialCharacters;
import johnengine.testing.DebugUtils;

public class Tokenizer {
    
    public static final class Token {
        public TokenType type;
        public String value;
        
        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }
        
        
        public void print() {
            DebugUtils.log(this, "type: " + this.type, "value: " + this.value);
        }
        
        public boolean equals(Token token) {
            return this.equals(token.type, token.value);
        }
        
        public boolean equals(TokenType type, char value) {
            return this.equals(type, "" + value);
        }
        
        public boolean equals(TokenType type, String value) {
            if( !this.type.equals(type) )
            return false;
            
            if( this.value.equals(value) )
            return true;
            
            if( this.value == null || value == null )
            return true;
            
            return false;
        }
        
        public boolean hasType(TokenType type) {
            return (this.type == type);
        }
        
        public boolean hasValue(String value) {
            return (this.value.equals(value));
        }
    }
    
    private List<Token> tokens;
    private String jegmdString;
    
    public Tokenizer(String jegmdString) {
        this.tokens = new ArrayList<>();
        this.jegmdString = jegmdString;
    }
    
    
    @SuppressWarnings("incomplete-switch")
    public void tokenize() {
        TokenType tokenType = TokenType.NONE;
        String tokenValue = "";
        boolean isDecimalFound = false;
        boolean skipEndingOnce = false;
        char stringStartCharacter = 0;
        int s = this.jegmdString.length();
        int position = 0;
        while( position < s )
        {
            char currentCharacter = this.jegmdString.charAt(position);
            boolean closeToken = false;
            
            if( tokenType == TokenType.NONE )
            {
                if( currentCharacter == '"' || currentCharacter == '\'' )
                {
                    tokenType = TokenType.STRING;
                    stringStartCharacter = currentCharacter;
                    position++;
                    continue;
                }
                else if( currentCharacter >= 48 && currentCharacter <= 57 )
                tokenType = TokenType.NUMBER;
                else if( currentCharacter == '.' )
                {
                    tokenType = TokenType.NUMBER;
                    isDecimalFound = true;
                }
                else if(
                    (currentCharacter >= 65 && currentCharacter <= 90) ||
                    (currentCharacter >= 97 && currentCharacter <= 122) ||
                    currentCharacter == '_'
                )
                tokenType = TokenType.IDENTIFIER;
                else if( SpecialCharacters.isSpecialCharacter(currentCharacter) )
                {
                    tokenType = TokenType.SPECIAL;
                    position++;
                    closeToken = true;
                }
                else
                {
                    position++;
                    continue;
                }
                
                tokenValue += currentCharacter;
            }
            else
            {
                switch( tokenType )
                {
                    case STRING: {
                        if( currentCharacter == stringStartCharacter )
                        {
                            if( skipEndingOnce )
                            tokenValue += currentCharacter;
                            else
                            {
                                closeToken = true;
                                position++; // Skip the closing character
                            }
                        }
                        else if( currentCharacter == '\\' )
                        skipEndingOnce = true;
                        else
                        tokenValue += currentCharacter;
                        
                        break;
                    }
                    
                    case NUMBER: {
                        if( 
                            (currentCharacter >= 48 && currentCharacter <= 57) ||
                            !isDecimalFound && currentCharacter == '.'
                        )
                        tokenValue += currentCharacter;
                        else
                        closeToken = true;
                        
                        break;
                    }
                    
                    case IDENTIFIER: {
                        if(
                            (currentCharacter >= 65 && currentCharacter <= 90) ||
                            (currentCharacter >= 97 && currentCharacter <= 122) ||
                            (currentCharacter >= 48 && currentCharacter <= 57) ||
                            currentCharacter == '_'
                        )
                        tokenValue += currentCharacter;
                        else
                        {
                            //String lowerCaseTokenValue = tokenValue.toLowerCase();
                            
                            if( this.isElementType(tokenValue) )
                            tokenType = TokenType.ELEMENT;
                            else if( this.isProperty(tokenValue) )
                            tokenType = TokenType.PROPERTY;
                            
                            closeToken = true;
                        }
                        
                        break;
                    }
                }
            }
            
            if( closeToken )
            {
                this.tokens.add(new Token(tokenType, tokenValue));
                tokenType = TokenType.NONE;
                tokenValue = "";
            }
            else
            position++;
            
            skipEndingOnce = false;
        }
    }
    
    private boolean isElementType(String string) {
        return Elements.isElement(string);
    }
    
    private boolean isProperty(String string) {
        return Properties.isProperty(string);
    }
    
    
    public List<Token> getTokens() {
        return this.tokens;
    }
}
