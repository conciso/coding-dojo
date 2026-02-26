import { describe, it, expect } from 'vitest';
import {
  createInitialState,
  movePlayer,
  playerShoot,
  moveAliens,
  moveBullets,
  aliensShoot,
  GAME_WIDTH,
  GAME_HEIGHT,
} from './state';

describe('createInitialState', () => {
  it('should create state with player, aliens, shields, and defaults', () => {
    const state = createInitialState();
    expect(state.player.lives).toBe(3);
    expect(state.score).toBe(0);
    expect(state.gameOver).toBe(false);
    expect(state.won).toBe(false);
    expect(state.bullets).toHaveLength(0);
  });

  it('should create a grid of aliens (5 rows x 11 cols)', () => {
    const state = createInitialState();
    expect(state.aliens).toHaveLength(55);
    expect(state.aliens.every(a => a.alive)).toBe(true);
  });

  it('should create 4 shields', () => {
    const state = createInitialState();
    expect(state.shields).toHaveLength(4);
  });

  it('should start aliens moving right', () => {
    const state = createInitialState();
    expect(state.alienDirection).toBe(1);
  });
});

describe('movePlayer', () => {
  it('should move player left', () => {
    const state = createInitialState();
    const startX = state.player.x;
    const next = movePlayer(state, 'left', 1 / 60);
    expect(next.player.x).toBeLessThan(startX);
  });

  it('should move player right', () => {
    const state = createInitialState();
    const startX = state.player.x;
    const next = movePlayer(state, 'right', 1 / 60);
    expect(next.player.x).toBeGreaterThan(startX);
  });

  it('should not move player past left edge', () => {
    const state = createInitialState();
    state.player.x = 0;
    const next = movePlayer(state, 'left', 1 / 60);
    expect(next.player.x).toBe(0);
  });

  it('should not move player past right edge', () => {
    const state = createInitialState();
    state.player.x = GAME_WIDTH - state.player.width;
    const next = movePlayer(state, 'right', 1 / 60);
    expect(next.player.x).toBe(GAME_WIDTH - state.player.width);
  });

  it('should not move when direction is none', () => {
    const state = createInitialState();
    const startX = state.player.x;
    const next = movePlayer(state, 'none', 1 / 60);
    expect(next.player.x).toBe(startX);
  });
});

describe('playerShoot', () => {
  it('should create a bullet at player position', () => {
    const state = createInitialState();
    const next = playerShoot(state);
    expect(next.bullets).toHaveLength(1);
    expect(next.bullets[0].owner).toBe('player');
    expect(next.bullets[0].vy).toBeLessThan(0);
  });

  it('should not shoot if player bullet already exists', () => {
    const state = createInitialState();
    const once = playerShoot(state);
    const twice = playerShoot(once);
    const playerBullets = twice.bullets.filter(b => b.owner === 'player');
    expect(playerBullets).toHaveLength(1);
  });
});

describe('moveAliens', () => {
  it('should do nothing when all aliens are dead', () => {
    const state = createInitialState();
    for (const alien of state.aliens) {
      alien.alive = false;
    }
    const next = moveAliens(state, 1 / 60);
    expect(next).toBe(state);
  });

  it('should move aliens horizontally', () => {
    const state = createInitialState();
    const startX = state.aliens[0].x;
    const next = moveAliens(state, 1 / 60);
    expect(next.aliens[0].x).not.toBe(startX);
  });

  it('should reverse direction and step down at edge', () => {
    const state = createInitialState();
    // Push rightmost alien to the edge
    for (const alien of state.aliens) {
      alien.x = GAME_WIDTH - alien.width;
    }
    state.alienDirection = 1;
    const next = moveAliens(state, 1 / 60);
    expect(next.alienDirection).toBe(-1);
    expect(next.aliens[0].y).toBeGreaterThan(state.aliens[0].y);
  });

  it('should reverse direction at left edge', () => {
    const state = createInitialState();
    for (const alien of state.aliens) {
      alien.x = 0;
    }
    state.alienDirection = -1;
    const next = moveAliens(state, 1 / 60);
    expect(next.alienDirection).toBe(1);
    expect(next.aliens[0].y).toBeGreaterThan(state.aliens[0].y);
  });
});

describe('moveBullets', () => {
  it('should move bullets by vy * dt', () => {
    const state = createInitialState();
    const shot = playerShoot(state);
    const startY = shot.bullets[0].y;
    const next = moveBullets(shot, 1 / 60);
    expect(next.bullets[0].y).toBeLessThan(startY);
  });

  it('should remove bullets that leave the screen top', () => {
    const state = createInitialState();
    const shot = playerShoot(state);
    shot.bullets[0].y = -20;
    const next = moveBullets(shot, 1 / 60);
    expect(next.bullets).toHaveLength(0);
  });

  it('should remove bullets that leave the screen bottom', () => {
    const state = createInitialState();
    state.bullets = [{ x: 100, y: GAME_HEIGHT + 10, width: 3, height: 10, vy: 200, owner: 'alien' as const }];
    const next = moveBullets(state, 1 / 60);
    expect(next.bullets).toHaveLength(0);
  });
});

describe('aliensShoot', () => {
  it('should add an alien bullet', () => {
    const state = createInitialState();
    const next = aliensShoot(state);
    expect(next.bullets).toHaveLength(1);
    expect(next.bullets[0].owner).toBe('alien');
    expect(next.bullets[0].vy).toBeGreaterThan(0);
  });

  it('should not shoot if no aliens alive', () => {
    const state = createInitialState();
    for (const alien of state.aliens) {
      alien.alive = false;
    }
    const next = aliensShoot(state);
    expect(next.bullets).toHaveLength(0);
  });
});
