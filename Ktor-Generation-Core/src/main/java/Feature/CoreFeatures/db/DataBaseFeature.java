package Feature.CoreFeatures.db;

import Copy.CustomLogger;
import Copy.Insertion;
import Copy.LogType;
import Feature.CoreFeatures.db.entity.DBEntity;
import Feature.CoreFeatures.db.entity.EntityField;
import Feature.CoreFeatures.db.entity.routes.StandardRoute;
import Feature.Logic.Feature;
import Feature.Logic.FeatureObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Constant.Constant.MAIN_FUN;

public class DataBaseFeature extends FeatureObject {
    
    private final String connectionTmp = "template/db_build/connection.tmp";
    private final String connectionImportTmp = "template/db_build/connectionImport.tmp";
    private final String entityImportTmp = "template/db_build/entityImport.tmp";
    private final String objectTmp = "template/db_build/object.tmp";
    private final String dataVariableTmp = "template/db_build/dataVariable.tmp";
    private final String dataClassTmp = "template/db_build/dataClass.tmp";
    private final String entityClassVariableTmp = "template/db_build/entityClassVariable.tmp";
    private final String entityClassFunctionTmp = "template/db_build/entityClassFun.tmp";
    private final String entityClassTmp = "template/db_build/entityClass.tmp";
    private final String entityAllCodeTmp = "template/db_build/entityAllCode.tmp";
    
    private final String routingCopyFieldsTmp = "template/db_build/routing/routeCopyFields.tmp";
    private final String routingUpdateTmp = "template/db_build/routing/routingUpdate.tmp";
    private final String routingDeleteTmp = "template/db_build/routing/routingDelete.tmp";
    private final String routingNewTmp = "template/db_build/routing/routingNew.tmp";
    private final String routingSaveTmp = "template/db_build/routing/routingSave.tmp";
    private final String routingGetAllTmp = "template/db_build/routing/routingGetAll.tmp";
    private final String routeTmp = "template/db_build/routing/route.tmp";
    private final String routeImportTmp = "template/db_build/routing/routeImport.tmp";
    
    private final String ContentNegotiation = "install(ContentNegotiation) { gson { } }";
    
    private String path;
    private DataBaseType type;
    private String port;
    private String host;
    private String dbName;
    private String username;
    private String password;
    private List<DBEntity> entities;
    
