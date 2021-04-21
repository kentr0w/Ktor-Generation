package Feature.CoreFeatures.db;

import org.apache.commons.lang3.tuple.Pair;

public enum DataBaseType {
    MYSQL ("com.mysql.cj.jdbc.Driver", "jdbc:mysql://"),
    PSQL ("org.postgresql.Driver", "jdbc:postgresql://"),
    H2 ("org.h2.Driver", "jdbc:h2:");
    
    
    public String getDbDriver() {
        return dbDriver;
    }
    
    public String getHost() {
        return host;
    }
    
    private final String dbDriver;
    private final String host;
    
    DataBaseType(String dbDriver, String host) {
        this.dbDriver = dbDriver;
        this.host = host;
    }
}
