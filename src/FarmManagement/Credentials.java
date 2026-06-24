package FarmManagement;

public enum Credentials {
    ADMIN_PASSWORD("admin123"),
    WORKER_PASSWORD("worker123");

    private final String password;

    Credentials(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
