package by.dima.model.entity.operation;

import by.dima.model.entity.Status;
import by.dima.model.entity.TaskExecution;

public class RandomStatusOperation extends StatusOperation implements Operation{

    public RandomStatusOperation() {
    }

    public RandomStatusOperation(Operation operation) {
        super(operation);
    }

    @Override
    public void work(TaskExecution taskExecution) {
        Status[] statuses = Status.values();
        setStatus(statuses[(int)Math.random() * statuses.length ]);
        super.work(taskExecution);
    }
}
