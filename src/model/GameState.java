package model;

public class GameState {

	private boolean gameOver;

	public GameState() {
		this.gameOver = false;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

}
