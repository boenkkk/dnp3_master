package io.stepfunc.dnp3_master;

import io.stepfunc.dnp3.AssociationInformation;
import io.stepfunc.dnp3.FunctionCode;
import io.stepfunc.dnp3.TaskError;
import io.stepfunc.dnp3.TaskType;
import org.joou.UByte;

// ANCHOR: association_information
public class TestAssociationInformation implements AssociationInformation {

    @Override
    public void taskStart(TaskType taskType, FunctionCode functionCode, UByte seq) {
        System.out.println("===============taskStart() taskType|functionCode|seq: "+taskType+"|"+functionCode+"|"+seq+"===============");
    }

    @Override
    public void taskSuccess(TaskType taskType, FunctionCode functionCode, UByte seq) {
        System.out.println("===============taskSuccess() taskType|functionCode|seq: "+taskType+"|"+functionCode+"|"+seq+"===============");
    }

    @Override
    public void taskFail(TaskType taskType, TaskError error) {
        System.out.println("===============taskFail() taskType|error: "+taskType+"|"+error+"===============");
    }

    @Override
    public void unsolicitedResponse(boolean isDuplicate, UByte seq) {
        System.out.println("===============unsolicitedResponse() isDuplicate|seq: "+isDuplicate+"|"+seq+"===============");
    }
}
// ANCHOR_END: association_information