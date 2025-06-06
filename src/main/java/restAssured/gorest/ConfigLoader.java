package restAssured.gorest;




import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigLoader {

    private static Map<String, Object> config;

    static {
        Yaml yaml = new Yaml();
        try (InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream("config.yaml")) {
            if (in == null) {
                throw new RuntimeException("config.yaml not found in resources");
            }
            config = yaml.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.yaml", e);
        }
    }

    public static String getApiToken() {
        return (String) ((Map<String, Object>) config.get("api")).get("token");
    }
}
