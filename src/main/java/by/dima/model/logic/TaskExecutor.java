package by.dima.model.logic;

import by.dima.model.dao.ExecutionDAO;
import by.dima.model.entity.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Phaser;

public class TaskExecutor extends Thread {

    private ExecutionDAO executionDAO;

    private WorkingState workingState;

    private Execution execution;
    private TaskExecution taskExecution;

    private List<Phaser> startThreads;

    private Phaser phaser;

    public TaskExecutor() {
        startThreads = new LinkedList<>();
        phaser = new Phaser();
        phaser.register();
    }

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }

    public TaskExecution getTaskExecution() {
        return taskExecution;
    }

    public void setTaskExecution(TaskExecution taskExecution) {
        this.taskExecution = taskExecution;
    }


    public List<Phaser> getStartThreads() {
        return startThreads;
    }

    public void setStartThreads(List<Phaser> startThreads) {
        this.startThreads = startThreads;
    }

    public WorkingState getWorkingState() {
        return workingState;
    }

    public void setWorkingState(WorkingState workingState) {
        this.workingState = workingState;
    }

    public void setExecutionDAO(ExecutionDAO executionDAO) {
        this.executionDAO = executionDAO;
    }

    public Phaser getPhaser() {
        return phaser;
    }

    @Override
    public void run() {
        phaser.arriveAndAwaitAdvance();

        Task currentTask = taskExecution.getTask();
        if (workingState.isWorking()) {
            taskExecution.setStartDate(new Date());
            OperationBuilder.build(currentTask.getAction().getType()).work(taskExecution);
        } else {
            taskExecution.setStatus(Status.SKIPPED);
        }

        executionDAO.updateTaskExecution(execution, taskExecution);

        for (Phaser phaser : startThreads) {
            phaser.arrive();
        }
    }
}
