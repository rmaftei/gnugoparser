package org.rmaftei.gnugoparser.lib.parsers;

/**
 * Created by rmaftei on 5/15/14.
 */
public class GnuGOParseException extends Exception {
    public GnuGOParseException(String message) {
        super(message);
    }

    public GnuGOParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
