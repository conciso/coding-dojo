import type { Player, Alien, Bullet, Shield, AlienType } from './entities';
import { createPlayer, createAlien, createBullet, createShield } from './entities';

export const GAME_WIDTH = 800;
export const GAME_HEIGHT = 600;
const PLAYER_SPEED = 300;
const ALIEN_SPEED_BASE = 60;
const ALIEN_STEP_DOWN = 20;

export interface GameState {
  player: Player;
  aliens: Alien[];
  bullets: Bullet[];
  shields: Shield[];
  score: number;
  gameOver: boolean;
  won: boolean;
  alienDirection: 1 | -1;
}

export type Direction = 'left' | 'right' | 'none';

export function createInitialState(): GameState {
  const player = createPlayer(GAME_WIDTH / 2 - 25, 550);
  const aliens = createAlienGrid();
  const shields = [
    createShield(150, 480),
    createShield(300, 480),
    createShield(450, 480),
    createShield(600, 480),
  ];

  return {
    player,
    aliens,
    bullets: [],
    shields,
    score: 0,
    gameOver: false,
    won: false,
    alienDirection: 1,
  };
}

function createAlienGrid(): Alien[] {
  const aliens: Alien[] = [];
  const rows: { type: AlienType; points: number }[] = [
    { type: 'top', points: 30 },
    { type: 'middle', points: 20 },
    { type: 'middle', points: 20 },
    { type: 'bottom', points: 10 },
    { type: 'bottom', points: 10 },
  ];

  for (let row = 0; row < rows.length; row++) {
    for (let col = 0; col < 11; col++) {
      const x = 80 + col * 55;
      const y = 60 + row * 45;
      aliens.push(createAlien(x, y, rows[row].type, rows[row].points));
    }
  }
  return aliens;
}

export function movePlayer(state: GameState, direction: Direction, dt: number): GameState {
  if (direction === 'none') return state;

  const dir = direction === 'left' ? -1 : 1;
  let newX = state.player.x + dir * PLAYER_SPEED * dt;
  newX = Math.max(0, Math.min(GAME_WIDTH - state.player.width, newX));

  return { ...state, player: { ...state.player, x: newX } };
}

export function playerShoot(state: GameState): GameState {
  const hasPlayerBullet = state.bullets.some(b => b.owner === 'player');
  if (hasPlayerBullet) return state;

  const bullet = createBullet(
    state.player.x + state.player.width / 2 - 1.5,
    state.player.y,
    'player',
  );
  return { ...state, bullets: [...state.bullets, bullet] };
}

export function moveAliens(state: GameState, dt: number): GameState {
  const liveAliens = state.aliens.filter(a => a.alive);
  if (liveAliens.length === 0) return state;

  const speed = ALIEN_SPEED_BASE + (55 - liveAliens.length) * 2;
  let direction = state.alienDirection;

  // Check if any alive alien hits the edge
  const hitsEdge = liveAliens.some(a => {
    if (direction === 1) return a.x + a.width + speed * dt >= GAME_WIDTH;
    return a.x - speed * dt <= 0;
  });

  if (hitsEdge) {
    // Step down and reverse
    const newAliens = state.aliens.map(a => ({ ...a, y: a.y + ALIEN_STEP_DOWN }));
    const newDirection = direction === 1 ? -1 : 1;
    return { ...state, aliens: newAliens, alienDirection: newDirection as 1 | -1 };
  }

  const dx = direction * speed * dt;
  const newAliens = state.aliens.map(a => ({ ...a, x: a.x + dx }));
  return { ...state, aliens: newAliens };
}

export function moveBullets(state: GameState, dt: number): GameState {
  const newBullets = state.bullets
    .map(b => ({ ...b, y: b.y + b.vy * dt }))
    .filter(b => b.y + b.height > 0 && b.y < GAME_HEIGHT);
  return { ...state, bullets: newBullets };
}

export function aliensShoot(state: GameState): GameState {
  const liveAliens = state.aliens.filter(a => a.alive);
  if (liveAliens.length === 0) return state;

  const shooter = liveAliens[Math.floor(Math.random() * liveAliens.length)];
  const bullet = createBullet(
    shooter.x + shooter.width / 2,
    shooter.y + shooter.height,
    'alien',
  );
  return { ...state, bullets: [...state.bullets, bullet] };
}
