import Handler.Core;
import org.apache.commons.codec.digest.DigestUtils;

public class Main {
    public static void main(String[] args) {
        System.out.println(DigestUtils.sha256Hex("webName"));
        System.out.println(DigestUtils.sha256Hex("webStatic"));
        Core core = new Core();
        core.start();
    }
}
