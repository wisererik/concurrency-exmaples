package main;

import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		for (int i=1; i<=10; i++){
			   Calculator calculator=new Calculator(i); Thread thread=new Thread(calculator);
			   thread.start();
			   TimeUnit.SECONDS.sleep(1);
			}
	}

}
