//Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
//Используйте wait/notify/notifyAll
public class MainThread {
    private final Object mon = new Object();
    private volatile char currentLetter = 'A'; //начинаем с А

    public static void main(String[] args) {
        MainThread mt = new MainThread();
        Thread tr1 = new Thread(() -> {
            mt.printA();
        });
        Thread tr2 = new Thread(() -> {
            mt.printB();
        });
        Thread tr3 = new Thread(() -> {
            mt.printC();
        });
        tr1.start();// запустили потоки ABC
        tr2.start();
        tr3.start();
    }
    public void printA() {
        synchronized (mon) { //синхронизируем по объекту mon
            try {
                for (int i = 0; i < 10; i++) {
                    while (currentLetter != 'A') { //пока currentLetter != нужному поток ждет
                        mon.wait();
                    }
                    System.out.print("A"); //выполнение действий потока
                    currentLetter = 'B'; //меняем
                    //mon.notify();
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (mon) {
            try {
                for (int j = 0; j < 10; j++) {
                    while (currentLetter != 'B') {
                        mon.wait();
                    }
                    System.out.print("B");
                    currentLetter = 'C';
                    //mon.notify();
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void printC() {
        synchronized (mon) {
            try {
                for (int t = 0; t < 10; t++) {
                    while (currentLetter != 'C') {
                        mon.wait();
                    }
                    System.out.print("C");
                    currentLetter = 'A';
                    //mon.notify();
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

