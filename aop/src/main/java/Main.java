import applications.Application;
import profiler.Profiler;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Application testApplication = new Application();

        testApplication.runFunction1();
        testApplication.runFunction2();
        testApplication.runFunction3();

        Profiler.printStatistic();
    }
}