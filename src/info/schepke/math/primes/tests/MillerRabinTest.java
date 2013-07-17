/**
 * 
 */
package info.schepke.math.primes.tests;

import info.schepke.math.MyBigInteger;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ms
 *
 */
public class MillerRabinTest {

	static final Random GENERATOR = new Random();
	static final public int DEFAULT_ACCURACY = 1000;
	static final public int DEFAULT_WORKERS = 10;

	final private MyBigInteger n;
	final private int k;
	private BigInteger d;
	private BigInteger s;

	private volatile int threadsCompleted;
	private volatile boolean running = true;
	private volatile boolean composite = false;

	//	LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
	private ConcurrentLinkedQueue<Runnable> taskQueue = new ConcurrentLinkedQueue<Runnable>();
	private WorkerThread[] workers;

	public MillerRabinTest(MyBigInteger n) {
		this(n, DEFAULT_ACCURACY, DEFAULT_WORKERS);
	}

	public MillerRabinTest(MyBigInteger n, int k, int workerCount) {
		this.n = n;
		this.k = k;

		if (n.equals(MyBigInteger.TWO) 
				|| n.equals(MyBigInteger.THREE)
				|| n.equals(MyBigInteger.FIVE) 
				|| n.equals(MyBigInteger.SEVEN)) {
			running = false;
			composite = false;
		}

		else if (n.compareTo(MyBigInteger.ONE) <= 0
				|| n.divisibleBy(MyBigInteger.TWO)
				|| n.divisibleBy(MyBigInteger.THREE)) {
			running = false;
			composite = true;
		}

		d = n.subtract(MyBigInteger.ONE);
		s = MyBigInteger.ZERO;
		BigInteger[] divideAndRemainder = d.divideAndRemainder(MyBigInteger.TWO);

		while (divideAndRemainder[1].equals(MyBigInteger.ZERO)) {
			d = divideAndRemainder[0];
			s = s.add(MyBigInteger.ONE);
			divideAndRemainder = d.divideAndRemainder(MyBigInteger.TWO);
		}

		workers = new WorkerThread[workerCount];
	}

	public boolean isPrime() {
		if (! running) {
			return !composite;
		}

		for (int i = 0; i < k; i++) {
			taskQueue.add(new MillerRabinStep());
		}

		threadsCompleted = 0;
		running = true;

		for (int i = 0; running && i < workers.length; i++) {
			workers[i] = new WorkerThread();
			workers[i].start();
		}

		while (running) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) { }
		}

		return !composite;
	}
	
	private class WorkerThread extends Thread {
		public void run() {
			try{
				while (running) {
					Runnable task = taskQueue.poll(); // Get a task from the queue.
					if (task == null) {
						break; // (because the queue is empty)
					}
					task.run();  // Execute the task;
				}
			}
			finally {
				threadFinished();
			}
		}
	}

	synchronized private void threadFinished() {
		threadsCompleted++;
		if (threadsCompleted == workers.length) { // all threads have finished
			running = false; // Make sure running is false after the thread ends.
			workers = null;
		}
	}

	private class MillerRabinStep implements Runnable {

		private final BigInteger a;
		private BigInteger x;

		MillerRabinStep () {
			a = (new BigInteger(n.bitCount() - 1, GENERATOR)).add(MyBigInteger.TWO);
			x = a.modPow(d, n);
		}

		@Override
		public void run() {
			if (x.equals(MyBigInteger.ONE) || x.equals(n.subtract(MyBigInteger.ONE))) {
				return;
			}

			for (BigInteger r = MyBigInteger.ONE; r.compareTo(s) < 0; r = r.add(MyBigInteger.ONE)) {
				x = x.multiply(x).mod(n);

				if (x.equals(MyBigInteger.ONE)) {
					composite = true;
					running = false;
					return;
				}

				if (x.equals(n.subtract(MyBigInteger.ONE))) {
					return;
				}
			}

			composite = true;
			running = false;
		}
	}


}
