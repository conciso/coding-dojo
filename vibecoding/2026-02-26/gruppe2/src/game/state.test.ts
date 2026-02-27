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
    expect(state.elapsed).toBe(0);
  });

  it('should place player centered horizontally', () => {
    const state = createInitialState();
    expect(state.player.x).toBe(GAME_WIDTH / 2 - 25);
    expect(state.player.y).toBe(550);
  });

  it('should create a grid of aliens (5 rows x 11 cols)', () => {
    const state = createInitialState();
    expect(state.aliens).toHaveLength(55);
    expect(state.aliens.every(a => a.alive)).toBe(true);
  });

  it('should position aliens in a spaced grid', () => {
    const state = createInitialState();
    // First alien: row 0, col 0
    expect(state.aliens[0].x).toBe(80);
    expect(state.aliens[0].y).toBe(60);
    // Second alien: row 0, col 1
    expect(state.aliens[1].x).toBe(80 + 55);
    expect(state.aliens[1].y).toBe(60);
    // Row 1, col 0 (index 11)
    expect(state.aliens[11].x).toBe(80);
    expect(state.aliens[11].y).toBe(60 + 45);
  });

  it('should assign correct alien types and points per row', () => {
    const state = createInitialState();
    // Row 0: top, 30pts
    expect(state.aliens[0].type).toBe('top');
    expect(state.aliens[0].points).toBe(30);
    // Row 1: middle, 20pts
    expect(state.aliens[11].type).toBe('middle');
    expect(state.aliens[11].points).toBe(20);
    // Row 3: bottom, 10pts
    expect(state.aliens[33].type).toBe('bottom');
    expect(state.aliens[33].points).toBe(10);
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
  it('should move player left by exactly PLAYER_SPEED * dt', () => {
    const state = createInitialState();
    const startX = state.player.x;
    const dt = 1 / 60;
    const next = movePlayer(state, 'left', dt);
    expect(next.player.x).toBeCloseTo(startX - 300 * dt, 5);
  });

  it('should move player right by exactly PLAYER_SPEED * dt', () => {
    const state = createInitialState();
    const startX = state.player.x;
    const dt = 1 / 60;
    const next = movePlayer(state, 'right', dt);
    expect(next.player.x).toBeCloseTo(startX + 300 * dt, 5);
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
  it('should create a bullet centered on player', () => {
    const state = createInitialState();
    const next = playerShoot(state);
    expect(next.bullets).toHaveLength(1);
    const b = next.bullets[0];
    expect(b.owner).toBe('player');
    expect(b.vy).toBeLessThan(0);
    // Bullet should be centered on player
    expect(b.x).toBeCloseTo(state.player.x + state.player.width / 2 - 1.5, 5);
    expect(b.y).toBe(state.player.y);
  });

  it('should not shoot if player bullet already exists', () => {
    const state = createInitialState();
    const once = playerShoot(state);
    const twice = playerShoot(once);
    const playerBullets = twice.bullets.filter(b => b.owner === 'player');
    expect(playerBullets).toHaveLength(1);
  });

  it('should allow shooting when only alien bullets exist', () => {
    const state = createInitialState();
    state.bullets = [{ x: 100, y: 100, width: 3, height: 10, vy: 200, owner: 'alien' as const }];
    const next = playerShoot(state);
    expect(next.bullets.filter(b => b.owner === 'player')).toHaveLength(1);
    expect(next.bullets).toHaveLength(2);
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

  it('should move aliens to the right when direction is 1', () => {
    const state = createInitialState();
    state.alienDirection = 1;
    const startX = state.aliens[0].x;
    const next = moveAliens(state, 1 / 60);
    expect(next.aliens[0].x).toBeGreaterThan(startX);
  });

  it('should move aliens to the left when direction is -1', () => {
    const state = createInitialState();
    state.alienDirection = -1;
    // Move aliens away from left edge so they don't trigger reversal
    for (const alien of state.aliens) {
      alien.x += 200;
    }
    const startX = state.aliens[0].x;
    const next = moveAliens(state, 1 / 60);
    expect(next.aliens[0].x).toBeLessThan(startX);
  });

  it('should move at exact base speed with all aliens alive and elapsed=0', () => {
    const state = createInitialState();
    state.alienDirection = 1;
    const startX = state.aliens[0].x;
    const dt = 1 / 60;
    // speed = 20 + 0*4 + 0*2 = 20
    const next = moveAliens(state, dt);
    expect(next.aliens[0].x).toBeCloseTo(startX + 20 * dt, 5);
  });

  it('should increase speed by kills*4', () => {
    const state = createInitialState();
    state.alienDirection = 1;
    // Kill 45 aliens → 10 alive → kills = 45
    for (let i = 10; i < 55; i++) {
      state.aliens[i].alive = false;
    }
    const startX = state.aliens[0].x;
    const dt = 1 / 60;
    // speed = 20 + 45*4 + 0*2 = 200
    const next = moveAliens(state, dt);
    expect(next.aliens[0].x).toBeCloseTo(startX + 200 * dt, 5);
  });

  it('should increase speed by elapsed*2', () => {
    const state = createInitialState();
    state.alienDirection = 1;
    state.elapsed = 10;
    const startX = state.aliens[0].x;
    const dt = 1 / 60;
    // speed = 20 + 0*4 + 10*2 = 40
    const next = moveAliens(state, dt);
    expect(next.aliens[0].x).toBeCloseTo(startX + 40 * dt, 5);
  });

  it('should reverse when only one alien reaches right edge (some vs every)', () => {
    const state = createInitialState();
    // Only first alien at edge, rest far left
    state.aliens[0].x = GAME_WIDTH - state.aliens[0].width;
    for (let i = 1; i < state.aliens.length; i++) {
      state.aliens[i].x = 100;
    }
    state.alienDirection = 1;
    const next = moveAliens(state, 1 / 60);
    expect(next.alienDirection).toBe(-1);
  });

  it('should reverse direction and step down at right edge', () => {
    const state = createInitialState();
    for (const alien of state.aliens) {
      alien.x = GAME_WIDTH - alien.width;
    }
    state.alienDirection = 1;
    const startY = state.aliens[0].y;
    const next = moveAliens(state, 1 / 60);
    expect(next.alienDirection).toBe(-1);
    expect(next.aliens[0].y).toBe(startY + 20);
  });

  it('should reverse when only one alien reaches left edge (some vs every)', () => {
    const state = createInitialState();
    state.aliens[0].x = 0;
    for (let i = 1; i < state.aliens.length; i++) {
      state.aliens[i].x = 400;
    }
    state.alienDirection = -1;
    const next = moveAliens(state, 1 / 60);
    expect(next.alienDirection).toBe(1);
  });

  it('should reverse direction and step down at left edge', () => {
    const state = createInitialState();
    for (const alien of state.aliens) {
      alien.x = 0;
    }
    state.alienDirection = -1;
    const startY = state.aliens[0].y;
    const next = moveAliens(state, 1 / 60);
    expect(next.alienDirection).toBe(1);
    expect(next.aliens[0].y).toBe(startY + 20);
  });
});

describe('moveBullets', () => {
  it('should move bullets by exactly vy * dt', () => {
    const state = createInitialState();
    const shot = playerShoot(state);
    const startY = shot.bullets[0].y;
    const vy = shot.bullets[0].vy;
    const dt = 1 / 60;
    const next = moveBullets(shot, dt);
    expect(next.bullets[0].y).toBeCloseTo(startY + vy * dt, 5);
  });

  it('should remove bullets that leave the screen top', () => {
    const state = createInitialState();
    // Bullet with height 10 at y = -10 means bottom edge at y = 0 (just touching)
    state.bullets = [{ x: 100, y: -10, width: 3, height: 10, vy: -400, owner: 'player' as const }];
    const next = moveBullets(state, 0);
    expect(next.bullets).toHaveLength(0);
  });

  it('should keep bullet that is still partially on screen top', () => {
    const state = createInitialState();
    state.bullets = [{ x: 100, y: -9, width: 3, height: 10, vy: -400, owner: 'player' as const }];
    const next = moveBullets(state, 0);
    expect(next.bullets).toHaveLength(1);
  });

  it('should remove bullets that leave the screen bottom', () => {
    const state = createInitialState();
    state.bullets = [{ x: 100, y: GAME_HEIGHT, width: 3, height: 10, vy: 200, owner: 'alien' as const }];
    const next = moveBullets(state, 0);
    expect(next.bullets).toHaveLength(0);
  });

  it('should keep bullet that is still partially on screen bottom', () => {
    const state = createInitialState();
    state.bullets = [{ x: 100, y: GAME_HEIGHT - 1, width: 3, height: 10, vy: 200, owner: 'alien' as const }];
    const next = moveBullets(state, 0);
    expect(next.bullets).toHaveLength(1);
  });
});

describe('aliensShoot', () => {
  it('should add an alien bullet spawned below the shooter', () => {
    const state = createInitialState();
    // Use only one alien for deterministic test
    for (let i = 1; i < state.aliens.length; i++) {
      state.aliens[i].alive = false;
    }
    const shooter = state.aliens[0];
    const next = aliensShoot(state);
    expect(next.bullets).toHaveLength(1);
    const b = next.bullets[0];
    expect(b.owner).toBe('alien');
    expect(b.vy).toBeGreaterThan(0);
    expect(b.x).toBeCloseTo(shooter.x + shooter.width / 2, 5);
    expect(b.y).toBe(shooter.y + shooter.height);
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
