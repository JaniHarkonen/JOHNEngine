package johnengine.extra.jegmd.tokenizer;

public final class NumberToken extends AToken {

    public static final String TYPE = "NUMBER";
    
    private boolean isDecimalFound;
    
    public NumberToken() {
        super(TYPE);
        this.isDecimalFound = false;
    }
    

    private boolean validateCharacter(char character) {
        if( character >= 48 && character <= 57 )
        return true;
        else if( !this.isDecimalFound && character == '.' )
        {
            this.isDecimalFound = true;
            return true;
        }
        
        return false;
    }
    
    @Override
    protected boolean validateStart(Tokenizer tokenizer) {
        char charAt = tokenizer.peekNext();
        
        if( this.validateCharacter(charAt) )
        {
            tokenizer.advance();
            this.recordCharacter(charAt);
            return true;
        }
        
        return false;
    }

    @Override
    protected boolean validateContinuation(Tokenizer tokenizer) {
        return this.validateStart(tokenizer);
    }
    
    @Override
    protected NumberToken createToken() {
        return new NumberToken();
    }
    
    @Override
    protected void reset() {
        super.reset();
        this.isDecimalFound = false;
    }
}
