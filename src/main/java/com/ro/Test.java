package com.ro;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {

        PriorityQueue pq = new PriorityQueue(10);

        pq.enqueue(new Patient(3, "Patient 1", "9:00 AM"));
        pq.enqueue(new Patient(1, "Patient 2", "9:15 AM"));
        pq.enqueue(new Patient(2, "Patient 3", "9:30 AM"));
        pq.enqueue(new Patient(4, "Patient 4", "10:00 AM"));
        pq.enqueue(new Patient(1, "Patient 5", "10:30 AM"));

        System.out.println("Initial Queue");
        System.out.println(pq);

        pq.updatePriority("Patient 3", 1);
        System.out.println("\nAfter Updating Priority of Patient 3 to 1");
        System.out.println(pq);

        pq.updatePriority("Patient 4", 2);
        System.out.println("\nAfter Updating Priority of Patient 4 to 2");
        System.out.println(pq);

        pq.dequeue();
        System.out.println("\nAfter Removing the Highest Priority Patient");
        System.out.println(pq);

        System.out.println("\n" + pq.peek());

    }

}
