package Feature.CoreFeatures.web;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WebResource {
    private String remotePath;
    private String resource;
    private final List<String> hash = Arrays.asList("remotePath", "resource");

    public WebResource() {}

    public List<String> getHash() {
        return hash.stream().map(DigestUtils::sha256Hex).collect(Collectors.toList());
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
