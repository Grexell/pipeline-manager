package by.dima.model.entity.operation;

import by.dima.model.entity.TaskExecution;

public class DelayOperation extends DecoratorOperation implements Operation {

    public static final int DEFAULT_DELAY = 1000;

    private int delay;

    public DelayOperation(Operation operation) {
        this(operation, DEFAULT_DELAY);
    }

    public DelayOperation(Operation operation, int delay) {
        super(operation);
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void work(TaskExecution taskExecution) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.work(taskExecution);
    }
}
