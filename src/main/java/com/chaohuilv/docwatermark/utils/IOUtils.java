package com.chaohuilv.docwatermark.utils;

import java.io.Closeable;

public class IOUtils {

    public static void close(Closeable x) {
        if (x != null) {
            try {
                x.close();
            } catch (Exception e) {
                // skip
            }
        }
    }


}
