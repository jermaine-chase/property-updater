package com.jerms.propertyupdater.util;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtils {

    public static void updatePropertiesFile(String filePath, Map<String, String> properties) throws IOException {
        Properties props = new Properties();

        try (FileInputStream inputStream = new FileInputStream(filePath);
             FileOutputStream outputStream = new FileOutputStream(filePath)) {

            props.load(inputStream);

            // Update or add properties
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                props.setProperty(entry.getKey(), entry.getValue());
            }

            props.store(outputStream, null);

        }
    }

    public static Map<String, String> loadPropertiesFromString(String propertiesString) {
        Map<String, String> properties = new HashMap<>();

        for (String line : propertiesString.lines().toList()) {
            String[] parts = line.split("=");
            if (parts.length == 2) {
                properties.put(parts[0].trim(), parts[1].trim());
            } else {
                // Handle invalid lines (optional)
                // You can throw an exception, log a warning, or ignore the line
            }
        }

        return properties;
    }

    public static void copyFileFromNetwork(String sourceUrl, String targetUrl) throws IOException {
        URL source = new URL(sourceUrl);
        URL target = new URL(targetUrl);

        try (InputStream inputStream = source.openStream();
             OutputStream outputStream = target.openConnection().getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void parseInput(String input) {
        /*
         section 1: files to copy
         /resources/com/bcbsnc/bolapp/...

         section 2: files to update
         */
    }
}
