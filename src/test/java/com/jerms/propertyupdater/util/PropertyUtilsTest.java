package com.jerms.propertyupdater.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.jerms.propertyupdater.util.PropertyUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PropertyUtilsTest {
    @TempDir
    public File tempDir;

    @Test
    public void testUpdatePropertiesFile() throws IOException {
        // Create a test properties file
        File testFile = new File(tempDir, "test.properties");
        Properties initialProps = new Properties();
        initialProps.setProperty("key1", "value1");
        initialProps.store(new FileOutputStream(testFile), null);

        // Define properties to update
        Map<String, String> updates = new HashMap<>();
        updates.put("key1", "updated_value1");
        updates.put("key2", "value2");

        // Call the update method
        updatePropertiesFile(testFile.getAbsolutePath(), updates);

        // Verify the updates
        Properties updatedProps = new Properties();
        updatedProps.load(new FileInputStream(testFile));
        assertEquals("updated_value1", updatedProps.getProperty("key1"));
        assertEquals("value2", updatedProps.getProperty("key2"));
    }

    @Test
    public void testLoadPropertiesFromString() {
        String propertiesString = "key1=value1\n" +
                " key2 = value2\n" + // Test with extra spaces
                "# This is a comment\n" +
                "key3=value3";

        Map<String, String> expectedProperties = new HashMap<>();
        expectedProperties.put("key1", "value1");
        expectedProperties.put("key2", "value2");
        expectedProperties.put("key3", "value3");

        // Call the method
        Map<String, String> loadedProperties = loadPropertiesFromString(propertiesString);

        // Verify the loaded properties
        assertEquals(expectedProperties, loadedProperties);
    }

    @Test
    public void testCopyFileFromNetwork() throws IOException {
        // Create a temporary test file
        File testFile = new File(tempDir, "test.txt");
        String testContent = "This is some test data";
        FileOutputStream outputStream = new FileOutputStream(testFile);
        outputStream.write(testContent.getBytes());
        outputStream.close();

        // Define source and target URLs (replace with actual URLs for your test)
        String sourceUrl = "file://" + testFile.getAbsolutePath();
        String targetUrl = "file://" + new File(tempDir, "copied_test.txt").getAbsolutePath();

        // Call the copy method
        copyFileFromNetwork(sourceUrl, targetUrl);

        // Verify that the target file was created and has the same content
        File copiedFile = new File(tempDir, "copied_test.txt");
        assertTrue(copiedFile.exists());
        assertEquals(testContent, new String(java.nio.file.Files.readAllBytes(copiedFile.toPath())));
    }
}
