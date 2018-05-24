package com.hlxx.climber.secondpage.settings;

public class TooManyTimesException extends Exception {
    public TooManyTimesException() {
    }

    public TooManyTimesException(String message) {
        super(message);
    }
}
