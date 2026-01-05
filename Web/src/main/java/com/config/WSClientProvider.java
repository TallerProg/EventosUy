package com.config;

import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 * Creates JAX-WS clients initialized with the URL provided in the shared ws.properties file.
 */
public final class WSClientProvider {
    private static final String PROPERTY_KEY = "ws.publish.url";
    private static final String DEFAULT_ENDPOINT = "http://127.0.0.1:9128/ws";

    private WSClientProvider() {
    }

    public static WebServicesService newService() {
        try {
            String endpoint = ExternalWSProperties.get(PROPERTY_KEY, DEFAULT_ENDPOINT);
            return new WebServicesService(new URL(toWsdl(endpoint)));
        } catch (MalformedURLException e) {
            throw new IllegalStateException("URL inválida en " + PROPERTY_KEY, e);
        }
    }

    public static WebServices newPort() {
        return newService().getWebServicesPort();
    }

    private static String toWsdl(String endpoint) {
        if (endpoint == null || endpoint.isBlank()) {
            return DEFAULT_ENDPOINT + "?wsdl";
        }
        String normalized = endpoint.trim();
        String lower = normalized.toLowerCase(Locale.ROOT);
        if (lower.contains("wsdl")) {
            return normalized;
        }
        if (normalized.endsWith("?")) {
            return normalized + "wsdl";
        }
        if (normalized.contains("?")) {
            return normalized;
        }
        return normalized + "?wsdl";
    }
}
