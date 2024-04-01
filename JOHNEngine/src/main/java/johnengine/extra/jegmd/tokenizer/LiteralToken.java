package johnengine.extra.jegmd.tokenizer;

import johnengine.extra.jegmd.Keywords;

public final class LiteralToken extends AToken {

    public static final String ELEMENT = "ELEMENT";
    public static final String PROPERTY = "PROPERTY";
    public static final String IDENTIFIER = "IDENTIFIER";
    
    public LiteralToken() {
        super(IDENTIFIER);
    }
    

    @Override
    protected boolean validateStart(Tokenizer tokenizer) {
        char charAt = tokenizer.peekNext();
        
        if(
            (charAt >= 65 && charAt <= 90) ||
            (charAt >= 97 && charAt <= 122) ||
            charAt == '_'
        )
        {
            tokenizer.advance();
            this.recordCharacter(charAt);
            return true;
        }
        
        return false;
    }

    @Override
    protected boolean validateContinuation(Tokenizer tokenizer) {
        char charAt = tokenizer.peekNext();
        
        if(
            (charAt >= 65 && charAt <= 90)  ||
            (charAt >= 97 && charAt <= 122) ||
            (charAt >= 48 && charAt <= 57)  ||
            charAt == '_'
        ) {
            tokenizer.advance();
            this.recordCharacter(charAt);
            return true;
        }
        
        if( Keywords.isElement(this.value) )
        this.type = LiteralToken.ELEMENT;
        else if( Keywords.isProperty(this.value) )
        this.type = LiteralToken.PROPERTY;
        
        return false;
    }
    
    @Override
    protected LiteralToken createToken() {
        LiteralToken token = new LiteralToken();
        token.type = this.type;
        return token;
    }
    
    @Override
    protected void reset() {
        super.reset();
        this.type = LiteralToken.IDENTIFIER;
    }
}
