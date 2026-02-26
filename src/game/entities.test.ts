import { describe, it, expect } from 'vitest';
import {
  createPlayer,
  createAlien,
  createBullet,
  createShieldSegment,
  createShield,
} from './entities';

describe('Player', () => {
  it('should create a player with default values', () => {
    const player = createPlayer();
    expect(player.x).toBe(375);
    expect(player.y).toBe(550);
    expect(player.width).toBe(50);
    expect(player.height).toBe(30);
    expect(player.lives).toBe(3);
  });

  it('should create a player with custom position', () => {
    const player = createPlayer(100, 200);
    expect(player.x).toBe(100);
    expect(player.y).toBe(200);
  });
});

describe('Alien', () => {
  it('should create an alien with type and points', () => {
    const alien = createAlien(50, 100, 'top', 30);
    expect(alien.x).toBe(50);
    expect(alien.y).toBe(100);
    expect(alien.type).toBe('top');
    expect(alien.points).toBe(30);
    expect(alien.alive).toBe(true);
    expect(alien.width).toBe(40);
    expect(alien.height).toBe(30);
  });

  it('should create different alien types', () => {
    const top = createAlien(0, 0, 'top', 30);
    const mid = createAlien(0, 0, 'middle', 20);
    const bot = createAlien(0, 0, 'bottom', 10);
    expect(top.points).toBe(30);
    expect(mid.points).toBe(20);
    expect(bot.points).toBe(10);
  });
});

describe('Bullet', () => {
  it('should create a player bullet moving upward', () => {
    const bullet = createBullet(100, 500, 'player');
    expect(bullet.x).toBe(100);
    expect(bullet.y).toBe(500);
    expect(bullet.vy).toBe(-400);
    expect(bullet.owner).toBe('player');
    expect(bullet.width).toBe(3);
    expect(bullet.height).toBe(10);
  });

  it('should create an alien bullet moving downward', () => {
    const bullet = createBullet(100, 100, 'alien');
    expect(bullet.vy).toBe(200);
    expect(bullet.owner).toBe('alien');
  });
});

describe('Shield', () => {
  it('should create a shield segment with full health', () => {
    const seg = createShieldSegment(10, 20);
    expect(seg.x).toBe(10);
    expect(seg.y).toBe(20);
    expect(seg.health).toBe(3);
    expect(seg.width).toBe(10);
    expect(seg.height).toBe(10);
  });

  it('should create a shield with exactly 5x3 = 15 segments', () => {
    const shield = createShield(100, 480);
    expect(shield.x).toBe(100);
    expect(shield.y).toBe(480);
    expect(shield.segments).toHaveLength(15);
  });

  it('should position segments in a correct 5x3 grid', () => {
    const shield = createShield(100, 480);
    // First segment at origin
    expect(shield.segments[0].x).toBe(100);
    expect(shield.segments[0].y).toBe(480);
    // Second segment one column right
    expect(shield.segments[1].x).toBe(110);
    expect(shield.segments[1].y).toBe(480);
    // 6th segment = second row, first col
    expect(shield.segments[5].x).toBe(100);
    expect(shield.segments[5].y).toBe(490);
    // Last segment (row 2, col 4)
    expect(shield.segments[14].x).toBe(140);
    expect(shield.segments[14].y).toBe(500);
  });
});
