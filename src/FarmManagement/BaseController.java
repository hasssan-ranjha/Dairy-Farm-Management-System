package FarmManagement;

/**
 * This is the parent class for all sub-controllers.
 * It ensures that every screen has access to the FarmManager data.
 */
public abstract class BaseController {

    // This is the shared data manager
    protected FarmManager manager;

    /**
     * This method is called by the DashboardController
     * whenever a new screen is loaded.
     */
    public void setManager(FarmManager manager) {
        this.manager = manager;
        onManagerSet();
    }

    /**
     * This is a "hook" method.
     * Sub-classes (like ViewActive) will override this if they
     * need to do something immediately after receiving the data
     * (like filling a table).
     */
    protected void onManagerSet() {
        // Default implementation is empty
    }
}