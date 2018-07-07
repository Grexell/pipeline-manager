package by.dima.model.entity.operation;

import by.dima.model.entity.TaskExecution;

public interface Operation {
    void work(TaskExecution taskExecution);
}
