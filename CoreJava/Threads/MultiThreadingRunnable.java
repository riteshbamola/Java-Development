package CoreJava.Threads;

public class MultiThreadingRunnable implements Runnable {
    final private int threadNumber;

    public MultiThreadingRunnable(int threadNumber){
        this.threadNumber= threadNumber;
    }


    @Override
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println(i + "from thread" + this.threadNumber);
        }
    }
}
