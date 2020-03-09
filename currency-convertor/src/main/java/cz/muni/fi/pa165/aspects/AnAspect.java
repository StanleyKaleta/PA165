package cz.muni.fi.pa165.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.inject.Named;
import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Aspect
public class AnAspect {

//    private static final Logger logger = Logger.getLogger("Logger");


    @Around("execution(public * *(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.nanoTime();
        Object result = joinPoint.proceed();
        long end = System.nanoTime();

        System.err.println("Calling method: " + joinPoint.getSignature() + " @ " + Arrays.toString(joinPoint.getArgs()) + " @ " + start);
        System.err.println("Method finished: " + joinPoint.getSignature() + " @ " + end);
        System.err.println("Length: " + joinPoint.getSignature() + " = " + (end - start) + " nanoseconds");

        return result;
    }
}
