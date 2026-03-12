class Demo{
    public void show(){
        System.out.println("demo");
    }
}
class Demo2 extends Demo{
    public void show1(){
        System.out.println("demo2");
    }
}
public class Casting{
    public static void main(String[] args) {


        Demo obj = (Demo) new Demo2();  //upcasting

        obj.show();

        Demo obj1 = new Demo();
        Demo2 obj2 = (Demo2) obj1;  //downcasting
        obj2.show1();

    }
}