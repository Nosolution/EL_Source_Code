package BackUps;

import android.content.Context;

import Managers.ClockManager;
import Managers.TaskManager;
import Managers.TimeManager;

public class LogManager {
    private TaskManager taskManager;
    private ClockManager clockManager;
    private TimeManager timeManager;

    private LogManager(Context context) {
        taskManager = TaskManager.getTaskManager(context);
        clockManager = ClockManager.getClockManager(context);
        timeManager = TimeManager.getTimeManager();
    }

    private LogManager() {

    }

    public static LogManager getLogManager(Context context) {
        return new LogManager(context);
    }

}
