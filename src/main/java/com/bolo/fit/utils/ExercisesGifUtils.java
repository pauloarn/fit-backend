package com.bolo.fit.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ExercisesGifUtils {

    public static String getGifBase64(String gifUrl) throws IOException {
        URL url = new URL(gifUrl);
        InputStream is = url.openStream();
        byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(is);
        return Base64.encodeBase64String(bytes);
    }
}
