package johnengine.extra.jegmd.tokenizer;

public final class StringToken extends AToken {

    public static final String TYPE = "STRING";
    
    private char startCharacter;
    
    public StringToken() {
        super(TYPE);
        this.startCharacter = 0;
    }
    

    @Override
    protected boolean validateStart(Tokenizer tokenizer) {
        char charAt = tokenizer.peekNext();
        
        if( charAt == '"' || charAt == '\'' )
        {
            this.startCharacter = charAt;
            tokenizer.advance();
            return true;
        }
        
        return false;
    }

    @Override
    protected boolean validateContinuation(Tokenizer tokenizer) {
        char charAt = tokenizer.peekNext();
        tokenizer.advance();
        
        if( charAt == this.startCharacter )
        return false;
        
        this.recordCharacter(charAt);
        return true;
    }
    
    @Override
    protected StringToken createToken() {
        return new StringToken();
    }
    
    @Override
    protected void reset() {
        super.reset();
        this.startCharacter = 0;
    }
}
