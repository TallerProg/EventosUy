package cliente.ws.sc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public final class Conf {
    private static final Properties P = new Properties();
    private static final String EXTERNAL_PATH = "/ens/devel01/tpgr27/eventsUy/ws.properties";

    static {
        try (InputStream in = new FileInputStream(EXTERNAL_PATH)) {
            if (in != null) {
                P.load(in);
                System.out.println("[Conf] Cargado desde " + EXTERNAL_PATH);
            } else {
                System.err.println("[Conf] No se encontró " + EXTERNAL_PATH);
            }
        } catch (java.lang.Exception e) {
            System.err.println("[Conf] Error cargando " + EXTERNAL_PATH + ": " + e.getMessage());
        }
        System.out.println("[Conf] working dir = " + new java.io.File(".").getAbsolutePath());
    }

    public static String get(String key) {
        // No forzar sufijo ?wsdl; que el archivo tenga el valor completo requerido
        return P.getProperty(key);
    }

    public static boolean getBool(String key, boolean defaultValue) {
        String v = P.getProperty(key);
        return v == null ? defaultValue : v.equalsIgnoreCase("true");
    }
}
