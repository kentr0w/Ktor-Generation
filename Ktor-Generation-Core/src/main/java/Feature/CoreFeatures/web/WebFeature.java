package Feature.CoreFeatures.web;

import Copy.CustomLogger;
import Copy.Insertion;
import Copy.LogType;
import Feature.Logic.Feature;
import Feature.Logic.FeatureObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Constant.Constant.MAIN_FUN;

public class WebFeature extends FeatureObject {

    private final String webTmpCode = "template/web_build/static.tmp";
    private final String webTmpRes = "template/web_build/res.tmp";
    private final String webImports = "template/web_build/import.tmp";
    
    
    private String path;
    private String name;
    private String template;
    private List<WebResource> resources;

    public WebFeature() {
        super("web");
    }
    
    public String getPath() {
        String missPartOfPath = super.calculatePath(this.path);
        if (!missPartOfPath.equals(""))
            this.setPath(missPartOfPath + File.separator + this.path);
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WebResource> getResources() {
        return resources;
    }

    public void setResources(List<WebResource> resources) {
        this.resources = resources;
    }

    @Override
    public void start() {
        CustomLogger.writeLog(LogType.INFO, "Starting to implement web-feature");
        if (!checkTemplateFiles()) {
            CustomLogger.writeLog(LogType.ERROR, "Web-feature won't be implemented");
            return;
        }
        StringBuilder code = new StringBuilder();
        for(WebResource resource: this.resources) {
            resource.setResource("template" + File.separator + resource.getResource());
            List<String> text = Arrays.asList(resource.getRemotePath(), resource.getResource());
            code.append(getCodeAfterReplace(webTmpRes, resource.getHash(), text));
        }
        if (duplicateCodeFromTemplateToFile(webTmpCode, this.getPath())) {
            CustomLogger.writeLog(LogType.INFO, "Web feature's code was duplicated");
            List<String> hash = Stream.of("webName", "webStatic").map(DigestUtils::sha256Hex).collect(Collectors.toList());
            List<String> text = Arrays.asList(this.name, code.toString());
            if (replaceListTextByHash(this.getPath(), hash, text))
                CustomLogger.writeLog(LogType.INFO, "Hash was replaced in web-feature");
            else
                CustomLogger.writeLog(LogType.ERROR, "Hash wasn't correct replaces in web-feature");
            if(Insertion.addImports(new File(this.getPath()), new File(webImports)))
                CustomLogger.writeLog(LogType.INFO, "Import was added");
            else
                CustomLogger.writeLog(LogType.ERROR, "Import wasn't added");
            String pathToApplicationFile = getSrcPath(this.getPath()) + File.separator + "Application.kt";
            if (Insertion.insertCodeWithImportInFile(new File(this.getPath()), new File(pathToApplicationFile), MAIN_FUN, this.name + "()")) {
                CustomLogger.writeLog(LogType.INFO, "Added web-feature to main");
            } else {
                CustomLogger.writeLog(LogType.ERROR, "Couldn't added web-feature to main");
            }
        } else {
            CustomLogger.writeLog(LogType.ERROR, "Web feature's code wasn't duplicated");
        }
    }

    private Boolean checkTemplateFiles() {
        if (!(new File(this.template).exists())) {
            CustomLogger.writeLog(LogType.ERROR, "");
            return false;
        }
        List<String> templsPath = this.resources.stream().map(WebResource::getResource).collect(Collectors.toList());
        for(String templPath: templsPath) {
            String correctTemplPath = templPath.replaceAll("/", File.separator);
            if (!(new File(this.template + File.separator + correctTemplPath)).exists())
                return false;
        }
        CustomLogger.writeLog(LogType.INFO, "Template files is correct");
        return copyTemplatesToConfig();
    }

    private Boolean copyTemplatesToConfig() {
        String pathToResources = (new File(getSrcPath(this.getPath()))).getParent() + File.separator + "resources";
        try{
            Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxrwxrwx"); // TODO check on Windows!
            Path path = Files.createDirectories(Path.of(pathToResources + File.separator + "template"), PosixFilePermissions.asFileAttribute(perms));
            CustomLogger.writeLog(LogType.INFO, "Templates folder was created");
            FileUtils.copyDirectory(new File(this.template), path.toFile());
            CustomLogger.writeLog(LogType.INFO, "Template folder was copied");
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            CustomLogger.writeLog(LogType.ERROR, "Error with copy template file");
            return false;
        }
    }

    @Override
    public String getId() {
        return this.id;
    }
}
