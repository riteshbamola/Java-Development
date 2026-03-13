import java.util.ArrayList;
import java.util.Iterator;

public class ArraysColl {
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

        list.set(0,100);   //set 100 at 0
        System.out.println(list.contains(54));


        /*
        list.remove(1);  //remove index 1    O(n)
        list.remove(Integer.valueOf(14));    //remove value 14
        System.out.println(list);

        list.clear();   //clear the list
        */

        for(Integer i:list){
            System.out.println(i);
        }

        Iterator<Integer> it = list.iterator();

        while(it.hasNext()){
            System.out.println(it.next());
        }

    }
}