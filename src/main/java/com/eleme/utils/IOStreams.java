package com.eleme.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOStreams {

    public static byte[] readAllBytes(InputStream in) throws IOException {
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int n;
        while ((n = in.read(buffer)) > 0) {
            sink.write(buffer, 0, n);
        }
        return sink.toByteArray();
    }

}
