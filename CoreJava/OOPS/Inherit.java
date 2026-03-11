class Aten{
    public void show(){
        System.out.println("A show");
    }
}
class Bten extends  Aten{
    @Override
    public void show() {
        System.out.println("B override show");
    }
}
class Computer{
    public void show(){
        System.out.println("Computer");
    }
}
class Laptop extends  Computer{
    @Override
    public void show(){
        System.out.println("Laptop");
    }
    public void show2(){
        System.out.println("Laptop2");

    }
}




//every class is inside a pacakage

//final variable = finalize a value just like (const) in js
//final class = finalize the inheritance (stopping further)
//final method = finalize  the method (no override)

// ACCESS MODIFIERS

//public = can be accessed from anywhere
//private = can be accessed inside class only
//default = can be accessed withing a pacakage



public class Inherit{
    public static void main(String[] args) {
        System.out.println("Hello");

        Bten obj = new Bten();
        obj.show();

        Computer lap = new Laptop();   //dynamic method dispatch
        lap.show();



    }
}