package johnengine.extra.jegmd.tokenizer;

import java.util.ArrayList;
import java.util.List;

import johnengine.core.logger.Logger;
import johnengine.extra.jegmd.Elements;
import johnengine.extra.jegmd.Properties;

public class Tokenizer {
    
    
    /******************** Token ********************/
    
    public static final class Token {
        public TokenType type;
        public String value;
        
        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
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
    
    
    /******************** Tokenizer ********************/
    
    private List<Token> tokens;
    private String jegmdString;
    private int position;
    private TokenType tokenType;
    private String tokenValue;
    
    public Tokenizer(String jegmdString) {
        this.tokens = new ArrayList<>();
        this.jegmdString = jegmdString;
        this.reset();
    }
    
    
    public void tokenize() {
        boolean isDecimalFound = false;
        boolean skipEndingOnce = false;
        char stringEndCharacter = 0;
        
        int s = this.jegmdString.length();
        while( this.position < s )
        {
                // Whether this iteration was the ending of the token
            char currentCharacter = this.jegmdString.charAt(this.position);
            
            if( this.tokenType == TokenType.NONE )
            {
                this.tokenType = TokenTable.table[currentCharacter];
                switch( this.tokenType )
                {
                    case STRING:
                        stringEndCharacter = currentCharacter;
                        this.advance();
                        continue;
                        
                    case NUMBER: 
                        isDecimalFound = (currentCharacter == '.');
                        break;
                        
                    case LITERAL: break;
                        
                    case SPECIAL:
                        this.tokenValue = "" + currentCharacter;
                        this.recordToken();
                        this.advance();
                        continue;
                        
                    case COMMENT:
                        int nextPosition = this.position + 1;
                        
                            // Single-line comment
                        if( nextPosition + 1 >= this.jegmdString.length() )
                        break;
                        
                            // Multi-line
                        if( this.jegmdString.charAt(nextPosition) == '*' )
                        this.tokenType = TokenType.COMMENT_MULTILINE;
                        break;
                        
                    default:
                        Logger.log(
                            Logger.VERBOSITY_VERBOSE, 
                            Logger.SEVERITY_WARNING, 
                            this, 
                            "Skipped character '" + currentCharacter +"'."
                        );
                        
                        this.advance();
                        continue;
                }
                
                this.tokenValue += currentCharacter;
            }
            else
            {
                TokenType currentCharacterType = TokenTable.table[currentCharacter];
                switch( this.tokenType )
                {
                    case STRING: {
                        if( currentCharacter == stringEndCharacter )
                        {
                            if( skipEndingOnce )
                            this.tokenValue += currentCharacter;
                            else
                            this.recordToken();
                        }
                        else if( currentCharacter == '\\' )
                        skipEndingOnce = true;
                        else
                        this.tokenValue += currentCharacter;
                        
                        break;
                    }
                    
                    case NUMBER: {
                        if( 
                            currentCharacterType == TokenType.NUMBER ||
                            !isDecimalFound && currentCharacter == '.'
                        )
                        this.tokenValue += currentCharacter;
                        else
                        {
                            this.recordToken();
                            this.hang();
                        }
                        
                        break;
                    }
                    
                    case LITERAL: {
                        if(
                            currentCharacterType == TokenType.LITERAL ||
                            currentCharacterType == TokenType.NUMBER
                        )
                        this.tokenValue += currentCharacter;
                        else
                        {
                                // Further classify the token type as identifiers,
                                // elements and properties all have similar 
                                // characteristics to literals
                            this.tokenType = TokenType.IDENTIFIER;
                            
                            if( this.isElementType(this.tokenValue) )
                            this.tokenType = TokenType.ELEMENT;
                            else if( this.isProperty(this.tokenValue) )
                            this.tokenType = TokenType.PROPERTY;
                            
                            this.recordToken();
                            this.hang();
                        }
                        
                        break;
                    }
                    
                    case COMMENT: {
                        if( currentCharacter == '\n' )
                        this.resetToken();
                        
                        break;
                    }
                    
                    case COMMENT_MULTILINE: {
                        if( 
                            currentCharacter == '/' && 
                            this.jegmdString.charAt(this.position - 1) == '*' 
                        )
                        this.resetToken();
                        
                        break;
                    }
                    
                    default: {
                        Logger.log(
                            Logger.VERBOSITY_MINIMAL, 
                            Logger.SEVERITY_FATAL, 
                            this, 
                            "Attempting to extract a token of non-existing type!"
                        );
                        break;
                    }
                }
            }
            
            this.advance();
            skipEndingOnce = false;
        }
        
        this.logTokens();
    }
    
    private void logTokens() {
        
            // Log tokens (depending on Logger settings)
        if( Logger.getVerbosity() != Logger.VERBOSITY_VERBOSE )
        return;
        
        String[] log = new String[this.tokens.size() * 3 + 1];
        log[0] = "Tokenization result:";
        for( int i = 0; i < this.tokens.size(); i++ )
        {
            Tokenizer.Token token = this.tokens.get(i);
            log[i * 3 + 1] = "TOKEN";
            log[i * 3 + 2] = "  type:  " + token.type;
            log[i * 3 + 3] = "  value: " + token.value;
        }
        
        Logger.log(
            Logger.VERBOSITY_VERBOSE, 
            Logger.SEVERITY_NOTIFICATION, 
            this, 
            log
        );
    }
    
    private void reset() {
        this.resetToken();
        this.position = 0;
    }
    
    private void recordToken() {
        this.tokens.add(new Token(this.tokenType, this.tokenValue));
        this.resetToken();
    }
    
    private void advance() {
        this.position++;
    }
    
    private void hang() {
        this.position--;
    }
    
    private void resetToken() {
        this.tokenType = TokenType.NONE;
        this.tokenValue = "";
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
