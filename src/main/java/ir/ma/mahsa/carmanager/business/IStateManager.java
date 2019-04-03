package ir.ma.mahsa.carmanager.business;

import java.io.IOException;

public interface IStateManager {
    void saveState(IStatefull<?> statefull) throws IOException;
    <S> void retrieveState(IStatefull<S> statefull) throws IOException, ClassNotFoundException;
    boolean isAutoSave();
}
