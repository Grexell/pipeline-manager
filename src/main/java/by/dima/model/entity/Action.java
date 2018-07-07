package by.dima.model.entity;

import by.dima.model.entity.operation.Operation;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Action {
    private String type;

    public Action() {
    }

    @JsonIgnore
    private Operation operation;

    public Action(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
