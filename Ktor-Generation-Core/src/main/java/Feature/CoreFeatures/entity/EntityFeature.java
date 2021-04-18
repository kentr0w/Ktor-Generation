package Feature.CoreFeatures.entity;

import Copy.CustomLogger;
import Copy.LogType;
import Feature.Logic.FeatureObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityFeature extends FeatureObject {
    
    private String name;
    private String file;
    private String primaryKey;
    private String tableName;
    private List<EntityField> entityFields;
    
    private static final String fieldTmp = "template/entity_build/EntityField.tmp";
    private static final String entityTmp = "template/entity_build/Entity.tmp";
    
    public EntityFeature() {
        super("entity");
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
    
    public String getPrimaryKey() {
        return primaryKey;
    }
    
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public void start() {
        String variable = "";
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
            if (codeWithOneOfField != null) {
                variable += codeWithOneOfField;
                CustomLogger.writeLog(LogType.INFO, field.getColumnName() + " was added to " + this.name);
            } else {
                CustomLogger.writeLog(LogType.ERROR, field.getColumnName() + " wasn't added to " + this.name);
            }
        }
        if (duplicateCodeFromTemplateToFile(entityTmp, this.file))
            CustomLogger.writeLog(LogType.INFO, "Entity code was duplicated");
        else
            CustomLogger.writeLog(LogType.ERROR, "Entity code wasn't duplicated");
        List<String> hash = Arrays.asList("entityName", "tableName", "entityFields", "primaryKey").stream().map(it -> DigestUtils.sha256Hex(it)).collect(Collectors.toList());
        List<String> text = Arrays.asList(this.name, this.tableName, variable, this.primaryKey);
        for (int i = 0; i < hash.size(); i++) {
            if (replaceTextByHash(this.file, hash.get(i), text.get(i)))
                CustomLogger.writeLog(LogType.INFO, "Hash of " + text.get(i) + " was replaced");
            else
                CustomLogger.writeLog(LogType.ERROR, "Hash of " + text.get(i) + " wasn't replaced");
        }
    }
    
    @Override
    public String getId() {
        return null;
    }
}
