package org.aldouscloud.aldouscloud.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message, Object id) {
        super(message + " with Id " + id + " not found");
    }
}
