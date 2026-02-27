export interface Rect {
  x: number;
  y: number;
  width: number;
  height: number;
}

export interface Player extends Rect {
  lives: number;
}

export type AlienType = 'top' | 'middle' | 'bottom';

export interface Alien extends Rect {
  type: AlienType;
  points: number;
  alive: boolean;
}

export type BulletOwner = 'player' | 'alien';

export interface Bullet extends Rect {
  vy: number;
  owner: BulletOwner;
}

export interface ShieldSegment extends Rect {
  health: number;
}

export interface Shield {
  x: number;
  y: number;
  segments: ShieldSegment[];
}

export function createPlayer(x = 375, y = 550): Player {
  return { x, y, width: 50, height: 30, lives: 3 };
}

export function createAlien(x: number, y: number, type: AlienType, points: number): Alien {
  return { x, y, width: 40, height: 30, type, points, alive: true };
}

export function createBullet(x: number, y: number, owner: BulletOwner): Bullet {
  const vy = owner === 'player' ? -400 : 200;
  return { x, y, width: 3, height: 10, vy, owner };
}

export function createShieldSegment(x: number, y: number): ShieldSegment {
  return { x, y, width: 10, height: 10, health: 3 };
}

export function createShield(x: number, y: number): Shield {
  const segments: ShieldSegment[] = [];
  const cols = 5;
  const rows = 3;
  for (let row = 0; row < rows; row++) {
    for (let col = 0; col < cols; col++) {
      segments.push(createShieldSegment(x + col * 10, y + row * 10));
    }
  }
  return { x, y, segments };
}
