package com.qa.config;

import com.qa.logger.Log;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class Config {

    /**
     * Method to initialize configuration.
     */
    public Properties initializeConfig() {
        Properties conf = new Properties();
        try (FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + File.separator +  "src/main/resources/config.properties")) {
            conf.load(fis);

            Map<String, String> environmentConfig = null;

            String environmentProperty = System.getProperty("environment");

            String environment = (environmentProperty != null) ? environmentProperty : conf.getProperty("ENV_NAME");

            environmentConfig = parseYaml(System.getProperty("user.dir") + File.separator + "src/main/resources/environments.yml", environment);

            conf = loadDeviceProperty(environmentConfig, conf);

        } catch (Exception e) {
            e.printStackTrace();
            Log.warn(e.getMessage());
        }
        return conf;
    }

    /**
     * Method to load properties specific for the emulator/device
     *
     * @param: properties
     * the map of emulator properties read from the sessions-sit.yml
     * file
     */

    public Properties loadDeviceProperty(Map<String, String> properties, Properties config) {
        for (Entry<String, String> iterator : properties.entrySet()) {
            config.setProperty(iterator.getKey(), iterator.getValue());
        }
        return config;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> parseYaml(String filename, String parameter) throws IOException {
        FileInputStream fis = null;
        Map<String, Object> platforms;
        Map<String, String> configs = null;
        try {
            fis = new FileInputStream(filename);
            platforms = new Yaml().load(fis);
            for (Entry<String, Object> key : platforms.entrySet()) {
                if (key.getKey().equalsIgnoreCase(parameter)) {
                    configs = (Map<String, String>) key.getValue();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.warn(e.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return configs;
    }

}
