import Feature.Project;
import Handler.Core;
import Reader.ConfigReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/config.yaml";
        Core core = new Core(path);
        core.start();
    }
}
