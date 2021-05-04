package Feature.Logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class FeatureObject implements IFeature {
    protected String id;
    private Feature features = Feature.getInstance();
    
    protected String calculatePath(String path) {
        List<String> pathToFile = Feature.getInstance().getProjectPathFromGlobal();
        List<String> missPartOfPath = new ArrayList<>();
        if (!path.startsWith(pathToFile.stream().collect(Collectors.joining(File.separator)))) {
            int count = 0;
            while (count < pathToFile.size() && !path.startsWith(pathToFile.get(count))) {
                missPartOfPath.add(pathToFile.get(count));
                count++;
            }
        }
        return missPartOfPath.stream().collect(Collectors.joining(File.separator));
    }
    
    public FeatureObject(String id) {
        this.id = id;
        features.addFeature(this);
    }
}
