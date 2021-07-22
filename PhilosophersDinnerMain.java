
public class PhilosophersDinnerMain {

    public static void main(String[] args) {

        Table table = new Table();
        table.addPhilosopher("Sócrates");
        table.addPhilosopher("Santo Agostinho");
        table.addPhilosopher("Santo Tomás de Aquino");
        table.addPhilosopher("Olavo de Carvalho");
        table.addPhilosopher("Mario Ferreira dos Santos");

        table.initialize();
    }

}