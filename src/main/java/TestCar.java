public class TestCar {

    private Car car;

    public TestCar(Car car) {
        this.car = car;
    }

    @BeforeSuite
    public void testStartEngineCar() {
        car.startEngine();
    }

    @AfterSuite
    public void testStopEngineCar(){
        car.stopEngine();
    }

    @Test
    public void testCarSignal(){
        car.signal();
    }

    @Test(3)
    public void testCarDrive(){
        car.drive();
    }

    @Test(6)
    public void testCarStay(){
        car.stay();
    }

}
