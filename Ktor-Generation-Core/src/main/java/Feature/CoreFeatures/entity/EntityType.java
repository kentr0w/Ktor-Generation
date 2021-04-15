package Feature.CoreFeatures.entity;

public enum EntityType {
    INTEGER ("integer"),
    VARCHAR("varchar"),
    DOUBLE ("double"),
    DATETIME ("datetime");
    
    private final String typeCode;
    
    public String getTypeCode() {
        return typeCode;
    }
    
    EntityType(String typeCode) {
        this.typeCode = typeCode;
    }
}
