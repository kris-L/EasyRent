package com.xw.common.util;

import java.security.Provider;

/**
 * Created by Horris on 2017/5/12.
 */

public class CryptoProvider extends Provider {
    /**
     * Constructs a provider with the specified name, version number,
     * and information.
     */
    protected CryptoProvider() {
        super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
        put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
        put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
    }
}
