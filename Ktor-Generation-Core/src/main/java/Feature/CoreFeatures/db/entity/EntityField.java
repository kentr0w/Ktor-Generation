package Feature.CoreFeatures.db.entity;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityField {
    private String variableName;
    private String columnName;
    private EntityType type;
    private List<FieldDetail> fieldDetail;
    private String length;
    private final List<String> hash = Arrays.asList("entityVariableName", "entityType", "entityColumnName", "entityLength", "entityDetails");
    
    public EntityField() {}
    
    public String getAllDetails() {
        return fieldDetail.stream().map(it -> it.getDetailCode()).collect(Collectors.joining());
    }
    
    public List<String> getHash() {
        return hash.stream().map(it -> DigestUtils.sha256Hex(it)).collect(Collectors.toList());
    }
    
    public String getLength() {
        return length;
    }
    
    public void setLength(String length) {
        this.length = length;
    }
    
    public String getVariableName() {
        return variableName;
    }
    
    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    public EntityType getType() {
        return type;
    }
    
    public void setType(EntityType type) {
        this.type = type;
    }
    
    public List<FieldDetail> getFieldDetail() {
        return fieldDetail;
    }
    
    public void setFieldDetail(List<FieldDetail> fieldDetail) {
        this.fieldDetail = fieldDetail;
    }
}
