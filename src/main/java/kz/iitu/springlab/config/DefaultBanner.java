package kz.iitu.springlab.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!dev & !prod")                 // when no profile is set
class DefaultBanner implements EnvironmentBanner {

    public String describe() { return "no profile is active"; }
}
