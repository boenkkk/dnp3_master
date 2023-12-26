package io.stepfunc.dnp3_master.listener;

import io.stepfunc.dnp3.ClientState;
import io.stepfunc.dnp3.ClientStateListener;

public class TestClientStateListener implements ClientStateListener {

    @Override
    public void onChange(ClientState clientState) {
        System.out.println("onChange() state: "+clientState);
    }
}
