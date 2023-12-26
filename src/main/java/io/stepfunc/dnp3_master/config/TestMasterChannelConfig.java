package io.stepfunc.dnp3_master.config;

import io.stepfunc.dnp3.AppDecodeLevel;
import io.stepfunc.dnp3.MasterChannelConfig;

import java.util.Scanner;

import static org.joou.Unsigned.ushort;

public class TestMasterChannelConfig {

    // ANCHOR: master_channel_config
    public static MasterChannelConfig getMasterChannelConfig() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("input master address: {1024} in example 1");
        int masterAddr = scanner.nextInt();

        // MasterChannelConfig config = new MasterChannelConfig(ushort(1));
        MasterChannelConfig config = new MasterChannelConfig(ushort(masterAddr));
        config.decodeLevel.application = AppDecodeLevel.OBJECT_VALUES;
        return config;
    }
    // ANCHOR_END: master_channel_config
}