    public DataBaseFeature() {
        super("db");
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
    
    public DataBaseType getType() {
        return type;
    }
    
    public void setType(DataBaseType type) {
        this.type = type;
    }
    
    public String getPort() {
        return port;
    }
    
    public void setPort(String port) {
        this.port = port;
    }
    
    public String getHost() {
        return host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public String getDbName() {
        return dbName;
    }
    
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<DBEntity> getEntities() {
        return entities;
    }
    
    public void setEntities(List<DBEntity> entities) {
        this.entities = entities;
    }
    
    @Override
    public void start() {
        if (!(new File(this.getPath()).exists())) {
            CustomLogger.writeLog(LogType.ERROR, "Non-existent path to file");
            return;
        }
        for(DBEntity entity: this.entities) {
            generateEntities(entity);
            generateRoutes(entity);
        }
        InputStream connectionTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(connectionTmp);
        duplicateCodeFromTemplateToFile(connectionTmpStream, this.getPath());
        try {
            connectionTmpStream.close();
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
        }
        Boolean result = getConnectionCode();
        InputStream connectionImportTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(connectionImportTmp);
        Insertion.addImports(new File(this.getPath()), connectionImportTmpStream);
        try {
            connectionImportTmpStream.close();
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
        }
        String pathToApplicationFile = getSrcPath(this.getPath()) + File.separator + "Application.kt";
        Insertion.insertCodeWithImportInFile(new File(this.getPath()), new File(pathToApplicationFile), MAIN_FUN, "dataBaseConnection()");
    }
    
    private Boolean generateRoutes(DBEntity entity) {
        if (entity.getRoute()!= null && entity.getRoute().getStandardRoutes()!= null){
            StringBuilder standardCode = new StringBuilder();
            for (StandardRoute standardRoute: entity.getRoute().getStandardRoutes()){
                standardCode.append(generateStandardRouteCode(entity, standardRoute));
            }
            InputStream routeTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(routeTmp);
            duplicateCodeFromTemplateToFile(routeTmpStream, entity.getPath());
            try {
                routeTmpStream.close();
            } catch (IOException exception) {
                CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
            }
            List<String> text = Arrays.asList(entity.getName(), entity.getName(), standardCode.toString());
            List<String> hash = Stream.of("dbRoutingApplicationName", "dbRoutingEntityNamePath", "dbStandardRoutingCode").map(DigestUtils::sha256Hex).collect(Collectors.toList());
            replaceListTextByHash(entity.getPath(), hash, text);
            InputStream routeImportTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(routeImportTmp);
            Insertion.addImports(new File(entity.getPath()), routeImportTmpStream);
            try {
                routeImportTmpStream.close();
            } catch (IOException exception) {
                CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
            }
            String pathToApplicationFile = getSrcPath(entity.getPath()) + File.separator + "Application.kt";
            Insertion.insertCodeWithImportInFile(new File(entity.getPath()), new File(pathToApplicationFile), MAIN_FUN, entity.getName() + "Route()");
            Insertion.insertCodeWithImportInFile(new File(pathToApplicationFile), new File(pathToApplicationFile), MAIN_FUN, ContentNegotiation);
            
        }
        return true;
    }
    
    private String generateStandardRouteCode(DBEntity entity, StandardRoute standardRoute) {
        String variableName = entity.getName().toLowerCase(Locale.ROOT);
        StringBuilder fieldsCode = new StringBuilder();
        switch (standardRoute) {
            case GETAll: {
                List<String> hash = Stream.of("routingGetAllName", "routingGetAllName", "routingGetAllName").map(DigestUtils::sha256Hex).collect(Collectors.toList());
                List<String> text = Collections.nCopies(3, entity.getName());
                InputStream routingGetAllTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(routingGetAllTmp);
                String result = getCodeAfterReplace(routingGetAllTmpStream, hash, text);
                try {
                    routingGetAllTmpStream.close();
                } catch (IOException exception) {
                    CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
                }
                return result;
            }
            case SAVE: {
                for(EntityField field: entity.getEntityFields()) {
                    List<String> hash = Stream.of("routingNameField", "routingVariableName", "routingNameField").map(DigestUtils::sha256Hex).collect(Collectors.toList());
                    List<String> text = Arrays.asList(field.getVariableName(), variableName, field.getVariableName());
                    InputStream routingNewTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(routingNewTmp);
                    fieldsCode.append(getCodeAfterReplace(routingNewTmpStream, hash, text) + System.lineSeparator());
                    try {
                        routingNewTmpStream.close();
                    } catch (IOException exception) {
                        CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
                    }
                }
                List<String> hash = Stream.of("routingEntityName", "routingVariableName", "routingEntityName", "routingEntityName", "routingCopyVariableCode").map(DigestUtils::sha256Hex).collect(Collectors.toList());
                List<String> text = Arrays.asList(entity.getName(), variableName, entity.getName(), entity.getName(), fieldsCode.toString());
                InputStream routingSaveTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(routingSaveTmp);
                String result = getCodeAfterReplace(routingSaveTmpStream, hash, text);
                try {
                    routingSaveTmpStream.close();
                } catch (IOException exception) {
                    CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
                }
                return result;
            }
            case DELETE: {
                String castFun = entity.getPrimaryKey().getType().routingCast();
                List<String> hash = Stream.of("routingDeleteEntityName", "routingDeleteCastFun", "routingDeleteEntityName").map(DigestUtils::sha256Hex).collect(Collectors.toList());
                List<String> text = Arrays.asList(entity.getName(), castFun, entity.getName());
                InputStream routingDeleteTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(routingDeleteTmp);
                String result = getCodeAfterReplace(routingDeleteTmpStream, hash, text);
                try {
                    routingDeleteTmpStream.close();
                } catch (IOException exception) {
                    CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
                }
                return result;
            }
            case UPDATE: {
                for(EntityField field: entity.getEntityFields()) {
                    List<String> hash = Stream.of("routingNameField", "routingVariableName", "routingNameField").map(DigestUtils::sha256Hex).collect(Collectors.toList());
                    List<String> text = Arrays.asList(field.getVariableName(), variableName, field.getVariableName());
                    InputStream routingCopyFieldsTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(routingCopyFieldsTmp);
                    fieldsCode.append(getCodeAfterReplace(routingCopyFieldsTmpStream, hash, text) + System.lineSeparator());
                    try {
                        routingCopyFieldsTmpStream.close();
                    } catch (IOException exception) {
                        CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
                    }
                }
                List<String> hash = Stream.of("routingDataClassName", "routingVariableName", "routingDataClassName", "routingEntityClassName", "routingVariableName",
                        "routingCopyVariableCode").map(DigestUtils::sha256Hex).collect(Collectors.toList());
                List<String> text = Arrays.asList(entity.getName(), variableName, entity.getName(), entity.getName(), variableName, fieldsCode.toString());
                InputStream routingUpdateTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(routingUpdateTmp);
                String result = getCodeAfterReplace(routingUpdateTmpStream, hash, text);
                try {
                    routingUpdateTmpStream.close();
                } catch (IOException exception) {
                    CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
                }
                return result;
            }
        }
        return "";
    }
    
    private Boolean generateEntities(DBEntity entity) {
        String entityCode = Arrays.asList(generateObject(entity), generateEntityClass(entity),
                generateDataClass(entity)).stream().collect(Collectors.joining(System.lineSeparator()));
        InputStream entityAllCodeTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(entityAllCodeTmp);
        duplicateCodeFromTemplateToFile(entityAllCodeTmpStream, entity.getPath());
        try {
            entityAllCodeTmpStream.close();
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
        }
        replaceTextByHash(entity.getPath(), DigestUtils.sha256Hex("entityAllCode"), entityCode);
        InputStream entityImportTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(entityImportTmp);
        Insertion.addImports(new File(entity.getPath()), entityImportTmpStream);
        try {
            entityImportTmpStream.close();
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
        }
        return true;
    }
    
    private String generateObject(DBEntity entity) {
        List<List<String>> fieldsTexts = entity.getEntitiesText();
        if (fieldsTexts == null) {
            return null;
        }
        StringBuilder variable = new StringBuilder();
        for (int i = 0; i < entity.getEntityFields().size(); i++) {
            EntityField field = entity.getEntityFields().get(i);
            InputStream entityFieldTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(entity.getFieldTmp());
            String codeWithOneOfField = getCodeAfterReplace(entityFieldTmpStream, field.getHash(), fieldsTexts.get(i)) + System.lineSeparator();
            try {
                entityFieldTmpStream.close();
            } catch (IOException exception) {
                CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
            }
            variable.append(codeWithOneOfField);
        }
        List<String> hash = Stream.of("entityObjectName", "objectTableType", "objectTableName",
                "objectIdType", "objectIdName", "objectIdLength", "objectIdDetail", "objectVariables").map(DigestUtils::sha256Hex).collect(Collectors.toList());
        List<String> text = Arrays.asList(entity.getName() + "Object",
                entity.getPrimaryKey().getType().typeToKotlinCode(),
                entity.getTableName(),
                entity.getPrimaryKey().getType().getTypeCode(),
                entity.getPrimaryKey().getIdName(),
                entity.getPrimaryKey().getLength(),
                entity.getPrimaryKey().getAllDetails(),
                variable.toString());
        InputStream objectTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(objectTmp);
        String result = getCodeAfterReplace(objectTmpStream, hash, text);
        try {
            objectTmpStream.close();
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
        }
        return result;
    }
    
    private String generateEntityClass(DBEntity entity) {
        StringBuilder variables = new StringBuilder();
        for (EntityField field: entity.getEntityFields()) {
            List<String> hash = Stream.of("entityClassVariableName", "entityClassVariableObjectName", "entityClassVariableType").map(DigestUtils::sha256Hex).collect(Collectors.toList());
            List<String> text = Arrays.asList(field.getVariableName(), entity.getName() , field.getVariableName());
            InputStream entityClassVariableTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(entityClassVariableTmp);
            variables.append(getCodeAfterReplace(entityClassVariableTmpStream, hash, text) + System.lineSeparator() + System.lineSeparator());
            try {
                entityClassVariableTmpStream.close();
            } catch (IOException exception) {
                CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
            }
        }
        List<String> hash = Stream.of("entityClassName", "entityClassIdType", "entityClassIdType", "entityClassIdType", "entityClassName",
                "entityObjectNameInEntityClass", "entityVariables", "entityCastFunction").map(DigestUtils::sha256Hex).collect(Collectors.toList());
        List<String> text = Arrays.asList(entity.getName(),
                entity.getPrimaryKey().getType().typeToKotlinCode(),
                entity.getPrimaryKey().getType().typeToKotlinCode(),
                entity.getPrimaryKey().getType().typeToKotlinCode(),
                entity.getName(),
                entity.getName(),
                variables.toString(),
                generateFunctionTo(entity)
        );
        InputStream entityClassTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(entityClassTmp);
        String result = getCodeAfterReplace(entityClassTmpStream, hash, text);
        try {
            entityClassTmpStream.close();
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
        }
        return result;
    }
    
    private String generateFunctionTo(DBEntity entity) {
        String idCast = entity.getPrimaryKey().getType().typeCast();
        String allVariables = entity.getEntityFields().stream().map(EntityField::getVariableName).collect(Collectors.joining(", "));
        List<String> text = Arrays.asList("to" + entity.getName() + "DataClass",
                entity.getName() + "DataClass",
                entity.getName() + "DataClass",
                idCast,
                allVariables);
        List<String> hash = Stream.of("castFunctionName", "dataClassName", "dataClassName", "idCast", "dataVariables").map(DigestUtils::sha256Hex).collect(Collectors.toList());
        InputStream entityClassFunctionTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(entityClassFunctionTmp);
        String result = getCodeAfterReplace(entityClassFunctionTmpStream, hash, text);
        try {
            entityClassFunctionTmpStream.close();
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
        }
        return result;
    }
    
    private String generateDataClass(DBEntity entity) {
        StringBuilder variables = new StringBuilder();
        InputStream dataVariableKeyTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(dataVariableTmp);
        variables.append(getCodeAfterReplace(dataVariableKeyTmpStream,
                Stream.of("dataVariableName", "dataVariableType").map(DigestUtils::sha256Hex).collect(Collectors.toList()),
                Arrays.asList(entity.getPrimaryKey().getIdName(), entity.getPrimaryKey().getType().typeToKotlinCode())) + "," + System.lineSeparator());
        try {
            dataVariableKeyTmpStream.close();
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
        }
        for (EntityField field: entity.getEntityFields()) {
            List<String> hash = Stream.of("dataVariableName", "dataVariableType").map(DigestUtils::sha256Hex).collect(Collectors.toList());
            List<String> text = Arrays.asList(field.getVariableName(), field.getType().typeToKotlinCode());
            InputStream dataVariableTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(dataVariableTmp);
            variables.append(getCodeAfterReplace(dataVariableTmpStream, hash, text) + "?," + System.lineSeparator());
            try {
                dataVariableTmpStream.close();
            } catch (IOException exception) {
                CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
            }
        }
        List<String> hash = Stream.of("dataClassNameInDataClass", "dataClassVariablesInDataClass").map(DigestUtils::sha256Hex).collect(Collectors.toList());
        List<String> text = Arrays.asList(entity.getName(), variables.toString());
        InputStream dataClassTmpStream = DataBaseFeature.class.getClassLoader().getResourceAsStream(dataClassTmp);
        String result = getCodeAfterReplace(dataClassTmpStream, hash, text);
        try {
            dataClassTmpStream.close();
        } catch (IOException exception) {
            CustomLogger.writeLog(LogType.ERROR, exception.getMessage());
        }
        return result;
    }
    
    private Boolean getConnectionCode() {
        List<String> hash = Arrays.asList("db-feature-preHost", "db-feature-hostName", "db-feature-port",
                "db-feature-dbName", "db-feature-dbDriver", "db-feature-username", "db-feature-password", "db-feature-entitiesName");
        hash = hash.stream().map(DigestUtils::sha256Hex).collect(Collectors.toList());
        this.entities.stream()
            .map(DBEntity::getPath)
            .collect(Collectors.toSet())
            .stream()
            .forEach(it -> Insertion.insertDependency(new File(it), new File(this.getPath())));
        List<String> text = Arrays.asList(this.type.getHost(), this.host, this.port, this.dbName, this.type.getDbDriver(), this.username,
                this.password, this.getEntities().stream().map(it -> it.getName() + "Object").collect(Collectors.joining(", ")));
        return replaceListTextByHash(this.getPath(), hash, text);
    }
    
    @Override
    public String getId() {
        return null;
    }
}
