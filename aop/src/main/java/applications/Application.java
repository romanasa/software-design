package applications;

public class Application {
    public void runFunction1() throws InterruptedException {
        runFunction2();
        runFunction3();
        Thread.sleep(100);
    }

    public void runFunction2() throws InterruptedException {
        runFunction3();
        Thread.sleep(200);
    }

    public void runFunction3() throws InterruptedException {
        Thread.sleep(300);
    }


}
