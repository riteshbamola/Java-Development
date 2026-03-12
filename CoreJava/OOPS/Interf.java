
//class - class = extends
//interface - class = implements
//interface to interface = extends


interface Ainterface{

    int age=12;  // final and static;
    void show();  //by default public abstract
}

interface R extends Ainterface{
    void run();
}

class Q implements  R{
    public void show(){
        System.out.println("Show..");
    }
    public void run(){
        System.out.println("Run..");
    }
}


class Binter implements  Ainterface{
   public void show(){
       System.out.println("Show ..");
    }
}

public  class Interf{
    public static void main(String[] args) {
        System.out.println(Ainterface.age);
    }
}