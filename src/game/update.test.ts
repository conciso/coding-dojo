import { describe, it, expect } from 'vitest';
import { update } from './update';
import { createInitialState } from './state';
import type { Actions } from './update';

function noActions(): Actions {
  return { left: false, right: false, shoot: false };
}

describe('update', () => {
  it('should move player left when left action is active', () => {
    const state = createInitialState();
    const startX = state.player.x;
    const next = update(state, 1 / 60, { ...noActions(), left: true });
    expect(next.player.x).toBeLessThan(startX);
  });

  it('should move player right when right action is active', () => {
    const state = createInitialState();
    const startX = state.player.x;
    const next = update(state, 1 / 60, { ...noActions(), right: true });
    expect(next.player.x).toBeGreaterThan(startX);
  });

  it('should not shoot when shoot is false', () => {
    const state = createInitialState();
    const next = update(state, 1 / 60, noActions());
    expect(next.bullets.filter(b => b.owner === 'player')).toHaveLength(0);
  });

  it('should create bullet on shoot action', () => {
    const state = createInitialState();
    const next = update(state, 1 / 60, { ...noActions(), shoot: true });
    const playerBullets = next.bullets.filter(b => b.owner === 'player');
    expect(playerBullets).toHaveLength(1);
  });

  it('should move bullets each tick', () => {
    const state = createInitialState();
    const shot = update(state, 1 / 60, { ...noActions(), shoot: true });
    const bulletY = shot.bullets.find(b => b.owner === 'player')!.y;
    const next = update(shot, 1 / 60, noActions());
    expect(next.bullets.find(b => b.owner === 'player')!.y).toBeLessThan(bulletY);
  });

  it('should kill alien and add score on hit', () => {
    const state = createInitialState();
    const alien = state.aliens[0];
    state.bullets = [{
      x: alien.x + 1, y: alien.y + 1,
      width: 3, height: 10, vy: -400, owner: 'player',
    }];
    const next = update(state, 0, noActions());
    expect(next.aliens[0].alive).toBe(false);
    expect(next.score).toBe(alien.points);
  });

  it('should only hit alive aliens with player bullets', () => {
    const state = createInitialState();
    const alien = state.aliens[0];
    alien.alive = false;
    state.bullets = [{
      x: alien.x + 1, y: alien.y + 1,
      width: 3, height: 10, vy: -400, owner: 'player',
    }];
    const next = update(state, 0, noActions());
    expect(next.score).toBe(0);
  });

  it('should remove player bullet on alien hit', () => {
    const state = createInitialState();
    const alien = state.aliens[0];
    state.bullets = [{
      x: alien.x + 1, y: alien.y + 1,
      width: 3, height: 10, vy: -400, owner: 'player',
    }];
    const next = update(state, 0, noActions());
    expect(next.bullets.filter(b => b.owner === 'player')).toHaveLength(0);
  });

  it('should only use player bullets for alien collision, not alien bullets', () => {
    const state = createInitialState();
    const alien = state.aliens[0];
    // Place alien bullet on an alien — should NOT kill it
    state.bullets = [{
      x: alien.x + 1, y: alien.y + 1,
      width: 3, height: 10, vy: 200, owner: 'alien',
    }];
    const next = update(state, 0, noActions());
    expect(next.aliens[0].alive).toBe(true);
    expect(next.score).toBe(0);
  });

  it('should lose a life when alien bullet hits player', () => {
    const state = createInitialState();
    state.bullets = [{
      x: state.player.x + 1, y: state.player.y + 1,
      width: 3, height: 10, vy: 200, owner: 'alien',
    }];
    const next = update(state, 0, noActions());
    expect(next.player.lives).toBe(2);
  });

  it('should remove alien bullet on player hit', () => {
    const state = createInitialState();
    state.bullets = [{
      x: state.player.x + 1, y: state.player.y + 1,
      width: 3, height: 10, vy: 200, owner: 'alien',
    }];
    const next = update(state, 0, noActions());
    expect(next.bullets.filter(b => b.owner === 'alien')).toHaveLength(0);
  });

  it('should not lose life from player bullet hitting player area', () => {
    const state = createInitialState();
    // Player bullet overlapping player position should NOT hurt player
    state.bullets = [{
      x: state.player.x + 1, y: state.player.y + 1,
      width: 3, height: 10, vy: -400, owner: 'player',
    }];
    // Kill all aliens so bullet doesn't hit them
    for (const alien of state.aliens) alien.alive = false;
    const next = update(state, 0, noActions());
    expect(next.player.lives).toBe(3);
  });

  it('should damage shield segment on bullet hit', () => {
    const state = createInitialState();
    const seg = state.shields[0].segments[0];
    state.bullets = [{
      x: seg.x + 1, y: seg.y + 1,
      width: 3, height: 10, vy: -400, owner: 'player',
    }];
    const next = update(state, 0, noActions());
    const hitSeg = next.shields[0].segments[0];
    expect(hitSeg.health).toBe(2);
  });

  it('should remove bullet that hits shield', () => {
    const state = createInitialState();
    const seg = state.shields[0].segments[0];
    // Kill all aliens so bullet doesn't hit them first
    for (const alien of state.aliens) alien.alive = false;
    state.bullets = [{
      x: seg.x + 1, y: seg.y + 1,
      width: 3, height: 10, vy: -400, owner: 'player',
    }];
    const next = update(state, 0, noActions());
    expect(next.bullets).toHaveLength(0);
  });

  it('should not let a bullet that hit an alien also hit a shield', () => {
    const state = createInitialState();
    // Place alien directly on top of a shield segment
    const seg = state.shields[0].segments[0];
    state.aliens[0].x = seg.x;
    state.aliens[0].y = seg.y;
    state.bullets = [{
      x: seg.x + 1, y: seg.y + 1,
      width: 3, height: 10, vy: -400, owner: 'player',
    }];
    const origHealth = seg.health;
    const next = update(state, 0, noActions());
    // Alien should be dead
    expect(next.aliens[0].alive).toBe(false);
    // Shield should NOT be damaged — bullet was consumed by alien hit
    const shieldSeg = next.shields[0].segments.find(s => s.x === seg.x && s.y === seg.y);
    expect(shieldSeg!.health).toBe(origHealth);
  });

  it('should remove shield segment when health reaches 0', () => {
    const state = createInitialState();
    const seg = state.shields[0].segments[0];
    seg.health = 1;
    state.bullets = [{
      x: seg.x + 1, y: seg.y + 1,
      width: 3, height: 10, vy: -400, owner: 'player',
    }];
    const next = update(state, 0, noActions());
    const remaining = next.shields[0].segments.filter(
      s => s.x === seg.x && s.y === seg.y,
    );
    expect(remaining).toHaveLength(0);
  });

  it('should set gameOver when lives reach 0', () => {
    const state = createInitialState();
    state.player.lives = 1;
    state.bullets = [{
      x: state.player.x + 1, y: state.player.y + 1,
      width: 3, height: 10, vy: 200, owner: 'alien',
    }];
    const next = update(state, 0, noActions());
    expect(next.gameOver).toBe(true);
  });

  it('should set won when all aliens are dead', () => {
    const state = createInitialState();
    for (const alien of state.aliens) {
      alien.alive = false;
    }
    state.aliens[0].alive = true;
    state.bullets = [{
      x: state.aliens[0].x + 1, y: state.aliens[0].y + 1,
      width: 3, height: 10, vy: -400, owner: 'player',
    }];
    const next = update(state, 0, noActions());
    expect(next.won).toBe(true);
  });

  it('should not set won when some aliens still alive', () => {
    const state = createInitialState();
    for (const alien of state.aliens) alien.alive = false;
    state.aliens[0].alive = true;
    state.aliens[1].alive = true;
    state.bullets = [{
      x: state.aliens[0].x + 1, y: state.aliens[0].y + 1,
      width: 3, height: 10, vy: -400, owner: 'player',
    }];
    const next = update(state, 0, noActions());
    expect(next.won).toBe(false);
    expect(next.aliens[1].alive).toBe(true);
  });

  it('should set gameOver when aliens reach player row', () => {
    const state = createInitialState();
    for (const alien of state.aliens) {
      alien.y = state.player.y;
    }
    const next = update(state, 0, noActions());
    expect(next.gameOver).toBe(true);
  });

  it('should set gameOver when only one alien reaches player row (some vs every)', () => {
    const state = createInitialState();
    // Only first alien at player row, rest far above
    state.aliens[0].y = state.player.y;
    for (let i = 1; i < state.aliens.length; i++) {
      state.aliens[i].y = 50;
    }
    const next = update(state, 0, noActions());
    expect(next.gameOver).toBe(true);
  });

  it('should not set gameOver from dead aliens at player row', () => {
    const state = createInitialState();
    for (const alien of state.aliens) {
      alien.y = state.player.y;
      alien.alive = false;
    }
    const next = update(state, 0, noActions());
    expect(next.gameOver).toBe(false);
  });

  it('should not update when game is over', () => {
    const state = createInitialState();
    state.gameOver = true;
    const next = update(state, 1 / 60, { ...noActions(), left: true });
    expect(next).toBe(state);
  });

  it('should not update when game is won', () => {
    const state = createInitialState();
    state.won = true;
    const next = update(state, 1 / 60, { ...noActions(), left: true });
    expect(next).toBe(state);
  });
});
