import java.util.concurrent.CountDownLatch;

public class Car implements Runnable {
    private long stageTime = 0;
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public long getStageTime(){
        return stageTime;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.startAllCarsFlag.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MainClass.startAllCarsFlag.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        stageTime = System.nanoTime();
        MainClass.chempionship.add(this);

        MainClass.finishAllCarsFlag.countDown();
        try {
            MainClass.finishAllCarsFlag.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}