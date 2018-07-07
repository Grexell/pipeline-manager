package by.dima.util;

import by.dima.model.entity.*;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ExecutionCreator {
    public Execution create(Pipeline pipeline) {
        Execution result = new Execution();

        List<TaskExecution> executions = new LinkedList<>();

        for (Task task : pipeline.getTasks()) {
            TaskExecution execution = new TaskExecution();

            execution.setTask(task);
            execution.setStatus(Status.PENDING);

            executions.add(execution);
        }

        result.setStatus(Status.PENDING);
        result.setPipeline(pipeline);
        result.setTaskExecutions(executions);

        return result;
    }
}
