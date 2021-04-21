import Handler.Core;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Stream.of("dbRoutingApplicationName", "dbRoutingEntityNamePath", "dbStandardRoutingCode").map(DigestUtils::sha256Hex).forEach(it -> {
            System.out.println(it);
        });
        
        Core core = new Core();
        core.start();
    }
}
