package CoreJava.Collections;

import java.util.*;

public class Tasks {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(121);
        list.add(34);
        list.add(67);
        list.add(3);
        list.add(17);
        list.add(121);
        list.add(3);

        System.out.println(list);
//        Integer sum=0;

        //max without Collections
//        for(Integer it: list){
//            sum= Math.max(sum,it);
//        }

        //with collections
//        System.out.println(Collections.max(list));

//        Collections.reverse(list);   reverse list
//        Collections.sort(list);     sorting

        Collections.sort(list);
        int idx=0;
        for(int i=1;i<list.size();i++){
            if(list.get(i) != list.get(idx)){
                list.set(idx+1,list.get(i));
                idx++;
            }
        }


        Set<Integer> set = new HashSet<>();
        set.add(new Integer(34));
        set.add(new Integer(34));
        System.out.println(set);
        Thread th = new Thread();



        System.out.println(list);
    }
}
