package com.maxicruz.usuarios.infraestructure.adapters.out.loggin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class Slf4jLoggingAdapter implements ILoggingAdapter {

    private static final Logger logger = LoggerFactory.getLogger(Slf4jLoggingAdapter.class);

    @Override
    public void logInfo(String message, Object... args) {
        logger.info(message, args);
    }

    @Override
    public void logDebug(String message, Object... args) {
        logger.debug(message, args);
    }

    @Override
    public void logTrace(String message, Object... args) {
        logger.trace(message, args);
    }

    @Override
    public void logError(String message, Throwable t, Object... args) {
        logger.error(message, t, args);
    }
}
