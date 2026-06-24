package FarmManagement;

public abstract class User {

    protected String name;
    protected Role role;

    public User(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public boolean canAddCow() {
        return role == Role.ADMIN;
    }

    public boolean canAddMilk() {
        return role == Role.ADMIN || role == Role.WORKER;
    }

    public boolean canUpdateStatus() {
        return role == Role.ADMIN;
    }

    public boolean canViewReports() {
        return true;
    }
}
