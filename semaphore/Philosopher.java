package semaphore;

import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {

    private static final long SLEEP_TIME = 1000;

    private Integer id;

    private String name;

    private PhilosopherState state = PhilosopherState.THINKING;

    private Table table;

    private static Semaphore stateMutex = new Semaphore(1);

    public Philosopher(String name, Integer id, Table table) {
        this.name = name;
        this.id = id;
        this.table = table;
    }

    @Override
    public void run() {

        while (true) {
            try {
                think();
                grabForks();
                eat();
                leaveForks();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void think() throws Exception {
        if (name.equals("Olavo de Carvalho")) {
            System.out.println(String.format("%d: Olavo is right...", id, name));
        }
        else {
            System.out.println(String.format("%d: %s is thinking...", id, name));
        }

        Thread.sleep(SLEEP_TIME);
    }

    private void grabForks() throws Exception {
        synchronized (stateMutex) {
            stateMutex.acquire(); // begin of critical region

            state = PhilosopherState.HUNGRY;

            if (table.nextPhilosopher(id).getState() != PhilosopherState.EATING &&
                table.previousPhilosopher(id).getState() != PhilosopherState.EATING) {
                
                state = PhilosopherState.EATING;

                // allow eating
                table.getFork(id).release();
            }

            stateMutex.release(); // end of critical region
        }

        // sleep until fork is free if is not eating
        table.getFork(id).acquire();
    }

    private void eat() throws Exception {
        System.out.println(
            String.format("%d: %s is eating...", id, name));

        Thread.sleep(SLEEP_TIME);
    }

    private void leaveForks() throws Exception {
        synchronized (stateMutex) {

            stateMutex.acquire();
            state = PhilosopherState.THINKING;

            // wake up right philosopher
            if (table.nextPhilosopher(id).getState() == PhilosopherState.HUNGRY &&
                table.nextNextPhilosopher(id).getState() != PhilosopherState.EATING) {
                
                table.nextPhilosopher(id).setState(PhilosopherState.EATING);
                table.nextFork(id).release();
            }

            // wake up left philosopher
            if (table.previousPhilosopher(id).getState() == PhilosopherState.HUNGRY &&
                table.prevPrevPhilosopher(id).getState() != PhilosopherState.EATING) {

                table.previousPhilosopher(id).setState(PhilosopherState.EATING);
                table.previousFork(id).release();
            }

            stateMutex.release();
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public PhilosopherState getState() {
        return state;
    }

    public void setState(PhilosopherState state) {
        this.state = state;
    }

}
