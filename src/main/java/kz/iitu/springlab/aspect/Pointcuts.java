package kz.iitu.springlab.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Pointcuts {

    // every method of every class in the service package and its sub-packages
    @Pointcut("within(kz.iitu.springlab.service..*)")
    public void serviceLayer() { }

    // any public method
    @Pointcut("execution(public * *(..))")
    public void publicMethod() { }

    // combination: public methods of the service layer
    @Pointcut("serviceLayer() && publicMethod()")
    public void serviceOperation() { }

    // methods marked with @SimpleCache (individual assignment, variant 5)
    @Pointcut("@annotation(kz.iitu.springlab.cache.SimpleCache)")
    public void cacheable() { }
}
