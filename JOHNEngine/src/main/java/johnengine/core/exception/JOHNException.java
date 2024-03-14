package johnengine.core.exception;

public class JOHNException extends RuntimeException {

    protected static class Null {}
    
    protected static String formatMessage(String message, Object... details) {
        String formattedMessage = message;
        
        for( int i = 0; i < details.length; i += 2 )
        {
            Object replacement = details[i + 1];
            
            if( !(replacement instanceof Null) )
            {
                formattedMessage = formattedMessage.replaceAll(
                    (String) details[i], 
                    replacement.toString()
                );
            }
        }
        
        return "\n" + formattedMessage;
    }
    
    /**
     * Default serial version UID.
     */
    private static final long serialVersionUID = 1L;
    
    public JOHNException(String message, Object... details) {
        super(formatMessage(message, details));
    }
}
