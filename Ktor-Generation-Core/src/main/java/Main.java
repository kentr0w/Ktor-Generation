import Handler.Core;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Core core = new Core();
        core.start();
    }
}
