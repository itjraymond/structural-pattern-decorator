package ca.jent.structural.patterns.decorator;

/**
 * Structural Pattern: Decorator
 * We will leverage a functional interface to show how we can decorate a
 * Logger with a Filter.
 * We therefore declare a simple "log" interface which will log a message.
 * 
 * @author jraymond
 */
@FunctionalInterface
public interface Logger {

    public void log(String message);
    
}
