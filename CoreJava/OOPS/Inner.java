class Ainner{

    int age;

    public void show(){
        System.out.println("Show..");
    }

    //inner class
    //only inner class can be static

    //We do not need an object of the outer class to create it if inner is static.


    class Binner{
        public void show(){
            System.out.println("B Show..");
        }
    }
 }

public class Inner{
    public static void main(String[] args) {
        Ainner obj = new Ainner();

        obj.show();

        Ainner.Binner obj1 = obj.new Binner();   //inner class object need a obj of upper class

//        Ainner.Binner = new Ainner.Binner();   in case of static


        obj1.show();
        //Anonymous inner class

        Ainner obj2 = new Ainner(){
            public void show(){
                System.out.println("Anonymous");
            }
        };
    }
}