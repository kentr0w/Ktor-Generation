package Feature.CoreFeatures.socket;

import Copy.CustomLogger;
import Copy.Insertion;
import Copy.LogType;
import Feature.Logic.Feature;
import Feature.Logic.FeatureObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Constant.Constant.MAIN_FUN;

public class SocketFeature extends FeatureObject {
    
    private String path;
    private String name;
    private String webPath;
    private String answer;
    private String closeWord;
    private String closeMessage;
    
    private final String socketTmp = "template/socket_build/socket.tmp";
    private final String importTmp = "template/socket_build/import.tmp";
    
    public String getPath() {
        String missPartOfPath = super.calculatePath(this.path);
        if (!missPartOfPath.equals(""))
            this.setPath(missPartOfPath + File.separator + this.path);
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getWebPath() {
        return webPath;
    }
    
    public void setWebPath(String webPath) {
        this.webPath = webPath;
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
        List<String> text = Arrays.asList(this.name, this.webPath, this.answer, this.closeWord, this.closeMessage);
        InputStream socketStream = SocketFeature.class.getClassLoader().getResourceAsStream(socketTmp);
        duplicateCodeFromTemplateToFile(socketStream, this.getPath());
        try {
            socketStream.close();
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
        }
        replaceListTextByHash(this.getPath(), hash, text);
        InputStream socketImportsStream = SocketFeature.class.getClassLoader().getResourceAsStream(importTmp);
        Insertion.addImports(new File(this.getPath()), socketImportsStream);
        try {
            socketImportsStream.close();
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
        }
        String pathToApplicationFile = getSrcPath(this.getPath()) + File.separator + "Application.kt";
        Insertion.insertCodeWithImportInFile(new File(this.getPath()), new File(pathToApplicationFile), MAIN_FUN, this.name + "()");
    }
    
    @Override
    public String getId() {
        return null;
    }
}
