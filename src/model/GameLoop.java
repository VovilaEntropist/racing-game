package model;

import utils.Consumer;

public class GameLoop {
	public static final double BOUND_TIME = 0.0166;

	private Consumer<Double> operations;

	private boolean exit = false;

	public GameLoop(Consumer<Double> operations) {
		this.operations = operations;
	}

	public void start() {
		long prevTime = System.nanoTime();

		while(!exit) {
			long curTime = System.nanoTime();
			double deltaTime = (curTime - prevTime) / 1e9;

			operations.accept(deltaTime);

			double frameTime = (System.nanoTime() - curTime) / 1e9;

			if (frameTime < BOUND_TIME) {
				try {
					Thread.sleep((long) ((BOUND_TIME - frameTime) * 1e3));
				} catch (InterruptedException e) {
					//e.printStackTrace();
				}
			}
			prevTime = curTime;
		}

	}

	public void stop() {
		exit = true;
	}


}
