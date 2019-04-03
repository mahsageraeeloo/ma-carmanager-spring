package ir.ma.mahsa.carmanager.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.List;
import java.util.Properties;

@Service
public class StateManagerFileImpl implements IStateManager, IHasLifeCycle {
    @Autowired
    private List<IStatefull> iStatefulls;
    @Value("${ir.ma.mahsa.carmanager.autoSave}")
    private boolean autoSave;

    @PostConstruct
    @Override
    public void initialize() {
        try {
//            List<IStatefull> iStatefulls = InstanceRegistry.getInstance().lookupMultiple(IStatefull.class);
            for (IStatefull iStatefull : iStatefulls) {
                retrieveState(iStatefull);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    @Override
    public void destroy() {
        if (isAutoSave()) {
            try {
//                List<IStatefull> iStatefulls = InstanceRegistry.getInstance().lookupMultiple(IStatefull.class);
                for (IStatefull iStatefull : iStatefulls) {
                    saveState(iStatefull);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveState(IStatefull<?> statefull) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./carList.bin", false));
        objectOutputStream.writeObject(statefull.getState());
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    @Override
    public <S> void retrieveState(IStatefull<S> statefull) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("./carList.bin"));
        statefull.setState((S) objectInputStream.readObject());
        objectInputStream.close();
    }

    @Override
    public boolean isAutoSave() {
        return autoSave;
    }
}
