package edu.houyhnhnm.util;

/**
 * Indicates an exception related with JPA Datasource. In most of the cases it encapsulate the real exception cause.
 */
public class DatasourceException extends RuntimeException {

    public DatasourceException() {
    }

    public DatasourceException(String message) {
        super(message);
    }

    public DatasourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatasourceException(Throwable cause) {
        super(cause);
    }

    public DatasourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
