package com.ro;

public class PriorityQueue {

    private Patient[] heap;
    private int size;

    public PriorityQueue(int capacity) {
        heap = new Patient[capacity + 1];
        size = 0;
    }

    public void enqueue(Patient patient) {
        if (size >= heap.length - 1) {
            resize();
        }
        heap[++size] = patient;
        swim(size);
    }

    public Patient dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority Queue is Empty!!");
        }
        Patient max = heap[1];
        swap(1, size--);
        sink(1);
        heap[size + 1] = null;
        return max;
    }

    public void updatePriority(String name, int newPriority) {
        for (int i = 1; i <= size; i++) {
            if (heap[i].name.equals(name)) {
                int oldPriority = heap[i].priority;
                heap[i].priority = newPriority;
                if (newPriority < oldPriority) {
                    swim(i);
                } else {
                    sink(i);
                }
                break;
            }
        }
    }

    public Patient peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority Queue is Empty!!");
        }

        return heap[1];
    }

    private void sink(int patient) {
        while (2 * patient <= size) {
            int pivotPatient = 2 * patient;
            if (pivotPatient < size && greater(pivotPatient, pivotPatient + 1)) pivotPatient++;
            if (!greater(patient, pivotPatient)) break;
            swap(patient, pivotPatient);
            patient = pivotPatient;
        }
    }

    private void swim(int patient) {
        while (patient > 1 && greater(patient / 2, patient)) {
            swap(patient, patient / 2);
            patient = patient / 2;
        }
    }

    private boolean greater(int i, int j) {
        return heap[i].priority > heap[j].priority;
    }

    private void swap(int patientToSwap, int pivotPatient) {
        Patient temp = heap[patientToSwap];
        heap[patientToSwap] = heap[pivotPatient];
        heap[pivotPatient] = temp;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        Patient[] temp = new Patient[heap.length * 2];
        System.arraycopy(heap, 1, temp, 1, size);
        heap = temp;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Priority Queue[");
        for (int i = 1; i <= size; i++) {
            stringBuilder.append(heap[i]);
            if (i < size) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

class Patient {
    int priority;
    String name;
    String arrivalTime;

    public Patient(int priority, String name, String arrivalTime) {
        this.priority = priority;
        this.name = name;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return "{'" + name + "', '" + priority + "', '" + arrivalTime + "'}";
    }
}
