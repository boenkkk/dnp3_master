package io.stepfunc.dnp3_master.listener;

import io.stepfunc.dnp3.LogLevel;
import io.stepfunc.dnp3.Logger;

// ANCHOR: logging_interface
// callback interface used to receive log messages
public class TestLogger implements Logger {

    @Override
    public void onMessage(LogLevel logLevel, String message) {
        System.out.print("onMessage() logLevel|message: "+logLevel+"|"+message);
    }
}
// ANCHOR_END: logging_interface