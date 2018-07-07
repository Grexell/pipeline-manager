package by.dima.model.dao;

import by.dima.model.entity.Execution;
import by.dima.model.entity.TaskExecution;

public interface ExecutionDAO {
    void createExecution(Execution execution);
    void updateExecution(Execution execution);
    void updateTaskExecution(Execution execution, TaskExecution taskExecution);
    Execution getExecution(int id);
}
