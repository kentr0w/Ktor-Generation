package Feature.CoreFeatures.db.entity;

import Copy.CustomLogger;
import Copy.LogType;
import Feature.CoreFeatures.db.entity.routes.Route;
import Feature.Logic.Feature;
import Feature.Logic.FeatureObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DBEntity {
    
    private String name;
    private String path;
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
    
    public String getPath() {
        List<String> pathToFile = Feature.getInstance().getProjectPathFromGlobal();
        List<String> missPartOfPath = new ArrayList<>();
        if (!path.startsWith(pathToFile.stream().collect(Collectors.joining(File.separator)))) {
            int count = 0;
            while (count < pathToFile.size() && !path.startsWith(pathToFile.get(count))) {
                missPartOfPath.add(pathToFile.get(count));
                count++;
            }
        }
        if (!missPartOfPath.isEmpty())
            this.setPath(missPartOfPath.stream().collect(Collectors.joining(File.separator)) + File.separator + this.path);
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
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
    
}
