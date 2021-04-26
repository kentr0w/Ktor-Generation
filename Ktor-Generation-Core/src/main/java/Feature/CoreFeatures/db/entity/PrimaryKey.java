package Feature.CoreFeatures.db.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PrimaryKey {
    private String idName;
    private EntityType type;
    private String length;
    private List<FieldDetail> fieldDetail;
    
    public String getLength() {
        if (length == null)
            return "";
        return ", " + length;
    }
    
    public void setLength(String length) {
        this.length = length;
    }
    
    public String getAllDetails() {
        return getFieldDetail().stream().map(it -> it.getDetailCode()).collect(Collectors.joining());
    }
    
    public PrimaryKey() {}
    
    public String getIdName() {
        return idName;
    }
    
    public void setIdName(String idName) {
        this.idName = idName;
    }
    
    public EntityType getType() {
        return type;
    }
    
    public void setType(EntityType type) {
        this.type = type;
    }
    
    public List<FieldDetail> getFieldDetail() {
        if (fieldDetail == null)
            return Arrays.asList(FieldDetail.ENTITY_ID, FieldDetail.AUTOINCREMENT);
        if (!fieldDetail.contains(FieldDetail.ENTITY_ID))
            fieldDetail.add(FieldDetail.ENTITY_ID);
        if (!fieldDetail.contains(FieldDetail.AUTOINCREMENT))
            fieldDetail.add(FieldDetail.AUTOINCREMENT);
        return fieldDetail;
    }
    
    public void setFieldDetail(List<FieldDetail> fieldDetail) {
        this.fieldDetail = fieldDetail;
    }
}
