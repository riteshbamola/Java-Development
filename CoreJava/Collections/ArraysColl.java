import java.util.ArrayList;

public class Arrays{
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(54);

        list.add(1,56);

        System.out.println(list);

        ArrayList<Integer>temp = new ArrayList<>();
        temp.add(14);

        list.addAll(temp);
        System.out.println(list);
    }
}