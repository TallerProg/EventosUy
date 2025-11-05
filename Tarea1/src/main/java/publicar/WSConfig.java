package publicar;

import java.io.InputStream;
import java.util.Properties;

public final class WSConfig {
    private static final Properties P = new Properties();

    static {
        try (InputStream in = WSConfig.class.getResourceAsStream("/ws.properties")) {
            if (in != null) {
                P.load(in);
            } else {
                System.err.println("[WSConfig] No se encontró /ws.properties en el classpath.");
            }
        } catch (Exception e) {
            System.err.println("[WSConfig] Error cargando ws.properties: " + e.getMessage());
        }

        
    }

    public static String get(String key, String defaultValue) {
        return P.getProperty(key, defaultValue);
    }

    public static boolean getBool(String key, boolean defaultValue) {
        String v = P.getProperty(key);
        return v == null ? defaultValue : v.equalsIgnoreCase("true");
    }
}
