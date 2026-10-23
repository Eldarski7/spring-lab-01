package kz.iitu.springlab.service;

import kz.iitu.springlab.audit.Audited;
import kz.iitu.springlab.cache.SimpleCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class CatalogService {

    private static final Logger log = LoggerFactory.getLogger(CatalogService.class);

    // reference to our own proxy (used only for the self-invocation fix, step 4.5)
    @Autowired
    @Lazy
    private CatalogService self;

    @SimpleCache // individual assignment, variant 5
    public String findById(long id) {
        log.info("[SERVICE] findById({}) is really executing", id);
        sleep(50); // simulating a database call
        return "Item no. " + id;
    }

    @Audited(action = "CATALOG_LIST", logArguments = true)
    public List<String> findAll(int limit) {
        sleep(300); // a deliberately "slow" method
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "Item no. " + i)
                .toList();
    }

    @Audited(action = "CATALOG_REMOVE")
    public String remove(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid identifier: " + id);
        }
        return "Removed item no. " + id;
    }

    public String removeTwice(long id) {
        // STEP 1 (before the fix): calls through "this" - the proxy is bypassed
//        String first = remove(id);
//        String second = remove(id + 1);
        // STEP 2 (after the fix): comment out the two lines above and uncomment these two
         String first = self.remove(id);
         String second = self.remove(id + 1);
        return first + "; " + second;
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
