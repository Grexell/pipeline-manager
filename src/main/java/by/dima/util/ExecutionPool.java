package by.dima.util;

import by.dima.model.entity.Execution;
import by.dima.model.logic.Executor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExecutionPool {
    private Map<Integer, Executor> executorMap;

    public ExecutionPool() {
        this(new HashMap<>());
    }

    public ExecutionPool(Map<Integer, Executor> executorMap) {
        this.executorMap = executorMap;
    }

    public int size() {
        return executorMap.size();
    }

    public boolean isEmpty() {
        return executorMap.isEmpty();
    }

    public boolean containsKey(int id) {
        return executorMap.containsKey(id);
    }

    public Executor get(int id) {
        return executorMap.get(id);
    }

    public Executor put(Executor executor) {
        return executorMap.put(executor.getExecution().getId(), executor);
    }

    public Executor remove(Executor executor) {
        return executorMap.remove(executor.getExecution().getId());
    }
}
