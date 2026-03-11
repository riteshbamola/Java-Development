import java.util.Arrays;

class Helper {

    int a=0;
    public int add(int n1,int n2){
        return n1+n2;
    }
}

public class Classes{
    public static void main(String[] args) {
        int num1=10;
        int num2=15;

        Helper h = new Helper();

        int result = h.add(num1,num2);
        System.out.println(result);
    }


}