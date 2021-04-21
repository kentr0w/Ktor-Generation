package Feature.CoreFeatures.db.entity;

public enum EntityType {
    INTEGER ("integer"),
    VARCHAR("varchar"),
    LONG("long"),
    DOUBLE ("double"),
    DATETIME ("datetime");
    
    private final String typeCode;
    
    public String getTypeCode() {
        return typeCode;
    }
    
    EntityType(String typeCode) {
        this.typeCode = typeCode;
    }
    
    public String typeToKotlinCode() {
        switch (this){
            case DOUBLE:
                return "Double";
            case INTEGER:
                return "Int";
            case DATETIME:
                return "LocalDataTime";
            case LONG:
                return "Long";
            default:
                return "String";
        }
    }
    
    public String typeCast() {
        switch (this) {
            case LONG:
                return ".toLong()";
            case INTEGER:
                return ".toInt()";
            default:
                return "";
        }
    }
    
    public String routingCast() {
        switch (this) {
            case LONG:
                return ".toLongOrNull()";
            default:
                return ".toIntOrNull()";
        }
    }
}
