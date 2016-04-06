package model;

public class GameState {

	private boolean gameOver;
	private Score score;

	public GameState() {
		this.gameOver = false;
		this.score = new Score();
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public Score getScore() {
		return score;
	}
}
