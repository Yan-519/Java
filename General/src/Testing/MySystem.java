package Testing;

public class MySystem {
    // Outer class values (non-static)
    private String prefix;
    private int logCount = 0;

    // Constructor to initialize system values
    public MySystem(String prefix) {
        this.prefix = prefix;
    }

    // Expose the group of functions as a public property
    public final ConsoleGroup console = new ConsoleGroup();

    // The grouped functions class (Inner Class)
    public class ConsoleGroup {
        
        // This function can directly access the outer class values
        public void write(String message) {
            System.out.println("[" + prefix + "] (#" + ++logCount + "): " + message);
        }

        public void clear() {
            System.out.println("Clearing console for system: " + prefix);
        }
    }
}
