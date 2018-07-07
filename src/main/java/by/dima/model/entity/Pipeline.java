package by.dima.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.converter.AbstractHttpMessageConverter;

import java.util.IdentityHashMap;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pipeline extends AbstractEntity {
    private String name;
    private String description;
    private List<Task> tasks;

    private IdentityHashMap<String, String> transitions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public IdentityHashMap<String, String> getTransitions() {
        return transitions;
    }

    public void setTransitions(IdentityHashMap<String, String> transitions) {
        this.transitions = transitions;
    }
}
