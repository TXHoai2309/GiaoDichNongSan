package com.example.giaodichnongsan.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ImageUrlHelper {
    private static final Pattern GOOGLE_DRIVE_FILE_PATTERN =
            Pattern.compile("drive\\.google\\.com/file/d/([^/]+)");

    private ImageUrlHelper() {}

    public static String normalize(String value) {
        if (value == null) return "";

        String url = value.trim();
        if (TextUtils.isEmpty(url)) return "";

        Matcher driveMatcher = GOOGLE_DRIVE_FILE_PATTERN.matcher(url);
        if (driveMatcher.find()) {
            return "https://drive.google.com/uc?export=view&id=" + driveMatcher.group(1);
        }

        if (url.contains("dropbox.com") && url.contains("?dl=0")) {
            return url.replace("?dl=0", "?raw=1");
        }

        return url;
    }

    public static boolean isHttpUrl(String value) {
        String url = normalize(value);
        return url.startsWith("http://") || url.startsWith("https://");
    }

    public static boolean isSupportedImageSource(String value) {
        String url = normalize(value);
        return isHttpUrl(url) || url.startsWith("data:image/");
    }
}
