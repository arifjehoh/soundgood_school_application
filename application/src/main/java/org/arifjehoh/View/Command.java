package org.arifjehoh.View;

public enum Command {
    /**
     * Not specified command.
     */
    ILLEGAL_COMMAND,

    /**
     * Close the application.
     */
    QUIT,

    /**
     * List all available instruments.
     */
    LIST,

    /**
     * Rent a instrument.
     */
    RENT,

    /**
     * Terminate a rented instrument.
     */
    TERMINATE,

    /**
     * Show information of user, rental invoices and all rented instruments.
     */
    ABOUT_ME,
    /**
     * Print outs all the commands above.
     */
    HELP
}
