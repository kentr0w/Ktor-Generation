package Feature.CoreFeatures.routing;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RouteDetail {
    private String url;
    private List<Request> requests;
    private final List<String> hash = Arrays.asList("routePath", "requests");
    
    public RouteDetail() {}
    
    public List<String> getHash() {
        return hash.stream().map(it -> DigestUtils.sha256Hex(it)).collect(Collectors.toList());
    }
    
    public List<Request> getRequests() {
        return requests;
    }
    
    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
}
