package model;

import java.util.ArrayList;
import java.util.List;

/**
 * FairLock.java is coped from ifeve.com.
 * @author erik
 *
 */
public class FairLock {
	private boolean isLocked = false;
	private Thread lockingThread = null;
	private List<QueueObject> waitingThreads = new ArrayList<QueueObject>();

	public void lock() throws InterruptedException {
		QueueObject queueObject = new QueueObject();
		boolean isLockedForThisThread = true;

		synchronized (this) {
			waitingThreads.add(queueObject);
		}

		while (isLockedForThisThread) {
			synchronized (this) {
				isLockedForThisThread = isLocked
						|| queueObject != waitingThreads.get(0);

				if (!isLockedForThisThread) {
					isLocked = true;
					waitingThreads.remove(queueObject);
					lockingThread = Thread.currentThread();
					return;
				}
			}
		}

		try {
			queueObject.doWait();
		} catch (InterruptedException e) {
			synchronized (this) {
				waitingThreads.remove(queueObject);
				throw e;
			}
		}
	}

	public synchronized void unlock() {
		if (this.lockingThread != Thread.currentThread()) {
			throw new IllegalMonitorStateException(
					"Calling thread has not locked this thread.");
		}
		
		isLocked = false;
		lockingThread = null;
		if (waitingThreads.size() > 0) {
			waitingThreads.get(0).doNotify();
		}
	}
}

class QueueObject {
	private boolean isNotified = false;

	public synchronized void doWait() throws InterruptedException {
		while (!isNotified) {
			this.wait();
		}

		this.isNotified = true;
	}

	public synchronized void doNotify() {
		this.isNotified = true;
		this.notify();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this == obj;
	}

}