package data;

import static additional.Clock.*;
import static additional.Quick.TILE_SIZE;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private Enemy[] enemyTypes;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int enemiesPerWave, enemiesSpawned;
	private boolean waveCompleted;
	
	public Wave(Enemy[] enemyTypes, float spawnTime, int enemiesPerWave) {
		this.enemyTypes = enemyTypes;
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;
		
		spawn();
	}

	public void update() {
		boolean allEnemiesDead = true;
		if (enemiesSpawned < enemiesPerWave) {
			timeSinceLastSpawn += Delta();
			if (timeSinceLastSpawn > spawnTime) {
				spawn();
				timeSinceLastSpawn = 0;
			}
		}
		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				allEnemiesDead = false;
				e.update();
				e.draw();
			} else
				enemyList.remove(e);
		}
		if (allEnemiesDead)
			waveCompleted = true;
	}

	private void spawn() {               //randomly generates which enemy will spawn
		int enemyChosen = 0;
		Random random = new Random();
		enemyChosen = random.nextInt(enemyTypes.length);
		enemyList.add(new Enemy(enemyTypes[enemyChosen].getTexture(), enemyTypes[enemyChosen]
				.getStartTile(), enemyTypes[enemyChosen].getTileGrid(), TILE_SIZE, TILE_SIZE, enemyTypes[enemyChosen]
				.getSpeed(), enemyTypes[enemyChosen].getHealth()));
		enemiesSpawned++;
	}
	
	public boolean isCompleted() {
		return waveCompleted;
	}
	
	public CopyOnWriteArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
}
