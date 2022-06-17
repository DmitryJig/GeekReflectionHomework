import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TestStarter {

    private boolean isBeforeSuite;
    private boolean isAfterSuite;

    public void start(Class testClass) {
        // в этой мапе будем сортировать методы
        Map<Integer, List<Method>> sortMethodsMap = new TreeMap<>();

        Method[] methods = testClass.getMethods();

        // складываем все методы в сортирующую мапу, методы тест по индексам приоритета от 1 до 10,
        // методы с аннотациями BeforeSuite и AfterSuite по индексам 0 и 11 соответственно
        for (Method method : methods) {

            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (isBeforeSuite) {
                    throw new RuntimeException("Лишний метод с аннотацией BeforeSuite");
                }
                List<Method> methodsList = new ArrayList<>();
                methodsList.add(method);
                sortMethodsMap.put(0, methodsList);
                isBeforeSuite = true;
                continue;
            }

            if (method.isAnnotationPresent(Test.class)) {
                Test testAnnotation = method.getAnnotation(Test.class);
                int priorityValue = testAnnotation.value();

                if (priorityValue < 1 || priorityValue > 10) { // если кто то пошутил и ввел невалидное значение ставим дефолтное
                    priorityValue = 5;
                }

                if (sortMethodsMap.containsKey(priorityValue)) {
                    sortMethodsMap.get(priorityValue).add(method);
                } else {
                    List<Method> methodsList = new ArrayList<>();
                    methodsList.add(method);
                    sortMethodsMap.put(priorityValue, methodsList);
                }
                continue;
            }

            if (method.isAnnotationPresent(AfterSuite.class)) {
                if (isAfterSuite) {
                    throw new RuntimeException("Лишний метод с аннотацией AfterSuite");
                }
                List<Method> methodsList = new ArrayList<>();
                methodsList.add(method);
                sortMethodsMap.put(11, methodsList);
                isAfterSuite = true;
            }
        }

        sortMethodsMap.forEach((x, y) -> {
            y.forEach(m -> {
                try {
                    m.invoke(testClass.getDeclaredConstructor(Car.class).newInstance(new Car("Toyota", 2022, 200)));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                         InstantiationException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    public static void main(String[] args) {

        TestStarter testStarter = new TestStarter();
        testStarter.start(TestCar.class);
    }
}
