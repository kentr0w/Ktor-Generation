package Feature.CoreFeatures.db.entity;

import Copy.CustomLogger;
import Copy.LogType;
import Feature.CoreFeatures.db.entity.routes.Route;
import Feature.CoreFeatures.routing.RouteDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DBEntity {
    
    private String name;
    private String file;
    private PrimaryKey primaryKey;
    private String tableName;
    private List<EntityField> entityFields;
    private Route route;
    
    private final String fieldTmp = "template/entity_build/EntityField.tmp";
    private final String entityTmp = "template/entity_build/Entity.tmp";
    private final String importTmp = "template/entity_build/import.tmp";
    
    public Route getRoute() {
        return route;
    }
    
    public void setRoute(Route route) {
        this.route = route;
    }
    
    public String getFieldTmp() {
        return fieldTmp;
    }
    
    public String getEntityTmp() {
        return entityTmp;
    }
    
    public String getImportTmp() {
        return importTmp;
    }
    
    public String getFile() {
        return file;
    }
    
    public void setFile(String file) {
        this.file = file;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public List<EntityField> getEntityFields() {
        return entityFields;
    }
    
    public void setEntityFields(List<EntityField> entityFields) {
        this.entityFields = entityFields;
    }
    
    public PrimaryKey getPrimaryKey() {
        return primaryKey;
    }
    
    public void setPrimaryKey(PrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<List<String>> getEntitiesText() {
        if (this.entityFields.stream().map(EntityField::getVariableName).collect(Collectors.toList()).contains(this.primaryKey.getIdName())) {
            CustomLogger.writeLog(LogType.ERROR, "Primary key contains non-existent variable");
            return null;
        }
        StringBuilder variable = new StringBuilder();
        List<List<String>> result = new ArrayList<>();
        for (EntityField field: entityFields) {
            List<String> text = new ArrayList(Arrays.asList(field.getVariableName(), field.getType().getTypeCode(), field.getColumnName()));
            if (field.getType().equals(EntityType.VARCHAR)) {
                if (field.getLength() == null) {
                    CustomLogger.writeLog(LogType.ERROR, this.name + " doesn't contain length for " + field.getColumnName());
                    return null;
                } else
                    text.add("," + field.getLength());
    
            } else {
                text.add("");
            }
            if (field.getFieldDetail() == null)
                text.add("");
            else
                text.add(field.getAllDetails());
            result.add(text);
        }
        return result;
    }
    
    public void start() {
        /*if (!this.entityFields.stream().map(EntityField::getVariableName).collect(Collectors.toList()).contains(this.primaryKey)) {
            CustomLogger.writeLog(LogType.ERROR, "Primary key contains non-existent variable");
            return;
        }
        StringBuilder variable = new StringBuilder();
        for (EntityField field: entityFields) {
            List<String> text = new ArrayList<>(Arrays.asList(field.getVariableName(), field.getType().getTypeCode(), field.getColumnName()));
            if (field.getType().equals(EntityType.VARCHAR)) {
                if (field.getLength() == null) {
                    CustomLogger.writeLog(LogType.ERROR, this.name + " doesn't contain length for " + field.getColumnName());
                    return;
                }
                else
                    text.add("," + field.getLength());
        
            } else {
                text.add("");
            }
            if(field.getFieldDetail() == null)
                text.add("");
            else
                text.add(field.getAllDetails());
            String codeWithOneOfField = getCodeAfterReplace(fieldTmp, field.getHash(), text) + System.lineSeparator();
            variable.append(codeWithOneOfField);
            CustomLogger.writeLog(LogType.INFO, field.getColumnName() + " was added to " + this.name);
        }
        if (duplicateCodeFromTemplateToFile(entityTmp, this.file))
            CustomLogger.writeLog(LogType.INFO, "Entity code was duplicated");
        else
            CustomLogger.writeLog(LogType.ERROR, "Entity code wasn't duplicated");
        if(Insertion.addImports(new File(this.file), new File(importTmp)))
            CustomLogger.writeLog(LogType.INFO, "Import was added");
        else
            CustomLogger.writeLog(LogType.ERROR, "Import wasn't added");
        List<String> hash = Stream.of("entityName", "tableName", "entityFields", "primaryKey").map(DigestUtils::sha256Hex).collect(Collectors.toList());
        List<String> text = Arrays.asList(this.name, this.tableName, variable.toString(), this.primaryKey);
        for (int i = 0; i < hash.size(); i++) {
            if (replaceTextByHash(this.file, hash.get(i), text.get(i)))
                CustomLogger.writeLog(LogType.INFO, "Hash of " + text.get(i) + " was replaced");
            else
                CustomLogger.writeLog(LogType.ERROR, "Hash of " + text.get(i) + " wasn't replaced");
        }*/
    }
}
