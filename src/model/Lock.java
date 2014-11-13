package model;

/**
 * Lock.java is copied from ifeve.com
 * @author erik
 *
 */
public class Lock {

	private boolean isLocked = false;
	private Thread lockingThread = null;

	public synchronized void lock() throws InterruptedException {
		while (isLocked) {
			this.wait();
		}

		isLocked = true;
		lockingThread = Thread.currentThread();
	}

	public synchronized void unlock() {
		if (Thread.currentThread() != this.lockingThread) {
			throw new IllegalMonitorStateException(
					"Calling thread has not locked this lock");
		}
		
		isLocked = false;
		lockingThread = null;
		this.notify();

	}
}
