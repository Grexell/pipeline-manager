package by.dima.util;

import by.dima.model.entity.Task;
import by.dima.model.entity.TaskExecution;

import java.util.function.Predicate;

public class TaskResolver implements Predicate<TaskExecution> {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean test(TaskExecution taskExecution) {
        if (taskExecution == null) return false;
        return taskExecution.getTask().getName().equals(name);
    }
}
