/*
 * Copyright (c) 2019 Šimon Hašák.
 * All rights reserved.
 */

package sk.tuke.fei.hasak.notifyservice.exception;

/**
 * The type Event not found exception.
 *
 * @author Šimon Hašák
 */
public class NotificationNotFoundException extends Exception {

    /**
     * Instantiates a new Event not found exception.
     */
    public NotificationNotFoundException() {}

    /**
     * Instantiates a new Event not found exception.
     *
     * @param message the message
     */
    public NotificationNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Event not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public NotificationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
