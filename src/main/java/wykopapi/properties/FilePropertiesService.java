package wykopapi.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

final class FilePropertiesService implements PropertiesService {
    private String appKey;
    private String secret;
    private String accountKey;

    FilePropertiesService() {
        try {
            Properties properties = new Properties();
            properties.load(new InputStreamReader(new FileInputStream("src/main/resources/application.properties")));
            appKey = properties.getProperty("appkey");
            secret = properties.getProperty("secret");
            accountKey = properties.getProperty("accountkey");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public String getAppKey() {
        return appKey;
    }

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public String getAccountKey() {
        return accountKey;
    }
}
