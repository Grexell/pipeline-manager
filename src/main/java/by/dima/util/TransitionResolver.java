package by.dima.util;

import by.dima.model.entity.Pipeline;

import java.util.*;

public class TransitionResolver {

    public static Set<String> resolve(Pipeline pipeline){
        Set<String> result = new HashSet<>();

        Set<String> previous = pipeline.getTransitions().keySet();
        Collection<String> next = pipeline.getTransitions().values();



        for (String task: previous) {
            if(Collections.frequency(next, task) == 0) {
                result.add(task);
            }
        }

        return result;
    }
}
