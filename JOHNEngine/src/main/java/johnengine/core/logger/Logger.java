package johnengine.core.logger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import johnengine.testing.DebugUtils;

public class Logger {
    
    /******************** Record-class ********************/
    
    public static class Record {
        public final String timestamp;
        public final Severity severity;
        public final String threadName;
        public final Object caller;
        public final String message;
        private final String messageString;
        
        private static String tokenize(Object o) {
            if( o == null )
            return "";
                
            return "[" + o.toString() + "]";
        }
        
        private Record(
            String timestamp, 
            Severity severity, 
            String threadName,
            Object caller,  
            String message
        ) {
            this.timestamp = timestamp;
            this.severity = severity;
            this.threadName = threadName;
            this.caller = caller;
            this.message = message;
            this.messageString = (
                tokenize(this.timestamp) +
                tokenize(this.severity) +
                tokenize(this.threadName) +
                tokenize(this.caller) + ": " +
                this.message
            );
        }
        
        public String getMessage() {
            return this.messageString;
        }
    }

    
    /******************** Verbosity-class ********************/
    
    private static class Verbosity {
        public final String label;
        public final int level;
        
        private Verbosity(String label, int level) {
            this.label = label;
            this.level = level;
        }
        
        @Override
        public String toString() {
            return this.label;
        }
    }
    
    
    /******************** Severity-class ********************/
    
    @SuppressWarnings("unused")
    private static class Severity {
        public final String label;
        public final int level;
        
        private Severity(String label, int level) {
            this.label = label;
            this.level = level;
        }
        
        @Override
        public String toString() {
            return this.label;
        }
    }
    
    /******************** Logger-class ********************/
    
    public static final Verbosity VERBOSITY_VERBOSE;
    public static final Verbosity VERBOSITY_STANDARD;
    public static final Verbosity VERBOSITY_MINIMAL;
    public static final Verbosity VERBOSITY_MUTED;
    static {
        VERBOSITY_VERBOSE = new Verbosity("VERBOSE", 0);
        VERBOSITY_STANDARD = new Verbosity("STANDARD", 1);
        VERBOSITY_MINIMAL = new Verbosity("MINIMAL", 2);
        VERBOSITY_MUTED = new Verbosity("MUTED", 3);
    }
    
    public static final Severity SEVERITY_FATAL;
    public static final Severity SEVERITY_WARNING;
    public static final Severity SEVERITY_NOTIFICATION;
    static {
        SEVERITY_FATAL = new Severity("FATAL", 0);
        SEVERITY_WARNING = new Severity("WARNING", 1);
        SEVERITY_NOTIFICATION = new Severity("NOTIFICATION", 2);
    }
    
    public static final int SHOW_TIMESTAMP = 1;
    public static final int SHOW_SEVERITY = 2;
    public static final int SHOW_THREAD = 4;
    public static final int SHOW_CALLER = 8;
    
    private static Map<String, ILogRecorder> recorders;
    private static Verbosity verbosity;
    private static int configuration;
    
    static {
        recorders = new HashMap<>();
        verbosity = VERBOSITY_VERBOSE;
        configuration = 0;
    }
    
    
    public static boolean addRecorder(String identifier, ILogRecorder recorder) {
        if( recorders.containsKey(identifier) )
        return false;
        
        recorders.put(identifier, recorder);
        return true;
    }
    
    public static void logln(
        Verbosity verbosityLevel, Severity severity, Object caller, String... messages
    ) {
        log(verbosityLevel, severity, caller, "", messageArrayToString(messages));
    }
    
    public static void log(
        Verbosity verbosityLevel, Severity severity, Object caller, String... messages
    ) { 
        if( verbosity.level > verbosityLevel.level )
        return;
        
        String log = messageArrayToString(messages);
        String threadName = Thread.currentThread().getName();
        
        Logger.Record record = new Logger.Record(
            checkConfiguration(SHOW_TIMESTAMP) ? getTimestamp() : null, 
            checkConfiguration(SHOW_SEVERITY) ? severity : null, 
            checkConfiguration(SHOW_THREAD) ? threadName : null, 
            checkConfiguration(SHOW_CALLER) ? caller : null, 
            log
        );
        
        
        boolean didRecord = false;
        for( Map.Entry<String, ILogRecorder> en : recorders.entrySet() )
        {
            en.getValue().log(record);
            didRecord = true;
        }
        
        if( !didRecord )
        DebugUtils.log("Logger", "WARNING: Logs are not being recorded!");
    }
    
    private static String messageArrayToString(String... messages) {
        String log = "";
        for( String message : messages )
        log += message + "\n";
        
        return log.substring(0, log.length() - 1);
    }
    
    private static boolean checkConfiguration(int configurationMask) {
        return (configuration & configurationMask) == configurationMask;
    }
    
    private static String getTimestamp() {
        LocalDateTime dateTime = LocalDateTime.now();
        return (
            dateTime.getHour() + ":" + 
            dateTime.getMinute() + ":" + 
            dateTime.getSecond() + "." +
            dateTime.getNano() / 1000000
        );
    }
    
    public static void setVerbosity(Verbosity v) {
        verbosity = v;
    }
    
    public static void configure(int configurationMask) {
        configuration = configurationMask;
    }
    
    public static Logger.Verbosity getVerbosity() {
        return verbosity;
    }
}
