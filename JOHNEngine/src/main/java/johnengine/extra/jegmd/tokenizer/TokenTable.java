package johnengine.extra.jegmd.tokenizer;

import johnengine.extra.jegmd.SpecialCharacters;

/**
 * Lookup table pairing characters with token types in
 * an array. Each index of the lookup table represents a
 * character that is considered the beginning of a token,
 * whose type is the stored at that index.
 */
class TokenTable {

    /**
     * @see TokenTable-class javadoc for more information.
     */
    public static final TokenType[] table = new TokenType[255];
    static {
        for( int i = 0; i < table.length; i++ )
        table[i] = TokenType.NONE;
        
        table['"'] = TokenType.STRING;
        table['\''] = TokenType.STRING;
        
        table['.'] = TokenType.NUMBER;
        table['0'] = TokenType.NUMBER;
        table['1'] = TokenType.NUMBER;
        table['2'] = TokenType.NUMBER;
        table['3'] = TokenType.NUMBER;
        table['4'] = TokenType.NUMBER;
        table['5'] = TokenType.NUMBER;
        table['6'] = TokenType.NUMBER;
        table['7'] = TokenType.NUMBER;
        table['8'] = TokenType.NUMBER;
        table['9'] = TokenType.NUMBER;
        
        table['a'] = TokenType.LITERAL; table['A'] = TokenType.LITERAL;
        table['b'] = TokenType.LITERAL; table['B'] = TokenType.LITERAL;
        table['c'] = TokenType.LITERAL; table['C'] = TokenType.LITERAL;
        table['d'] = TokenType.LITERAL; table['D'] = TokenType.LITERAL;
        table['e'] = TokenType.LITERAL; table['E'] = TokenType.LITERAL;
        table['f'] = TokenType.LITERAL; table['F'] = TokenType.LITERAL;
        table['g'] = TokenType.LITERAL; table['G'] = TokenType.LITERAL;
        table['h'] = TokenType.LITERAL; table['H'] = TokenType.LITERAL;
        table['i'] = TokenType.LITERAL; table['I'] = TokenType.LITERAL;
        table['j'] = TokenType.LITERAL; table['J'] = TokenType.LITERAL;
        table['k'] = TokenType.LITERAL; table['K'] = TokenType.LITERAL;
        table['l'] = TokenType.LITERAL; table['L'] = TokenType.LITERAL;
        table['m'] = TokenType.LITERAL; table['M'] = TokenType.LITERAL;
        table['n'] = TokenType.LITERAL; table['N'] = TokenType.LITERAL;
        table['o'] = TokenType.LITERAL; table['O'] = TokenType.LITERAL;
        table['p'] = TokenType.LITERAL; table['P'] = TokenType.LITERAL;
        table['q'] = TokenType.LITERAL; table['Q'] = TokenType.LITERAL;
        table['r'] = TokenType.LITERAL; table['R'] = TokenType.LITERAL;
        table['s'] = TokenType.LITERAL; table['S'] = TokenType.LITERAL;
        table['t'] = TokenType.LITERAL; table['T'] = TokenType.LITERAL;
        table['u'] = TokenType.LITERAL; table['U'] = TokenType.LITERAL;
        table['v'] = TokenType.LITERAL; table['V'] = TokenType.LITERAL;
        table['w'] = TokenType.LITERAL; table['W'] = TokenType.LITERAL;
        table['x'] = TokenType.LITERAL; table['X'] = TokenType.LITERAL;
        table['y'] = TokenType.LITERAL; table['Y'] = TokenType.LITERAL;
        table['z'] = TokenType.LITERAL; table['Z'] = TokenType.LITERAL;
        
        table['_'] = TokenType.LITERAL;
        
        table[SpecialCharacters.FIELD_VALUE_SEPARATOR] = TokenType.SPECIAL;
        table[SpecialCharacters.EXPRESSION_START] = TokenType.SPECIAL;
        table[SpecialCharacters.EXPRESSION_END] = TokenType.SPECIAL;
        table[SpecialCharacters.TERM_SEPARATOR] = TokenType.SPECIAL;
        table[SpecialCharacters.BLOCK_START] = TokenType.SPECIAL;
        table[SpecialCharacters.BLOCK_END] = TokenType.SPECIAL;
        
        table['/'] = TokenType.COMMENT;
    }
}
