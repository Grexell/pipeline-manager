package by.dima.model.dao;

import by.dima.model.entity.Pipeline;
import by.dima.model.entity.Task;

import java.util.List;

public interface TaskDAO {
    void createTask(Pipeline pipeline, Task task);
    void deleteTask(Task task);
    void updateTask(Task task);

    Task getTaskByPipelineAndName(Pipeline pipeline, String name);
    List<Task> getAllTasks(Pipeline pipeline);
}
