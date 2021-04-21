import Handler.Core;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        /*System.out.println(DigestUtils.sha256Hex("db-feature-preHost"));
        System.out.println(DigestUtils.sha256Hex("db-feature-hostName"));
        System.out.println(DigestUtils.sha256Hex("db-feature-port"));
        System.out.println(DigestUtils.sha256Hex("db-feature-dbName"));
        System.out.println(DigestUtils.sha256Hex("db-feature-dbDriver"));
        System.out.println(DigestUtils.sha256Hex("db-feature-username"));
        System.out.println(DigestUtils.sha256Hex("db-feature-password"));*/
    
        Stream.of("dbRoutingApplicationName", "dbRoutingEntityNamePath", "dbStandardRoutingCode").map(DigestUtils::sha256Hex).forEach(it -> {
            System.out.println(it);
        });
        System.out.println();
    
        Stream.of("routingVariableName", "routingEntityName", "routingEntityName", "routingCopyVariableCode").map(DigestUtils::sha256Hex).forEach(it -> {
            System.out.println(it);
        });
        
        Core core = new Core();
        core.start();
    }
}
