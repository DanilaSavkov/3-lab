package enums;

public enum Action {
    MOVE(3), SEX(2), EAT(1);
    int priority;

    Action(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
