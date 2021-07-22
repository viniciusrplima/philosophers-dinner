import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Table {

    private List<Philosopher> philosophers = new ArrayList<>();

    private List<Semaphore> forks = new ArrayList<>();

    public void addPhilosopher(String name) {
        Philosopher philosopher = new Philosopher(name, philosophers.size(), this);
        philosophers.add(philosopher);
    }

    public Integer nextIndex(Integer id) {
        return (id + 1) % philosophers.size();
    }

    public Integer previousIndex(Integer id) {
        return (id + philosophers.size() - 1) % philosophers.size();
    }

    public Philosopher nextPhilosopher(Integer id) {
        id = nextIndex(id);
        return philosophers.get(id);
    }

    public Philosopher nextNextPhilosopher(Integer id) {
        id = nextIndex(nextIndex(id));
        return philosophers.get(id);
    }

    public Philosopher previousPhilosopher(Integer id) {
        id = previousIndex(id);
        return philosophers.get(id);
    }

    public Philosopher prevPrevPhilosopher(Integer id) {
        id = previousIndex(previousIndex(id));
        return philosophers.get(id);
    }

    public Semaphore getFork(Integer id) {
        return forks.get(id);
    }

    public Semaphore nextFork(Integer id) {
        id = nextIndex(id);
        return forks.get(id);
    }

    public Semaphore previousFork(Integer id) {
        id = previousIndex(id);
        return forks.get(id);
    }

    public void initialize() {
        for (int i = 0; i < philosophers.size(); i++) {
            forks.add(new Semaphore(1));
        }

        for (Philosopher philosopher : philosophers) {
            new Thread(philosopher).start();
        }
    }
    
}
