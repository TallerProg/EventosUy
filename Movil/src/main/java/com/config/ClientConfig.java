package com.config;

import java.io.InputStream;
import java.util.Properties;

public final class ClientConfig {
  private static final Properties P = new Properties();
  static {
    try (InputStream in = ClientConfig.class.getResourceAsStream("/wsclient.properties")) {
      if (in != null) P.load(in);
      else System.err.println("[ClientConfig] No se encontró /wsclient.properties");
    } catch (Exception ignored) {}

    System.getProperties().forEach((k,v)->{
      var key = String.valueOf(k);
      if (key.startsWith("sc.") || key.startsWith("timeout.")) {
        P.setProperty(key, String.valueOf(v));
      }
    });
  }

  public static String get(String k, String def){ return P.getProperty(k, def); }

  public static int getInt(String k, int def){
    try { return Integer.parseInt(P.getProperty(k)); } catch(Exception e){ return def; }
  }
}
