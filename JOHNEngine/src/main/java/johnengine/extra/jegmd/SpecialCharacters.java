package johnengine.extra.jegmd;

import java.util.HashSet;
import java.util.Set;

public final class SpecialCharacters {

    public static final char FIELD_VALUE_SEPARATOR = ':';
    public static final char EXPRESSION_START = '(';
    public static final char EXPRESSION_END = ')';
    public static final char TERM_SEPARATOR = ',';
    public static final char BLOCK_START = '{';
    public static final char BLOCK_END = '}';
    
    private static final Set<Character> CHARACTERS;
    static {
        CHARACTERS = new HashSet<>();
        CHARACTERS.add(FIELD_VALUE_SEPARATOR);
        CHARACTERS.add(EXPRESSION_START);
        CHARACTERS.add(EXPRESSION_END);
        CHARACTERS.add(TERM_SEPARATOR);
        CHARACTERS.add(BLOCK_START);
        CHARACTERS.add(BLOCK_END);
    }
    
    public static boolean isSpecialCharacter(char character) {
        return (CHARACTERS.contains(character));
    }
}
