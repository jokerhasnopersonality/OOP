package io.github.jokerhasnopersonality;

import java.util.Arrays;

public class PrimeNumberCheck {
    private boolean check;
    public boolean SequientalCheck(int[] numbers) throws NullPointerException {
        if (numbers == null) {
            throw new NullPointerException();
        }
        check = false;
        for(int n : numbers) {
            if (!IsPrime(n)) {
                check = true;
            }
        }
        return check;
    }

    public boolean ParallelCheck(int[] numbers, int streams)
            throws NullPointerException, IllegalStateException {
        if (numbers == null) {
            throw new NullPointerException();
        }
        if (streams < 1 || streams > 6) {
            throw new IllegalStateException();
        }

        int divide = numbers.length / streams;
        Thread[] threads = new Thread[streams];
        check = false;
        for (int i = 0; i < streams; i++) {
            PrimeCheck primeCheck = new PrimeCheck(Arrays.copyOfRange(numbers, i * divide, (i + 1) * divide));
            threads[i] = new Thread(primeCheck);
            threads[i].start();
        }
        for (Thread thread : threads) {
            if (thread.isAlive()) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return check;
    }

    private boolean IsPrime(int N) {
        int sqrt = (int) Math.sqrt(N);
        for (int i = 2; i <= sqrt; i++) {
            if (N % i == 0) {
                return false;
            }
        }
        return true;
    }

    private class PrimeCheck implements Runnable {
        private final int[] numbers;

        PrimeCheck(int[] numbers) {
            this.numbers = numbers;
        }

        @Override
        public void run() {
            for (int n : numbers) {
                if (!IsPrime(n)) {
                    check = true;
                    return;
                }
            }
        }
    }
}