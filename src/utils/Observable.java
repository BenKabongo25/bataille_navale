package utils;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    /**
     * Liste des observateurs
     */
    protected List<Observer> observers;

    public Observable() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void delObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers)
            observer.updateObserver(this);
    }
}
