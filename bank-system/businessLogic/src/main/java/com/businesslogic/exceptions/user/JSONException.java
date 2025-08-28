package com.businesslogic.exceptions.user;

import com.businesslogic.exceptions.BLException;

/**
 * Public class JSONException is a subclass of BLException.
 * Is thrown when there is a trouble in converting to json.
 */
public class JSONException extends BLException {
    public JSONException(String message) {
        // Call the parent constructor with super
        super("While converting to JSON error appeared: " + message);
    }
}
