package ca.jent.structural.patterns.decorator;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Loggers is a "Garbage" class.  It is a garbage class because its role is 
 * to provide a Logger with a wired filter (the decorator).  So Loggers (with 
 * an 's') has no real role but of being of infrastructure purposes.
 * 
 * Note the three methods implementation provides the same functionality. 
 * @author jraymond
 */
public class Loggers {
    
    /**
     * Static method that return an implementation of the Logger interface.
     * Note that we are returning an Anonymous Logger class object which
     * has been decorated with a Predicate (filter).
     * Note how we capture (closure) the logger and filter in the returned 
     * anonymous class object.
     * @param logger A logger which has no filter wired.
     * @param filter The filter to decorate the logger.
     * @return An Anonymous Logger class object with a wired filter.
     */
    public static Logger getLoggerWith(Logger logger, Predicate<String> filter) {
        Objects.requireNonNull(logger);
        Objects.requireNonNull(filter);
        return new Logger() {
            @Override
            public void log(String message) {
                if (filter.test(message)) {
                    logger.log(message);
                }
            }
            
        };
    }
    
    /**
     * Static method that return an implementation of the Logger interface.
     * This is exactly the same functionality as above except it uses only
     * lambdas.  So instead of returning an anonymous class object, we return
     * a lambda.
     * @param logger A simple logger.
     * @param filter The filter to decorate the logger.
     * @return 
     */
    public static Logger getLoggerWithLambda(Consumer<String> logger, Predicate<String> filter) {
        Objects.requireNonNull(logger);
        Objects.requireNonNull(filter);
        return messsage -> {
            if (filter.test(messsage)) {
                logger.accept(messsage);
            }
        };
    }
    
    /**
     * Here we are doing the same identical functionality as above again except 
     * we no longer need the interface Logger.  Since the interface Logger is
     * a Consumer<String> i.e. T -> ()
     * Then we can use only lambda to do the implementations.  No need to create
     * named class or interface.
     * Note that we are still "decorating" the logger with a filter.
     * @param logger A simple logger as a lambda implementation.
     * @param filter A filter to decorate the logger.
     * @return  The decorated Logger (a Consumer<String>).
     */
    public static Consumer<String> getLoggerAsLambda(Consumer<String> logger, Predicate<String> filter) {
        Objects.requireNonNull(logger);
        Objects.requireNonNull(filter);
        return message -> {
            if (filter.test(message)) {
                logger.accept(message);
            }
        };
    }
}
