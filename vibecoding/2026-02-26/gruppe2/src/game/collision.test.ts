import { describe, it, expect } from 'vitest';
import { rectsOverlap, checkBulletHits } from './collision';
import { createAlien, createBullet } from './entities';

describe('rectsOverlap', () => {
  it('should detect overlapping rects', () => {
    const a = { x: 0, y: 0, width: 20, height: 20 };
    const b = { x: 10, y: 10, width: 20, height: 20 };
    expect(rectsOverlap(a, b)).toBe(true);
  });

  it('should return false for separated rects', () => {
    const a = { x: 0, y: 0, width: 20, height: 20 };
    const b = { x: 100, y: 100, width: 20, height: 20 };
    expect(rectsOverlap(a, b)).toBe(false);
  });

  it('should return false when edges exactly touch horizontally', () => {
    const a = { x: 0, y: 0, width: 20, height: 20 };
    const b = { x: 20, y: 0, width: 20, height: 20 };
    expect(rectsOverlap(a, b)).toBe(false);
  });

  it('should return false when edges exactly touch vertically', () => {
    const a = { x: 0, y: 0, width: 20, height: 20 };
    const b = { x: 0, y: 20, width: 20, height: 20 };
    expect(rectsOverlap(a, b)).toBe(false);
  });

  it('should detect overlap by 1px horizontally', () => {
    const a = { x: 0, y: 0, width: 20, height: 20 };
    const b = { x: 19, y: 0, width: 20, height: 20 };
    expect(rectsOverlap(a, b)).toBe(true);
  });

  it('should detect overlap by 1px vertically', () => {
    const a = { x: 0, y: 0, width: 20, height: 20 };
    const b = { x: 0, y: 19, width: 20, height: 20 };
    expect(rectsOverlap(a, b)).toBe(true);
  });

  it('should detect partial corner overlap', () => {
    const a = { x: 0, y: 0, width: 20, height: 20 };
    const b = { x: 19, y: 19, width: 20, height: 20 };
    expect(rectsOverlap(a, b)).toBe(true);
  });

  it('should return false when separated vertically', () => {
    const a = { x: 0, y: 0, width: 20, height: 20 };
    const b = { x: 0, y: 50, width: 20, height: 20 };
    expect(rectsOverlap(a, b)).toBe(false);
  });

  it('should return false when b is fully left of a', () => {
    const a = { x: 50, y: 0, width: 20, height: 20 };
    const b = { x: 0, y: 0, width: 20, height: 20 };
    expect(rectsOverlap(a, b)).toBe(false);
  });

  it('should return false when b is fully above a', () => {
    const a = { x: 0, y: 50, width: 20, height: 20 };
    const b = { x: 0, y: 0, width: 20, height: 20 };
    expect(rectsOverlap(a, b)).toBe(false);
  });

  it('should return false when a.x exactly equals b.x + b.width', () => {
    const a = { x: 20, y: 0, width: 10, height: 10 };
    const b = { x: 0, y: 0, width: 20, height: 10 };
    expect(rectsOverlap(a, b)).toBe(false);
  });

  it('should return false when a.y exactly equals b.y + b.height', () => {
    const a = { x: 0, y: 20, width: 10, height: 10 };
    const b = { x: 0, y: 0, width: 10, height: 20 };
    expect(rectsOverlap(a, b)).toBe(false);
  });
});

describe('checkBulletHits', () => {
  it('should detect bullet hitting a target', () => {
    const bullet = createBullet(50, 100, 'player');
    const alien = createAlien(48, 95, 'top', 30);
    const hits = checkBulletHits([bullet], [alien]);
    expect(hits).toHaveLength(1);
    expect(hits[0].bullet).toBe(bullet);
    expect(hits[0].target).toBe(alien);
  });

  it('should return no hits when bullet misses', () => {
    const bullet = createBullet(0, 0, 'player');
    const alien = createAlien(500, 500, 'top', 30);
    const hits = checkBulletHits([bullet], [alien]);
    expect(hits).toHaveLength(0);
  });

  it('should ignore dead aliens', () => {
    const bullet = createBullet(50, 100, 'player');
    const alien = createAlien(48, 95, 'top', 30);
    alien.alive = false;
    const hits = checkBulletHits([bullet], [alien]);
    expect(hits).toHaveLength(0);
  });

  it('should handle multiple bullets hitting different targets', () => {
    const b1 = createBullet(50, 100, 'player');
    const b2 = createBullet(200, 100, 'player');
    const a1 = createAlien(48, 95, 'top', 30);
    const a2 = createAlien(198, 95, 'middle', 20);
    const hits = checkBulletHits([b1, b2], [a1, a2]);
    expect(hits).toHaveLength(2);
    expect(hits[0].bullet).toBe(b1);
    expect(hits[1].bullet).toBe(b2);
  });

  it('should only match first target per bullet', () => {
    const bullet = createBullet(50, 100, 'player');
    const a1 = createAlien(48, 95, 'top', 30);
    const a2 = createAlien(48, 95, 'middle', 20);
    const hits = checkBulletHits([bullet], [a1, a2]);
    expect(hits).toHaveLength(1);
    expect(hits[0].target).toBe(a1);
  });

  it('should return empty array for empty inputs', () => {
    expect(checkBulletHits([], [])).toHaveLength(0);
  });
});
