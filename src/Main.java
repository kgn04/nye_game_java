public class Main {
    public static void main(String[] args) {
        Data.generateData();
        GameManager gm = new GameManager();
        gm.run();
        //System.out.println(Data.tasks.size());
    }
}