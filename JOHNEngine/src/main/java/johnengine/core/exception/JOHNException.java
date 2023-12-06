package johnengine.core.exception;

public class JOHNException extends RuntimeException {

    public static final int FATAL_ERROR = 1;
    public static final int ERROR = 2;
    public static final int WARNING = 3;
    
    private static final long serialVersionUID = 176516516L;
    
    private int severity;

    public JOHNException(int severity, String message) {
        super(formatMessageBySeverity(severity, message));
        this.severity = severity;
    }
    
    
    private static String formatMessageBySeverity(int severity, String message) {
        switch( severity )
        {
            case FATAL_ERROR: return "FATAL ERROR: " + message;
            case ERROR: return "ERROR: " + message;
            case WARNING: return "Warning: " + message;
        }
        
        return message;
    }
}
