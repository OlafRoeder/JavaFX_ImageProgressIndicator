package demo.application;

import javafx.concurrent.Task;

public interface ApplicationType {

    /**
     * Prints type of demo.application.
     */
    void printApplicationType();

    /**
     * Executes a {@link Task} at some unspecified time in the future.
     *
     * @param task {@link Task}
     */
    void execute(Task<?> task);
}