package io.quarkus.workshop.superheroes.villain;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ConfigUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;

@ApplicationScoped
public class VillainApplicationLifeCycle {

    private static final Logger LOGGER = Logger.getLogger(VillainApplicationLifeCycle.class);

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("   _  ___      _                    _____ _____ ");
        LOGGER.info("  | |/ / |    (_)             /\\   |  __ \\_   _|");
        LOGGER.info("  | ' /| | ___ _ _ __        /  \\  | |__) || |  ");
        LOGGER.info("  |  < | |/ _ \\ | '_ \\      / /\\ \\ |  ___/ | |  ");
        LOGGER.info("  | . \\| |  __/ | | | |    / ____ \\| |    _| |_ ");
        LOGGER.info("  |_|\\_\\_|\\___|_|_| |_|   /_/    \\_\\_|   |_____|");

        LOGGER.info("The application VILLAIN is starting with profile " + ConfigUtils.getProfiles());
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application VILLAIN is stopping...");
    }
}
