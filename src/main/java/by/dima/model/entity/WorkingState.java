package by.dima.model.entity;

public class WorkingState {
    private boolean working;

    public WorkingState() {
    }

    public WorkingState(boolean working) {
        this.working = working;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }
}
