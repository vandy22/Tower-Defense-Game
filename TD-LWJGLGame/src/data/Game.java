package data;

import static helpers.Artist.loadTexture;

public class Game {
	
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	public static final int TILE_SIZE = 64;
	
	public Game(int[][] map) {
		grid = new TileGrid(map);
		waveManager = new WaveManager(new Enemy(loadTexture("ufo"), grid.getTile(10, 12), grid, 64, 64, 50), 0.5f, 5);
		player = new Player(grid, waveManager);
	}
	
	public void update() {
		grid.draw();
		waveManager.update();
		player.update();
	}

}
