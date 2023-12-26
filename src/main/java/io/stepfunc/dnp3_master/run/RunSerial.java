package io.stepfunc.dnp3_master.run;

import io.stepfunc.dnp3.MasterChannel;
import io.stepfunc.dnp3.Runtime;
import io.stepfunc.dnp3.SerialSettings;
import io.stepfunc.dnp3_master.config.TestMasterChannelConfig;

import java.time.Duration;
import java.util.Scanner;

public class RunSerial {

    public static void run(Runtime runtime) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("input serialPath: {/dev/pts/4}");
        String serialPath = scanner.next();

        // ANCHOR: create_serial_channel
        MasterChannel channel = MasterChannel.createSerialChannel(
                runtime,
                TestMasterChannelConfig.getMasterChannelConfig(),
                serialPath,
                new SerialSettings(),
                Duration.ofSeconds(5),
                state -> System.out.println("Port state change: " + state)
        );
        // ANCHOR_END: create_serial_channel

        try {
            RunChannel.run(channel);
        }
        finally {
            channel.shutdown();
        }
    }
}
