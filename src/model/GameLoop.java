package model;

public class GameLoop {
	public static final double BOUND_TIME = 0.0166;

	private Runnable operations;

	private boolean notExit = true;

	public GameLoop(Runnable operations) {
		this.operations = operations;
	}

	public void start() {
		long prevTime = System.nanoTime();

		while(notExit) {
			long curTime = System.nanoTime();
			double deltaTime = (curTime - prevTime) / 1e9;

			operations.run();

			double frameTime = (System.nanoTime() - curTime) / 1e9;

			if (frameTime < BOUND_TIME) {
				try {
					Thread.sleep((long) ((BOUND_TIME - frameTime) * 1e3));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void stop() {
		notExit = false;
	}

}
