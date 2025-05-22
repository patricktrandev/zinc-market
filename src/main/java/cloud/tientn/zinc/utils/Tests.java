package cloud.tientn.zinc.utils;

import java.util.*;

public class Tests {
    public static void main(String[] args) {
        Date orderDate= new Date();
        Calendar cal = Calendar.getInstance();
//        Calendar cal = Calendar.getInstance();
//        cal.set(2025, Calendar.MAY, 17); // May is 4 (0-based) or Calendar.MAY
//        Date orderDate = cal.getTime();
        //System.out.println("Order Date: " + orderDate);
        Date processing;
        Set<String> workingDays= new HashSet<>();
        workingDays.add("Monday");
        workingDays.add("Tuesday");
        workingDays.add("Wednesday");
        workingDays.add("Thursday");
        workingDays.add("Friday");

        cal.setTime(orderDate);
        String currentDay = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);

        if(!workingDays.contains(currentDay)){
            do{
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }while (!workingDays.contains(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US)));
        }
        cal.add(Calendar.DAY_OF_WEEK, 2);
        String processDay=cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
        if(!workingDays.contains(processDay)){
            do{
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }while (!workingDays.contains(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US)));
        }
        processing= cal.getTime();
        System.out.println(processing);

    }
}
