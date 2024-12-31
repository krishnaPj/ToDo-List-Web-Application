package net.entities;

/**
 * Enum representing the status of a user in the system.
 */
public enum UserStatus { 
    /**
     * User has not confirmed their account (e.g., email verification pending).
     */
    NOT_CONFIRMED, 

    /**
     * User has confirmed their account and is fully active.
     */
    CONFIRMED 
}