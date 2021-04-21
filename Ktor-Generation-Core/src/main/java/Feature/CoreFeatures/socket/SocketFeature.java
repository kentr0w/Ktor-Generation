package Feature.CoreFeatures.socket;

import Copy.Insertion;
import Feature.Logic.FeatureObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Constant.Constant.MAIN_FUN;

public class SocketFeature extends FeatureObject {
    
    private String file;
    private String name;
    private String path;
    private String answer;
    private String closeWord;
    private String closeMessage;
    
    private final String socketTmp = "template/socket_build/socket.tmp";
    private final String importTmp = "template/socket_build/import.tmp";
    
    public String getFile() {
        return file;
    }
    
    public void setFile(String file) {
        this.file = file;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public String getCloseWord() {
        return closeWord;
    }
    
    public void setCloseWord(String closeWord) {
        this.closeWord = closeWord;
    }
    
    public String getCloseMessage() {
        return closeMessage;
    }
    
    public void setCloseMessage(String closeMessage) {
        this.closeMessage = closeMessage;
    }
    
    public String getSocketTmp() {
        return socketTmp;
    }
    
    public SocketFeature() {
        super("socket");
    }
    
    @Override
    public void start() {
        List<String> hash = Stream.of("socketName", "socketPath", "socketAnswer", "socketCloseWord", "socketCloseMessage").map(DigestUtils::sha256Hex).collect(Collectors.toList());
        List<String> text = Arrays.asList(this.name, this.path, this.answer, this.closeWord, this.closeMessage);
        duplicateCodeFromTemplateToFile(socketTmp, this.file);
        replaceListTextByHash(this.file, hash, text);
        Insertion.addImports(new File(this.file), new File(importTmp));
        String pathToApplicationFile = getSrcPath(this.file) + File.separator + "Application.kt";
        Insertion.insertCodeWithImportInFile(new File(this.file), new File(pathToApplicationFile), MAIN_FUN, this.name + "()");
    }
    
    @Override
    public String getId() {
        return null;
    }
}
