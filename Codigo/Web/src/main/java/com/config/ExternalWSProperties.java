package com.config;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Properties;


final class ExternalWSProperties {
    private static final Path EXTERNAL_PATH = Path.of("/ens/devel01/tpgr27/eventosUy/ws.properties");
    private static final Properties PROPS = new Properties();
    private static volatile long lastLoadedTimestamp = -1L;

    private ExternalWSProperties() {
    }

    static String get(String key, String defaultValue) {
        ensureLatest();
        return PROPS.getProperty(key, defaultValue);
    }

    private static void ensureLatest() {
        long externalTimestamp = readExternalTimestamp();
        if (externalTimestamp > 0 && externalTimestamp != lastLoadedTimestamp) {
            synchronized (PROPS) {
                if (externalTimestamp != lastLoadedTimestamp) {
                    loadFromExternal(externalTimestamp);
                }
            }
        } else if (lastLoadedTimestamp == -1L) {
            synchronized (PROPS) {
                if (lastLoadedTimestamp == -1L) {
                    loadFromClasspath();
                    lastLoadedTimestamp = 0L; 
                }
            }
        }
    }

    private static long readExternalTimestamp() {
        try {
            FileTime time = Files.getLastModifiedTime(EXTERNAL_PATH);
            return time.toMillis();
        } catch (Exception ignored) {
            return -1L;
        }
    }

    private static void loadFromExternal(long externalTimestamp) {
        try (InputStream in = Files.newInputStream(EXTERNAL_PATH)) {
            Properties latest = new Properties();
            latest.load(in);
            PROPS.clear();
            PROPS.putAll(latest);
            lastLoadedTimestamp = externalTimestamp;
            System.out.println("[ExternalWSProperties] Cargado " + EXTERNAL_PATH);
        } catch (Exception e) {
            System.err.println("[ExternalWSProperties] Error leyendo " + EXTERNAL_PATH + ": " + e.getMessage());
        }
    }

    private static void loadFromClasspath() {
        try (InputStream in = ExternalWSProperties.class.getResourceAsStream("/wsclient.properties")) {
            if (in != null) {
                PROPS.load(in);
                System.out.println("[ExternalWSProperties] Fallback al wsclient.properties del WAR");
            } else {
                System.err.println("[ExternalWSProperties] No se encontró wsclient.properties en el WAR");
            }
        } catch (Exception e) {
            System.err.println("[ExternalWSProperties] Error leyendo wsclient.properties: " + e.getMessage());
        }
    }
}
