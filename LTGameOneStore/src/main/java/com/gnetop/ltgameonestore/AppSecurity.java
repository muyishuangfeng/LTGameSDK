package com.gnetop.ltgameonestore;


import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

public class AppSecurity {
    private static final String TAG = AppSecurity.class.getSimpleName();
    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA512withRSA";
    private static final String PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJ7j9dpJDdgK+EBp1jpvOSnjtFusRrqd4E1j6XXROZyAk+N9WVlYA3RHOn59DIyesS80QRzk9lKsM4W41yynL8GGpNZSNrLKaPCsZf26knPVV+XwitsMZVIck4zhViNIq7XowKQLZCe2z81yT/R/pU+jzt7m7RmHfSemgL/toyKwIDAQAB";


    public static  String getPublicKey(){
        return PUBLIC_KEY;
    }

    public static boolean verifyPurchase(String signedData, String signature) {
        Log.d(TAG, "\n========== Security verifyPurchase ==========");
        Log.d(TAG, "BASE64 PUBLICKEY :: " + PUBLIC_KEY);
        Log.d(TAG, "SIGNED DATA :: " + signedData);
        Log.d(TAG, "SIGNATURE :: " + signature);
        Log.d(TAG, "=============================================\n");

        if (TextUtils.isEmpty(signedData) || TextUtils.isEmpty(signature)) {
            return false;
        }
        PublicKey key = generatePublicKey(PUBLIC_KEY);
        return verify(key, signedData, signature);
    }

    public static PublicKey generatePublicKey(String encodedPublicKey) {
        try {
            byte[] decodedKey = Base64.decode(encodedPublicKey, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
            return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("RSA not available", e);
        } catch (InvalidKeySpecException e) {
            Log.e(TAG, "Invalid key specification.");
            throw new IllegalArgumentException(e);
        }
    }

    public static boolean verify(PublicKey publicKey, String signedData, String signature) {
        try {
            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initVerify(publicKey);
            sig.update(signedData.getBytes());
            if (!sig.verify(Base64.decode(signature, Base64.DEFAULT))) {
                Log.e(TAG, "Signature verification failed.");
                return false;
            }
            return true;
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "NoSuchAlgorithmException.");
        } catch (InvalidKeyException e) {
            Log.e(TAG, "Invalid key specification.");
        } catch (SignatureException e) {
            Log.e(TAG, "SignatureTest exception.");
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Base64 decoding failed.");
        }
        return false;
    }

    public static String generatePayload() {
        char[] payload;
        final char[] specials = {'~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '-', '{', '}', '|', '\\', '/', '.',
                '.', '=', '[', ']', '?', '<', '>'};
        StringBuilder buffer = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch) {
            buffer.append(ch);
        }
        for (char ch = 'a'; ch <= 'z'; ++ch) {
            buffer.append(ch);
        }
        for (char ch = 'A'; ch <= 'Z'; ++ch) {
            buffer.append(ch);
        }

        for (char ch : specials) {
            buffer.append(ch);
        }

        payload = buffer.toString().toCharArray();

        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        //length : 20자
        for (int i = 0; i < 20; i++) {
            randomString.append(payload[random.nextInt(payload.length)]);
        }

        return randomString.toString();
    }
}
