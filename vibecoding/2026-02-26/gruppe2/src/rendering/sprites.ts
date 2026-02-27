import type { AlienType } from '../game/entities';

// Classic Space Invaders pixel patterns (1 = filled, 0 = empty)
// Each row is a horizontal line of pixels

const ALIEN_TOP = [
  [0,0,0,0,1,1,0,0,0,0],
  [0,0,0,1,1,1,1,0,0,0],
  [0,0,1,1,1,1,1,1,0,0],
  [0,1,1,0,1,1,0,1,1,0],
  [0,1,1,1,1,1,1,1,1,0],
  [0,0,0,1,0,0,1,0,0,0],
  [0,0,1,0,0,0,0,1,0,0],
];

const ALIEN_MIDDLE = [
  [0,0,1,0,0,0,0,0,1,0,0],
  [0,0,0,1,0,0,0,1,0,0,0],
  [0,0,1,1,1,1,1,1,1,0,0],
  [0,1,1,0,1,1,1,0,1,1,0],
  [1,1,1,1,1,1,1,1,1,1,1],
  [1,0,1,1,1,1,1,1,1,0,1],
  [1,0,1,0,0,0,0,0,1,0,1],
  [0,0,0,1,1,0,1,1,0,0,0],
];

const ALIEN_BOTTOM = [
  [0,0,0,0,1,1,1,1,0,0,0,0],
  [0,1,1,1,1,1,1,1,1,1,1,0],
  [1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,0,0,1,1,0,0,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1],
  [0,0,1,1,1,0,0,1,1,1,0,0],
  [0,1,1,0,0,1,1,0,0,1,1,0],
  [0,0,1,1,0,0,0,0,1,1,0,0],
];

const PLAYER_SPRITE = [
  [0,0,0,0,0,0,1,0,0,0,0,0,0],
  [0,0,0,0,0,1,1,1,0,0,0,0,0],
  [0,0,0,0,0,1,1,1,0,0,0,0,0],
  [0,1,1,1,1,1,1,1,1,1,1,1,0],
  [1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1],
];

const SPRITE_MAP: Record<AlienType, number[][]> = {
  top: ALIEN_TOP,
  middle: ALIEN_MIDDLE,
  bottom: ALIEN_BOTTOM,
};

const COLOR_MAP: Record<AlienType, string> = {
  top: '#ff2222',
  middle: '#ffff44',
  bottom: '#44ffff',
};

export function drawAlien(
  ctx: CanvasRenderingContext2D,
  x: number, y: number,
  width: number, height: number,
  type: AlienType,
): void {
  const sprite = SPRITE_MAP[type];
  const color = COLOR_MAP[type];
  const cols = sprite[0].length;
  const rows = sprite.length;
  const px = width / cols;
  const py = height / rows;

  ctx.fillStyle = color;
  for (let r = 0; r < rows; r++) {
    for (let c = 0; c < cols; c++) {
      if (sprite[r][c]) {
        ctx.fillRect(x + c * px, y + r * py, px + 0.5, py + 0.5);
      }
    }
  }
}

export function drawPlayer(
  ctx: CanvasRenderingContext2D,
  x: number, y: number,
  width: number, height: number,
): void {
  const sprite = PLAYER_SPRITE;
  const cols = sprite[0].length;
  const rows = sprite.length;
  const px = width / cols;
  const py = height / rows;

  ctx.fillStyle = '#33ff33';
  for (let r = 0; r < rows; r++) {
    for (let c = 0; c < cols; c++) {
      if (sprite[r][c]) {
        ctx.fillRect(x + c * px, y + r * py, px + 0.5, py + 0.5);
      }
    }
  }
}
