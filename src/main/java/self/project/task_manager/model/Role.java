package self.project.task_manager.model;

public enum Role {
    ADMIN,
    USER;

    public String getRoleName() {
        return this.name();
    }
}
