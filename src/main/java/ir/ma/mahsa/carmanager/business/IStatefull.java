package ir.ma.mahsa.carmanager.business;

public interface IStatefull<S> {
    S getState();
    void setState(S state);
}
