package net.streets.common.response;

import net.streets.common.enumeration.StrResponseCode;

/**
 * User: tkaviya
 * Date: 3/27/2015
 * Time: 7:47 PM
 */
public class StrResponseObject<T> {
    StrResponseCode responseCode;
    T responseObject = null;

    public StrResponseObject(StrResponseCode responseCode, T responseObject) {
        this.responseCode = responseCode;
        this.responseObject = responseObject;
    }

    public StrResponseObject(StrResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public int getCode() {
        return responseCode.code;
    }

    //Override default message with custom message. Return this to allow method chaining
    public StrResponseObject<T> setMessage(String responseMessage) {
        responseCode.message = responseMessage;
        return this;
    }

    public String getMessage() {
        return responseCode.message;
    }

    public StrResponseCode getResponseCode() {
        return responseCode;
    }

    //Change response code for this.symResponseObject. Return this to allow method chaining
    public StrResponseObject<T> setResponseCode(StrResponseCode responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public T getResponseObject() {
        return responseObject;
    }

    //Change object for this.symResponseObject. Return this to allow method chaining
    public StrResponseObject<T> setResponseObject(T responseObject) {
        this.responseObject = responseObject;
        return this;
    }

    @Override
    public String toString() {
        return "{" + responseCode.getCode() + ":" + responseCode.getMessage() + "}";
    }
}
