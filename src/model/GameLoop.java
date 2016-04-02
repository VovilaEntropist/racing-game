package model;

import utils.Consumer;

public class GameLoop extends Thread {
	public static final double BOUND_TIME = 0.0166;

	private Consumer<Double> operations;

	public GameLoop(Consumer<Double> operations) {
		this.operations = operations;
	}

	public GameLoop(Consumer<Double> operations, String name) {
		super(name);
		this.operations = operations;
	}

	@Override
	public void run() {
		long prevTime = System.nanoTime();

		while(true) {
			long curTime = System.nanoTime();
			double deltaTime = (curTime - prevTime) / 1e9;

			operations.run(deltaTime);

			double frameTime = (System.nanoTime() - curTime) / 1e9;

			if (frameTime < BOUND_TIME) {
				try {
					Thread.sleep((long) ((BOUND_TIME - frameTime) * 1e3));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			prevTime = curTime;
		}

	}


}
