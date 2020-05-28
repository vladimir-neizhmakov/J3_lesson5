import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static final int TUNNEL_CARS_COUNT = CARS_COUNT / 2;
    //Добавил флаг все на старте
    public static final CountDownLatch startAllCarsFlag = new CountDownLatch(CARS_COUNT);
    //Добавил флаг все на финише
    public static final CountDownLatch finishAllCarsFlag = new CountDownLatch(CARS_COUNT);
    //Добавил симафор прохождения туннеля
    public static final Semaphore tunnelCarsCount = new Semaphore(TUNNEL_CARS_COUNT);
    //Время общего старта
    static long startTime;
    //Список финалистов
    public static List<Car> chempionship = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
           startAllCarsFlag.await();
           startTime = System.nanoTime();
           System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            finishAllCarsFlag.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < cars.length; i++) {
            System.out.println(chempionship.get(i).getName()+" проехал все этапы гонки за: "+(chempionship.get(i).getStageTime()-startTime)+"нс");
        }

    }
}
