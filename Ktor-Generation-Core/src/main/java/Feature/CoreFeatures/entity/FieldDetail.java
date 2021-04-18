package Feature.CoreFeatures.entity;

public enum FieldDetail {
    AUTOINCREMENT (".autoIncrement()"),
    NULLABLE (".nullable()"),
    UNIQUE_INDEX(".uniqueIndex()"),
    AUTO_INCREMENT(".autoIncrement()"),
    ;
    
    private final String detailCode;
    
    public String getDetailCode() {
        return detailCode;
    }
    
    FieldDetail(String detailCode) {
        this.detailCode = detailCode;
    }
}
