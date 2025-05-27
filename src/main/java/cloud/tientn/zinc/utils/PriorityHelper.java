package cloud.tientn.zinc.utils;

import cloud.tientn.zinc.model.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PriorityHelper {
    public static int comparePriority(String p1, String p2) {
        List<String> levels = Arrays.asList(OrderPriorityUtils.NORMAL.name(),OrderPriorityUtils.URGENT.name(), OrderPriorityUtils.TWOHOURS.name());
        return levels.indexOf(p2) - levels.indexOf(p1);
    }
    public static int toLevel(String p) {
        switch (p.toUpperCase()) {
            case "NORMAL": return 3;
            case "URGENT": return 2;
            case "TWOHOURS": return 1;
            default: return 0;
        }
    }

    public static void mergeSortOrders(List<Order> orders) {
        if (orders.size() <= 1) return;

        int mid = orders.size() / 2;
        List<Order> left = new ArrayList<>(orders.subList(0, mid));
        List<Order> right = new ArrayList<>(orders.subList(mid, orders.size()));

        mergeSortOrders(left);
        mergeSortOrders(right);

        merge(orders, left, right);
    }

    private static void merge(List<Order> result, List<Order> left, List<Order> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            Order o1 = left.get(i);
            Order o2 = right.get(j);

            int cmp = PriorityHelper.toLevel(o1.getPriority()) - PriorityHelper.toLevel(o2.getPriority());
            if (cmp < 0 || (cmp == 0 && o1.getCreatedAt().isBefore(o2.getCreatedAt()))) {
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
}
