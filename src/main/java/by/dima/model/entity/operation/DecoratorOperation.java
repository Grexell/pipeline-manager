package by.dima.model.entity.operation;

import by.dima.model.entity.TaskExecution;

public class DecoratorOperation implements Operation{
    private Operation operation;

    public DecoratorOperation() {
    }

    public DecoratorOperation(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public void work(TaskExecution taskExecution) {
        if (getOperation() != null) {
            getOperation().work(taskExecution);
        }
    }
}
