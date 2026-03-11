public class Strr{

    public static void main(String[] args) {


        String s1= "Ritesh"; //store in string constant pool
        String s2= "Ritesh"; //new object not created just shared using constant pool

        String name ="Ayush";
        name = name + "Rawat";  // new object created

        //string are immutable    -- thread safe

        //ways to make mutable  without creating new objects

        StringBuffer str= new StringBuffer("RiteshBamola");  //store in heap  -- slow because thread safe
        str.append(" hello");
        System.out.println(str);

        StringBuilder str2 = new StringBuilder("Ritesh");  //store in heap -- fast not thread safe

    }
}