package io.stepfunc.dnp3_master.run;

import io.stepfunc.dnp3.*;
import io.stepfunc.dnp3.Runtime;
import io.stepfunc.dnp3_master.config.TestMasterChannelConfig;
import io.stepfunc.dnp3_master.listener.TestClientStateListener;

import java.util.Scanner;

public class RunTls {

    public static void run(Runtime runtime, TlsClientConfig tlsConfig) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("input ipport: {127.0.0.1:20001}");
        String ipport = scanner.next();

        // ANCHOR: create_tls_channel
        MasterChannel channel = MasterChannel.createTlsChannel(
                runtime,
                LinkErrorMode.CLOSE,
                TestMasterChannelConfig.getMasterChannelConfig(),
                new EndpointList(ipport),
                new ConnectStrategy(),
                new TestClientStateListener(),
                tlsConfig
        );
        // ANCHOR_END: create_tls_channel

        try {
            RunChannel.run(channel);
        }
        finally {
            channel.shutdown();
        }
    }
}
