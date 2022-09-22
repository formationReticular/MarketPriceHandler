package ab.demo;

import java.util.HashMap;
import java.util.Map;

public enum Instrument {
    EURtoUSD("EUR/USD"),
    GBPtoUSD("GBP/USD"),
    EURtoJPY("EUR/JPY");

    private static final Map<String, Instrument> DESC_MAP = new HashMap<>();

    static {
        for (Instrument instance : Instrument.values()) {
            DESC_MAP.put(instance.getDescription().toLowerCase(), instance);
        }
    }

    private final String description;

    Instrument(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Instrument get(String name) {
        return DESC_MAP.get(name.toLowerCase());
    }
}
