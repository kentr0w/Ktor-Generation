package Feature.CoreFeatures.routing;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Request {
    
    private  RequestType type;
    private String requestUrl;
    
    private List<String> hashList = Arrays.asList("requestPath", "requestType");
    
    public Request() {}
    
    public List<String> getHashList() {
        return hashList.stream().map(hash -> DigestUtils.sha256Hex(hash)).collect(Collectors.toList());
    }
    
    public void setHashList(List<String> hashList) {
        this.hashList = hashList;
    }
    
    public RequestType getType() {
        return type;
    }
    
    public void setType(RequestType type) {
        this.type = type;
    }
    
    public String getRequestUrl() {
        return requestUrl;
    }
    
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
