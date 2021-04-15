package Feature.CoreFeatures.entity;

public enum FieldDetail {
    AUTOINCREMENT (".autoIncrement()"),
    NULLABLE (".nullable()"),
    ;
    
    private final String detailCode;
    
    public String getDetailCode() {
        return detailCode;
    }
    
    FieldDetail(String detailCode) {
        this.detailCode = detailCode;
    }
}
