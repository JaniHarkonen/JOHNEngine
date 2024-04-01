package johnengine.extra.jegmd.tokenizer;

import java.util.ArrayList;
import java.util.List;

import johnengine.testing.DebugUtils;

public class Tokenizer {
    String source;
    int position;
    
    private List<AToken> tokens;
    
    public Tokenizer(String source) {
        this.tokens = new ArrayList<>();
        this.source = source;
        this.position = 0;
    }
    
    
    public void tokenize() {
        if( this.source == null || this.source.equals("") )
        {
            DebugUtils.log(this, "ERROR: Trying to tokenize a null or empty string");
            return;
        }
        
        AToken[] tokenTypes = new AToken[] {
            new SpecialToken(),
            new StringToken(),
            new NumberToken(),
            new LiteralToken(),
        };
        
        while( this.hasCharactersLeft() )
        {
            if( this.peekNext() == ' ' )
            {
                this.advance();
                continue;
            }
            
            
            for( AToken token : tokenTypes )
            {
                if( token.extract(this) )
                break;
            }
        }
    }
    
    
    void addToken(AToken token) {
        this.tokens.add(token);
    }
    
    void advance(int advance) {
        this.position += advance;
    }
    
    void advance() {
        this.advance(1);
    }
    
    
    char peekNext() {
        return this.source.charAt(this.position);
    }
    
    char peekAt(int position) {
        return this.source.charAt(position);
    }
    
    boolean hasCharactersLeft() {
        return (this.position < this.source.length());
    }
    
    public List<AToken> getTokens() {
        return this.tokens;
    }
}
