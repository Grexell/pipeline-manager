package by.dima.model.entity.operation;

import by.dima.model.entity.Status;
import by.dima.model.entity.TaskExecution;

public class StatusOperation extends DecoratorOperation implements Operation {

    private Status status;

    public StatusOperation() {
    }

    public StatusOperation(Status status) {
        this.status = status;
    }

    public StatusOperation(Operation operation) {
        super(operation);
    }

    public StatusOperation(Operation operation, Status status) {
        super(operation);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void work(TaskExecution taskExecution) {
        taskExecution.setStatus(status);
        super.work(taskExecution);
    }
}
