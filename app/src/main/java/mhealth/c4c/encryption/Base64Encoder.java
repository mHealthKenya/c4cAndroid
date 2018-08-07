package mhealth.c4c.encryption;

import android.util.Base64;

public class Base64Encoder {


    public static final String encryptString(String x){

        byte[] encodeValue = Base64.encode(x.getBytes(), Base64.DEFAULT);
        return new String(encodeValue);

    }

    public static final String decryptedString(byte[] x){

        byte[] decodeValue = Base64.decode(x, Base64.DEFAULT);
        return new String(decodeValue);

    }
}
