package CoreJava.Threads;

public class MultiThreadingThing extends Thread{
    final private int threadNumber;

    public MultiThreadingThing(int threadNumber){
        this.threadNumber= threadNumber;
    }
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println(i + "from thread" + this.threadNumber);
        }
    }
}
