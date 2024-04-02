package johnengine.core.logger;

import johnengine.core.logger.Logger.Record;

public class ConsoleRecorder implements ILogRecorder {

    @Override
    public boolean log(Record record) {
        System.out.println(record.getMessage());
        return true;
    }
}
