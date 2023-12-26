package io.stepfunc.dnp3_master;

import io.stepfunc.dnp3.FileError;
import io.stepfunc.dnp3.FileReader;
import org.joou.UByte;
import org.joou.UInteger;

import java.util.List;

// ANCHOR: file_logger
public class LoggingFileReader implements FileReader {

    @Override
    public boolean opened(UInteger size) {
        System.out.println("Opened file - size: " + size);
        return true;
    }

    @Override
    public boolean blockReceived(UInteger blockNum, List<UByte> data) {
        System.out.println("Received file block: " + blockNum);
        return true;
    }

    @Override
    public void aborted(FileError error) {
        System.out.println("Aborted file transfer: " + error);
    }

    @Override
    public void completed() {
        System.out.println("Completed file transfer");
    }
}
// ANCHOR_END: file_logger