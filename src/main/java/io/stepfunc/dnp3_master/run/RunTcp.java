package io.stepfunc.dnp3_master.run;

import io.stepfunc.dnp3.*;
import io.stepfunc.dnp3.Runtime;
import io.stepfunc.dnp3_master.config.TestMasterChannelConfig;
import io.stepfunc.dnp3_master.listener.TestClientStateListener;

import java.util.Scanner;

public class RunTcp {

    public static void run(Runtime runtime) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("input ipport: {127.0.0.1:20000}");
        String ipport = scanner.next();

        // ANCHOR: create_tcp_channel
        MasterChannel channel = MasterChannel.createTcpChannel(
                runtime,
                LinkErrorMode.CLOSE,
                TestMasterChannelConfig.getMasterChannelConfig(),
                new EndpointList(ipport),
                new ConnectStrategy(),
                new TestClientStateListener()
        );
        // ANCHOR_END: create_tcp_channel

        try {
            RunChannel.run(channel);
        }
        finally {
            channel.shutdown();
        }
    }
}
