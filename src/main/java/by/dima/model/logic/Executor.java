package by.dima.model.logic;

import by.dima.model.dao.ExecutionDAO;
import by.dima.model.entity.*;
import by.dima.util.ExecutionPool;
import java.util.*;
import java.util.concurrent.Phaser;

public class Executor implements Runnable {

    private ExecutionDAO executionDAO;

    private WorkingState workingState;
    private ExecutionPool executionPool;
    private Execution execution;

    public Executor() {
    }

    public Executor(Execution execution) {
        workingState = new WorkingState(true);
        this.execution = execution;
    }

    public void setExecutionDAO(ExecutionDAO executionDAO) {
        this.executionDAO = executionDAO;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }

    public Execution getExecution() {
        return execution;
    }

    public WorkingState getWorkingState() {
        return workingState;
    }

    public void setExecutionPool(ExecutionPool executionPool) {
        this.executionPool = executionPool;
    }

    @Override
    public void run() {
        Phaser phaser;

        execution.setStartDate(new Date());
        executionDAO.createExecution(execution);

        HashMap<String, TaskExecutor> threads = new HashMap<>();

        for (TaskExecution taskExecution : execution.getTaskExecutions()) {
            TaskExecutor executor = new TaskExecutor();

            executor.setExecution(execution);
            executor.setTaskExecution(taskExecution);
            executor.setWorkingState(workingState);
            executor.setExecutionDAO(executionDAO);
            threads.put(taskExecution.getTask().getName(), executor);
        }

        phaser = new Phaser(threads.values().size() + 1);

        for (Map.Entry<String, String> entry : execution.getPipeline().getTransitions().entrySet()) {
            threads.get(entry.getKey()).getStartThreads().add(threads.get(entry.getValue()).getPhaser());
            threads.get(entry.getValue()).getPhaser().register();
            threads.get(entry.getKey()).getStartThreads().add(phaser);
        }

        for (Thread thread: threads.values()) {
            thread.start();
        }

        phaser.arriveAndAwaitAdvance();
        Status executionStatus = Status.COMPLETED;

        for (TaskExecution taskExecution : execution.getTaskExecutions()) {
            executionStatus = taskExecution.getStatus();

            if (taskExecution.getStatus() == Status.FAILED || taskExecution.getStatus() == Status.SKIPPED) {
                break;
            }
        }

        execution.setStatus(executionStatus);
        executionDAO.updateExecution(execution);
        executionPool.remove(this);
    }
}
