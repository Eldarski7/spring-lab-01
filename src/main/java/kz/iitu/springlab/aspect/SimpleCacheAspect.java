package kz.iitu.springlab.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Individual assignment, variant 5: result cache
@Aspect
@Component
@Order(4) // the innermost aspect: closest to the real method
public class SimpleCacheAspect {

    private static final Logger log = LoggerFactory.getLogger(SimpleCacheAspect.class);

    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    @Around("kz.iitu.springlab.aspect.Pointcuts.cacheable()")
    public Object cached(ProceedingJoinPoint pjp) throws Throwable {
        String key = pjp.getSignature().toShortString() + Arrays.toString(pjp.getArgs());
        Object hit = cache.get(key);
        if (hit != null) {
            log.info("[CACHE] HIT  {} -> the real method is NOT called", key);
            return hit;
        }
        log.info("[CACHE] MISS {} -> calling the real method", key);
        Object result = pjp.proceed();
        if (result != null) {
            cache.put(key, result);
        }
        return result;
    }
}
