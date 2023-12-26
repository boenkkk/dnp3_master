package io.stepfunc.dnp3_master.config;

import io.stepfunc.dnp3.CertificateMode;
import io.stepfunc.dnp3.TlsClientConfig;

public class TestTlsClientConfig {

    public static TlsClientConfig getTlsSelfSignedConfig() {
        // ANCHOR: tls_self_signed_config
        TlsClientConfig config = new TlsClientConfig(
                "test.com",
                "./certs/self_signed/entity2_cert.pem",
                "./certs/self_signed/entity1_cert.pem",
                "./certs/self_signed/entity1_key.pem",
                "" // no password
        ).withCertificateMode(CertificateMode.SELF_SIGNED);
        // ANCHOR_END: tls_self_signed_config

        return config;
    }

    public static TlsClientConfig getTlsCAConfig() {
        // ANCHOR: tls_ca_chain_config
        TlsClientConfig config = new TlsClientConfig(
                "test.com",
                "./certs/ca_chain/ca_cert.pem",
                "./certs/ca_chain/entity1_cert.pem",
                "./certs/ca_chain/entity1_key.pem",
                "" // no password
        );
        // ANCHOR_END: tls_ca_chain_config

        return config;
    }
}
