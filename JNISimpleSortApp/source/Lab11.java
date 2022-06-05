import java.util.*;  
import java.util.stream.*;  

public class Lab11 {
    static {
        System.loadLibrary("native");
    }

    Double[] a;
    Double[] b;
    Boolean order;
    
    public static void main(String[] args) {
        Lab11 l = new Lab11();

        System.out.println("sort01");
        Double[] d = l.sort01(new Double[]{1.5, 4.5, 3.5}, true);
        System.out.println(d[0] + " " + d[1] + " " + d[2]);
        Double[] d1 = l.sort01(new Double[]{1.5, 4.5, 3.5}, false);
        System.out.println(d1[0] + " " + d1[1] + " " + d1[2]);

        System.out.println();
        System.out.println("sort02");
        l.order = true;
        Double[] d3 = l.sort02(new Double[]{1.5, 4.5, 3.5});
        System.out.println(d3[0] + " " + d3[1] + " " + d3[2]);
        l.order = false;
        Double[] d4 = l.sort02(new Double[]{1.5, 4.5, 3.5});
        System.out.println(d4[0] + " " + d4[1] + " " + d4[2]);

        System.out.println();
        System.out.println("sort03");
        l.sort03();

        System.out.println();
        System.out.println("Wynik");
        for (Double double1 : l.b) {
            System.out.println(double1);
        }
        Scanner sc = new Scanner(System.in);
        sc.next();
        sc.close();
    }

    public native Double[] sort01(Double[] a, Boolean order);

    public native Double[] sort02(Double[] a);

    public native void sort03();

    public void sort04(){

        List<Double> slist = Arrays.asList(a);
        List<Double> sortedList;

        if(order){
            sortedList = slist.stream().sorted().collect(Collectors.toList());
        }else{
            sortedList=slist.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }

        b = new Double[a.length];

        for (int i = 0; i < b.length; i++) {
            b[i] = (Double) sortedList.toArray()[i];
        }
    }
}