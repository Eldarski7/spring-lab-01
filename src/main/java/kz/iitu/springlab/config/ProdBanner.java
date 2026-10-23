package kz.iitu.springlab.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
class ProdBanner implements EnvironmentBanner {

    public String describe() { return "PRODUCTION: handle with care"; }
}
