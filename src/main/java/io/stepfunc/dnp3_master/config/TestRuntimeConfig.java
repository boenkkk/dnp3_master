package io.stepfunc.dnp3_master.config;

import io.stepfunc.dnp3.RuntimeConfig;

import static org.joou.Unsigned.ushort;

public class TestRuntimeConfig {

    // ANCHOR: runtime_config
    public static RuntimeConfig getRuntimeConfig() {
        return new RuntimeConfig().withNumCoreThreads(ushort(4));
    }
    // ANCHOR_END: runtime_config
}
