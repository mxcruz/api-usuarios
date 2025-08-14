package com.maxicruz.usuarios.application.services;

public interface ILoggingService {
    void logInfo(String message, Object ... args);
    void logDebug(String message, Object ... args);
    void logTrace(String message, Object ... args);
    void logError(String message,   Throwable t, Object ... args);
}
