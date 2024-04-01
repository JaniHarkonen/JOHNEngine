package johnengine.extra.jegmd.tokenizer;

import johnengine.testing.DebugUtils;

public abstract class AToken {
    
    protected String type;
    protected String value;
    
    public AToken(String type) {
        this.type = type;
        this.reset();
    }
    
    
    protected abstract boolean validateStart(Tokenizer tokenizer);
    
    protected abstract boolean validateContinuation(Tokenizer tokenizer);
    
    protected abstract AToken createToken();
    
    public boolean extract(Tokenizer tokenizer) {
        if( !this.validateStart(tokenizer) )
        return false;
        
        boolean tokenContinues = true;
        while( tokenContinues && tokenizer.hasCharactersLeft() )
        tokenContinues = this.validateContinuation(tokenizer);
        
        AToken extractedToken = this.createToken();
        extractedToken.value = this.value;
        tokenizer.addToken(extractedToken);
        this.reset();
        return true;
    }
    
    protected void reset() {
        this.value = "";
    }
    
    protected void recordCharacter(char character) {
        this.value += character;
    }
    
    public void print() {
        DebugUtils.log(this, "type: " + this.type, "value: " + this.value);
    }
}
