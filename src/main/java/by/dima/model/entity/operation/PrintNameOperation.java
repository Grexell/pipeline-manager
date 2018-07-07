package by.dima.model.entity.operation;

import by.dima.model.entity.TaskExecution;

public class PrintNameOperation extends DecoratorOperation implements Operation {

    public PrintNameOperation(Operation operation) {
        super(operation);
    }

    @Override
    public void work(TaskExecution taskExecution) {
        System.out.println(taskExecution.getTask().getName());
        super.work(taskExecution);
    }
}
