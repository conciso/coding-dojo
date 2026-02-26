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

  it('should create bullet on shoot action', () => {
    const state = createInitialState();
    const next = update(state, 1 / 60, { ...noActions(), shoot: true });
    expect(next.bullets.some(b => b.owner === 'player')).toBe(true);
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
    // Place bullet directly on first alien
    const alien = state.aliens[0];
    state.bullets = [{
      x: alien.x + 1, y: alien.y + 1,
      width: 3, height: 10, vy: -400, owner: 'player',
    }];
    const next = update(state, 0, noActions());
    expect(next.aliens[0].alive).toBe(false);
    expect(next.score).toBe(alien.points);
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
    // Keep last one alive, then kill it
    state.aliens[0].alive = true;
    state.bullets = [{
      x: state.aliens[0].x + 1, y: state.aliens[0].y + 1,
      width: 3, height: 10, vy: -400, owner: 'player',
    }];
    const next = update(state, 0, noActions());
    expect(next.won).toBe(true);
  });

  it('should set gameOver when aliens reach player row', () => {
    const state = createInitialState();
    for (const alien of state.aliens) {
      alien.y = state.player.y;
    }
    const next = update(state, 0, noActions());
    expect(next.gameOver).toBe(true);
  });

  it('should not update when game is over', () => {
    const state = createInitialState();
    state.gameOver = true;
    const next = update(state, 1 / 60, { ...noActions(), left: true });
    expect(next).toBe(state);
  });
});
