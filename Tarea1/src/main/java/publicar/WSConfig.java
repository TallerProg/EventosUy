package publicar;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class WSConfig {
    private static final Properties P = new Properties();
    private static final Path EXTERNAL_PATH = Path.of("/ens/devel01/tpgr27/eventosUy/ws.properties");

    static {
        if (!loadFromExternal()) {
            loadFromClasspath();
        }
    }

    private WSConfig() {
        // Utility
    }

    private static boolean loadFromExternal() {
        try (InputStream in = Files.newInputStream(EXTERNAL_PATH)) {
            P.load(in);
            System.out.println("[WSConfig] Propiedades cargadas desde " + EXTERNAL_PATH);
            return true;
        } catch (Exception e) {
            System.err.println("[WSConfig] No se pudo leer " + EXTERNAL_PATH + ": " + e.getMessage());
            return false;
        }
    }

    private static void loadFromClasspath() {
        try (InputStream in = WSConfig.class.getResourceAsStream("/ws.properties")) {
            if (in != null) {
                P.load(in);
                System.out.println("[WSConfig] Propiedades cargadas desde el classpath");
            } else {
                System.err.println("[WSConfig] No se encontró /ws.properties en el classpath.");
            }
        } catch (Exception e) {
            System.err.println("[WSConfig] Error cargando ws.properties del classpath: " + e.getMessage());
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
