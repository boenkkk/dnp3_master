package io.stepfunc.dnp3_master;

import io.stepfunc.dnp3.*;
import io.stepfunc.dnp3.Runtime;
import io.stepfunc.dnp3_master.config.TestRuntimeConfig;
import io.stepfunc.dnp3_master.config.TestTlsClientConfig;
import io.stepfunc.dnp3_master.listener.TestLogger;
import io.stepfunc.dnp3_master.run.RunSerial;
import io.stepfunc.dnp3_master.run.RunTcp;
import io.stepfunc.dnp3_master.run.RunTls;

import java.util.Scanner;

public class MasterExample {

    public static void main(String[] args) throws Exception {
        // Initialize logging with the default configuration
        // This may only be called once during program initialization
        // ANCHOR: logging_init
        Logging.configure(new LoggingConfig(), new TestLogger());
        // ANCHOR_END: logging_init

        // ANCHOR: runtime
        Runtime runtime = new Runtime(TestRuntimeConfig.getRuntimeConfig());
        // ANCHOR_END: runtime

        try {
            run(runtime, args);
        } finally {
            // ANCHOR: runtime_shutdown
            runtime.shutdown();
            // ANCHOR_END: runtime_shutdown
        }
    }

    private static void run(Runtime runtime, String[] args) throws Exception {
        // if(args.length != 1) {
        //     System.err.println("You must specify a transport");
        //     System.err.println("Usage: master-example <transport> (tcp, serial, tls-ca, tls-self-signed)");
        //     return;
        // }
        // final String type = args[0];

        Scanner scanner = new Scanner(System.in);

        System.out.println("input transport: {tcp, serial, tls-ca, tls-self-signed}");
        String type = scanner.next();

        switch (type) {
            case "tcp" -> RunTcp.run(runtime);
            case "serial" -> RunSerial.run(runtime);
            case "tls-ca" -> RunTls.run(runtime, TestTlsClientConfig.getTlsCAConfig());
            case "tls-self-signed" -> RunTls.run(runtime, TestTlsClientConfig.getTlsSelfSignedConfig());
            default -> System.err.printf("Unknown transport: %s%n", type);
        }
    }
}