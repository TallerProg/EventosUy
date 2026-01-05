package publicar;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public final class WSConfig {
    private static final Properties P = new Properties();
    // Ruta externa única solicitada
    private static final String EXTERNAL_PATH = "/ens/devel01/tpgr27/eventsUy/ws.properties";

    static {
        try (InputStream in = new FileInputStream(EXTERNAL_PATH)) {
            if (in != null) {
                P.load(in);
                System.out.println("[WSConfig] Cargado desde " + EXTERNAL_PATH);
            } else {
                System.err.println("[WSConfig] No se encontró " + EXTERNAL_PATH);
            }
        } catch (Exception e) {
            System.err.println("[WSConfig] Error cargando " + EXTERNAL_PATH + ": " + e.getMessage());
        }
        System.out.println("[WSConfig] working dir = " + new java.io.File(".").getAbsolutePath());
    }

    public static String get(String key, String defaultValue) {
        return P.getProperty(key, defaultValue);
    }

    public static boolean getBool(String key, boolean defaultValue) {
        String v = P.getProperty(key);
        return v == null ? defaultValue : v.equalsIgnoreCase("true");
    }
}
