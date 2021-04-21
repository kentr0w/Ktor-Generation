package Feature.CoreFeatures.db.entity;

public enum FieldDetail {
    AUTOINCREMENT (".autoIncrement()"),
    NULLABLE (".nullable()"),
    UNIQUE_INDEX(".uniqueIndex()"),
    ENTITY_ID(".entityId()"),
    ;
    
    private final String detailCode;
    
    public String getDetailCode() {
        return detailCode;
    }
    
    FieldDetail(String detailCode) {
        this.detailCode = detailCode;
    }
}
