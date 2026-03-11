class Mobile{
    static String category;   //shared for each object
    String name;
    int price;

    static {    // only called once in its lifecycle
        category= "Phone";
    }

    public Mobile(String name, int price){   //Constructor
        this.name=name;
        this.price=price;
    }

    public  void getDetails(){
        System.out.println(this.name + this.price + category);
    }

    //cannot use nonstatic variables in a static method
    //can do it using obj reference shown below

    public static void getDetails1(Mobile obj){
        System.out.println(obj.name + obj.price + category);
    }
}

// every class in java extends Object Class
//super   call the constructor of super class

class A{
    public A(){

        super();  // by default it is there no matter you mention or not
        System.out.println("In A");
    }

    public A(int n){
        this(); // call current object constructor;
        System.out.println("In A int");
    }
}

class B extends A{
    public B(){
        super();
        System.out.println("In B");
    }

    public  B(int n){
        super(n);  //will call parametirzed constructor of superclass
        System.out.println("in B int");
    }
}


public class Important {
    public static void main(String[] args) {
        Mobile m1= new Mobile("iPhone",500);
        Mobile m2= new Mobile("samsung",400);

        m1.getDetails();
        m2.getDetails();

        Mobile.getDetails1(m1);
        Mobile.getDetails1(m2);


        new B();  // anonymous object
        B obj1= new B(5); // refrence object

    }
}