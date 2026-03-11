class Student{
    int rollno;
    String name;

    public Student(int rollno, String name){
        this.name=name;
        this.rollno=rollno;
    }

    public void getDetails(){
        System.out.println("Name:"+ this.name);
        System.out.println("Rollno:"+ this.rollno);
    }
}

public class Arrays {
    public static void main(String[] args){

        int arr[]= new int[5];  //1d array

        int arr2[][]= new int[2][4];  //2d array  every row have same no of cols

        int arr3[][]= new int[2][];  // jagged  every row can have different no of cols

        arr3[0]= new int[2];  //1st row 2 cols
        arr3[1]= new int[4];  //2nd row 4 cols

        Student s1= new Student(12, "Ritesh Bamola");
        Student s2= new Student(13,"Ayush Rawat");
        Student s3= new Student(14,"Piyush Rawat");
        Student s4= new Student(15,"Ayush Negi");


        Student list[]= new Student[4];   //array of objects
        list[0]=s1;
        list[1]=s2;
        list[2]=s3;
        list[3]=s4;

        System.out.println("Details");

        for(Student element:list){
            element.getDetails();
        }
    }
}