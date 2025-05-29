package cloud.tientn.zinc.utils;

import cloud.tientn.zinc.model.Order;
import org.apache.qpid.proton.codec.security.SaslOutcomeType;

import java.time.LocalDateTime;
import java.util.*;

public class Tests {
//    public static void main(String[] args) {
//        Date orderDate= new Date();
//        Calendar cal = Calendar.getInstance();
////        Calendar cal = Calendar.getInstance();
////        cal.set(2025, Calendar.MAY, 17); // May is 4 (0-based) or Calendar.MAY
////        Date orderDate = cal.getTime();
//        //System.out.println("Order Date: " + orderDate);
//        Date processing;
//        Set<String> workingDays= new HashSet<>();
//        workingDays.add("Monday");
//        workingDays.add("Tuesday");
//        workingDays.add("Wednesday");
//        workingDays.add("Thursday");
//        workingDays.add("Friday");
//
//        cal.setTime(orderDate);
//        String currentDay = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
//
//        if(!workingDays.contains(currentDay)){
//            do{
//                cal.add(Calendar.DAY_OF_MONTH, 1);
//            }while (!workingDays.contains(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US)));
//        }
//        cal.add(Calendar.DAY_OF_WEEK, 2);
//        String processDay=cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
//        if(!workingDays.contains(processDay)){
//            do{
//                cal.add(Calendar.DAY_OF_MONTH, 1);
//            }while (!workingDays.contains(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US)));
//        }
//        processing= cal.getTime();
//        System.out.println(processing);
//
//
//
//        LocalDateTime t2= LocalDateTime.now().plusDays(2);
//        System.out.println(t2);
//
//        ProcessOr();
//    }

    public static class Or {
        int id;
        String p;
        LocalDateTime t;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }

        public LocalDateTime getT() {
            return t;
        }

        public void setT(LocalDateTime t) {
            this.t = t;
        }

        public Or(int id, String p, LocalDateTime t) {{
            this.id=id;
            this.p=p;
            this.t=t;
        }}
        public Or(){}

    }
    private static final Map<String, Integer> PRIORITY_LEVEL = Map.of(
            "N", 1,
            "U", 2,
            "TH", 3
    );
    public static int comparePriority(String p1, String p2) {
        return PRIORITY_LEVEL.get(p1)-PRIORITY_LEVEL.get(p2);
    }

    public static int toLevel(String priority) {
        return PRIORITY_LEVEL.get(priority);
    }
    public static void bubbleSortOrders(List<Or> orders) {
        for (int i = 0; i < orders.size() - 1; i++) {
            for (int j = 0; j <  orders.size() - i - 1; j++) {
                Or o1 = orders.get(j);
                Or o2 = orders.get(j + 1);

                int cmp = PriorityHelper.toLevel(o1.getP()) - PriorityHelper.toLevel(o2.getP());

                //System.out.println("-->>>"+PriorityHelper.toLevel(o1.getP()));
                //int cmp2 = PriorityHelper.comparePriority("TH", "N");
                System.out.println("-->>" + cmp);
                // System.out.println("-->"+cmp2);
                if (cmp > 0|| (cmp == 0 && o1.getT().isAfter(o2.getT()))) {
                    // Swap o1 and o2
                    orders.set(j, o2);
                    orders.set(j + 1, o1);
                }
            }

        }
    }
    public static void mergeSortOrders(List<Or> orders) {
        if (orders.size() <= 1) return;

        int mid = orders.size() / 2;
        List<Or> left = new ArrayList<>(orders.subList(0, mid));
        List<Or> right = new ArrayList<>(orders.subList(mid, orders.size()));

        mergeSortOrders(left);
        mergeSortOrders(right);

        merge(orders, left, right);
    }

    private static void merge(List<Or> result, List<Or> left, List<Or> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            Or o1 = left.get(i);
            Or o2 = right.get(j);

            int cmp = PriorityHelper.toLevel(o1.getP()) - PriorityHelper.toLevel(o2.getP());
            if (cmp < 0 || (cmp == 0 && o1.getT().isBefore(o2.getT()))) {
                result.set(k++, o1);
                i++;
            } else {
                result.set(k++, o2);
                j++;
            }
        }

        while (i < left.size()) {
            result.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            result.set(k++, right.get(j++));
        }
    }
    public static void ProcessOr(){
        Or o1= new Or(1,"TWOHOURS",LocalDateTime.now());
        Or o2= new Or(2,"NORMAL",LocalDateTime.now());
        Or o3= new Or(3,"TWOHOURS",LocalDateTime.now());
        Or o4= new Or(4,"URGENT",LocalDateTime.now());
        Or o5= new Or(5,"NORMAL",LocalDateTime.now());
        Or o6= new Or(6,"URGENT",LocalDateTime.now());
        Or o7= new Or(7,"TWOHOURS",LocalDateTime.now());
//        Comparator<Or> orderComparator = (a1, a2) -> {
//            //int cmp = PriorityHelper.comparePriority(a1.p, a2.p);
//            if (cmp == 0) {
//                return a1.t.compareTo(a2.t);
//            }
//            return cmp;
//        };
//        PriorityQueue<Or> orderQueue = new PriorityQueue<>(orderComparator);
//        orderQueue.add(o1);
//        orderQueue.add(o2);
//        orderQueue.add(o3);
//        orderQueue.add(o4);
//        orderQueue.add(o5);
//        while (!orderQueue.isEmpty()) {
//            Or or = orderQueue.poll();
//            System.out.println(or.id);
//        }

        List<Or> orderList = new ArrayList<>(List.of(o1, o2, o3, o4, o5,o6,o7));
        System.out.println("Original order list: " + orderList.size());
        //bubbleSortOrders(orderList);
        mergeSortOrders(orderList);
        for (Or o : orderList) {
            System.out.println(o.id);  // Kết quả: 1, 3, 6,4, 5, 2
        }
    }
}
