public class Car {
    private final String name;
    private final int year;
    private final int speed;

    public Car(String name, int year, int speed) {
        this.name = name;
        this.year = year;
        this.speed = speed;
    }

    public void startEngine() {
        System.out.println("Двигатель завелся");
    }

    public void drive() {
        System.out.println("Автомобиль едет");
    }

    public void signal() {
        System.out.println("Bi-bi !!!");
    }

    public void stay() {
        System.out.println("Автомобиль остановился");
    }

    public void stopEngine() {
        System.out.println("Урааа приехали!!! мотор выключен");
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", speed=" + speed +
                '}';
    }
}
