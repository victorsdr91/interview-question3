package com.victorsanchez.exercise.persistence;

/**
 * Exception thrown when an object is not found on the storage
 */
public class RecordNotFoundException extends Exception {
    /**
     * Constructor of the exception that receives a message to be displayed on the log
     * @param msg - Message to be displayed
     */
    public RecordNotFoundException(String msg) {
        super(msg);
    }
}
