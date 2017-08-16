package ca.jent.structural.patterns.decorator;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Structural Pattern: Decorator
 * We will leverage a functional interface to show how we can decorate a
 * Logger with a Filter.
 * First:  See Loggers for somewhat adequate solutions.  Loggers uses this
 *         interface to produce a Logger decorated with a filter.  Loggers
 *         class shows three ways to implement the same decoration pattern
 *         with increasingly better implementations which leverage lambdas.
 * 
 * Second: However the best implementation of this decorator pattern is
 *         self contained in this interface leveraging the default method.
 *         A lambda is returned which correspond to a Logger decorated with
 *         a filter.  It is much more concise and no need for garbage class.
 * 
 * Summary:  This interface is sufficient to implement a Logger decorated with
 *           a filter.  The Loggers class is not needed (provided for learning
 *           and demonstration purpose).
 * 
 * This interface: declare a simple "log" interface which will log any String 
 *                 message.
 * 
 * @author jraymond
 */
@FunctionalInterface
public interface Logger {

    public void log(String message);
    
    /**
     * Produce a Logger implementation decorated with a filter.
     * @param filter The decorator
     * @return Logger decorated with filter.
     */
    default Logger getLoggerWith(Predicate<String> filter) {
        Objects.requireNonNull(filter);
        return message -> {
            if (filter.test(message)) {
                log(message);
            }
        };
    }
    
    /**
     * Usage for this interface and the class Loggers
     * Demonstrate from worst to best usage.
     * @param args n/a
     */
    public static void main(String[] args) {
        
        // 1. Using Loggers.getLoggerWith(...).  We first need to create a Logger instance then decorate it.
        Logger logger1 = message -> System.out.println(message);
        Logger logger1DecoratedWithFilter = Loggers.getLoggerWith(logger1, message -> message.startsWith("INFO"));
        
        logger1DecoratedWithFilter.log("INFO: Using Loggers.getLoggerWith(logger1)");
        
        // 2. Using Loggers.getLoggerWithLambda(...).  We create a Logger instance (implementation) directly as a 
        //    parameterized lambda and returns a decorated logger with a filter.
        Logger logger2DecoratedWithFilter = Loggers.getLoggerWithLambda(message -> System.out.println(message), 
                                                                        message -> message.startsWith("INFO"));
        logger2DecoratedWithFilter.log("INFO: Using Loggers.getLoggerWithLambda(lambda,lambda)");
        
        // 3. Using Loggers.getLoggerAsLambda(...).  Same as above but without actually using any Logger interface.
        //    This version is also very adequate as it may use a garbage class but the Logger interface is no longer
        //    needed.  It implements a Logger functionality using only lambdas. (Notice the return type -> it is not Logger)
        Consumer<String> logger3DecoratedWithFilter = Loggers.getLoggerAsLambda(message -> System.out.println(message), 
                                                                                message -> message.startsWith("INFO"));
        logger3DecoratedWithFilter.accept("INFO: Using Loggers.getLoggerAsLambda(lambda,lambda)");
        
        // 4. Without using Loggers class but only using Logger interface.
        Logger logger4 = message -> System.out.println(message);
        Logger logger4DecoratedWithFilter = logger4.getLoggerWith(message -> message.startsWith("INFO"));
        logger4.log("INFO: Using only Logger interface.  No classes.");
        
    }
    
}
