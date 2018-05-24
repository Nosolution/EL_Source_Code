package BackUps;

import android.content.Context;

import java.util.List;

import Managers.ClockManager;
import Managers.Task;
import Managers.TaskManager;
import Managers.TimeManager;

public class LogManager {
    private TaskManager taskManager;
    private ClockManager clockManager;
    private TimeManager timeManager;
    private List<Task> taskList;

    private LogManager(Context context) {
        taskManager = TaskManager.getTaskManager(context);
        clockManager = ClockManager.getClockManager(context);
        timeManager = TimeManager.getTimeManager();
        taskList = taskManager.getTaskList();
    }

    private LogManager() {

    }

    public static LogManager getLogManager(Context context) {
        return new LogManager(context);
    }

}
