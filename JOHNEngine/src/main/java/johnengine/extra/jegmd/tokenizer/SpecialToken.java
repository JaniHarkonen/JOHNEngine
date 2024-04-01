package johnengine.extra.jegmd.tokenizer;

public final class SpecialToken extends AToken {

    public static final char FIELD_VALUE_SEPARATOR = ':';
    public static final char EXPRESSION_START = '(';
    public static final char EXPRESSION_END = ')';
    public static final char TERM_SEPARATOR = ',';
    public static final char BLOCK_START = '{';
    public static final char BLOCK_END = '}';
    
    public static final String TYPE = "SPECIAL";
    
    public SpecialToken() {
        super(TYPE);
    }
    

    @Override
    protected boolean validateStart(Tokenizer tokenizer) {
        char charAt = tokenizer.peekNext();
        if(
            charAt == SpecialToken.FIELD_VALUE_SEPARATOR ||
            charAt == SpecialToken.EXPRESSION_START ||
            charAt == SpecialToken.EXPRESSION_END ||
            charAt == SpecialToken.TERM_SEPARATOR ||
            charAt == SpecialToken.BLOCK_START ||
            charAt == SpecialToken.BLOCK_END
        ) {
            tokenizer.advance();
            this.recordCharacter(charAt);
            return true;
        }
        
        return false;
    }

    @Override
    protected boolean validateContinuation(Tokenizer tokenizer) {
        return false;
    }
    
    @Override
    protected SpecialToken createToken() {
        return new SpecialToken();
    }
}
