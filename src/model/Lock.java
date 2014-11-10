package model;

public class Lock {

	private boolean isLocked = false;
	private Thread lockedThread = null;

	public synchronized void lock() throws InterruptedException {
		while (isLocked) {
			this.wait();
		}

		isLocked = true;
		lockedThread = Thread.currentThread();
	}

	public synchronized void unlock() {
		if (Thread.currentThread() != this.lockedThread) {
			throw new IllegalMonitorStateException(
					"Calling thread has not locked this lock");
		}
		
		isLocked = false;
		lockedThread = null;
		this.notify();

	}
}
