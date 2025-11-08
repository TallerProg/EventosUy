package cliente.ws.sc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public final class Conf {
    private static final Properties P = new Properties();

    static {
        try (InputStream in = new FileInputStream("C:/Users/morga/git/tpgr27/wsConfig.properties")) {
            if (in != null) {
                P.load(in);
            } else {
                System.err.println("[WSConfig] No se encontró /ws.properties en el classpath.");
            }
        } catch (java.lang.Exception e) {
            System.err.println("[WSConfig] Error cargando ws.properties: " + e.getMessage());
        }
        System.out.println("[WSConfig] working dir = " + new java.io.File(".").getAbsolutePath());
    }

    public static String get(String key) {
        return P.getProperty(key) + "?wsdl";
    }

    public static boolean getBool(String key, boolean defaultValue) {
        String v = P.getProperty(key);
        return v == null ? defaultValue : v.equalsIgnoreCase("true");
    }
}
