import CoreJava.Threads.MultiThreadingRunnable;
import CoreJava.Threads.MultiThreadingThing;

public class Threading{


    public static void main(String[] args) {


        Thread t1 = new Thread();
        t1.setPriority(Thread.MAX_PRIORITY -1 );   //set Priority
        System.out.println(t1.getPriority());     //get priority



        //using thread class
        for(int i=0;i<5;i++) {
            MultiThreadingThing obj1 = new MultiThreadingThing(i);
            obj1.start();
        }

        //using runnable interface
        for(int i=0;i<5;i++) {
            MultiThreadingRunnable obj= new MultiThreadingRunnable(i);
            Thread t2 = new Thread(obj);
            t2.start();
        }


    }
}