import java.io.*;
import java.util.Scanner;

public class Inpu{
    public static void main(String[] args) throws Exception {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader bf = new BufferedReader(in);

        System.out.println("Enter a number");
        int num = Integer.parseInt(bf.readLine());
        System.out.println(num);


        System.out.println("Enter a number");
        Scanner sc = new Scanner(System.in);
        int num2= sc.nextInt();
        System.out.println(num2);
    }
}