package src;

interface Observer {
    void notify(String message);
}

class LoggerObserver implements Observer {
    @Override
    public void notify(String message) {
        System.out.println("[LOG] " + message);
    }
}

class AlertObserver implements Observer {
    @Override
    public void notify(String message) {
        System.out.println("[ALERT] " + message);
    }
}
