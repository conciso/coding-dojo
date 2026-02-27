import { createInitialState, GAME_WIDTH, GAME_HEIGHT } from './game/state';
import { update } from './game/update';
import { render } from './rendering/renderer';
import { InputHandler } from './input/input';
import { aliensShoot } from './game/state';

const canvas = document.createElement('canvas');
canvas.width = GAME_WIDTH;
canvas.height = GAME_HEIGHT;
document.body.style.margin = '0';
document.body.style.background = '#000';
document.body.style.display = 'flex';
document.body.style.justifyContent = 'center';
document.body.style.alignItems = 'center';
document.body.style.height = '100vh';
document.body.appendChild(canvas);

const ctx = canvas.getContext('2d')!;
const input = new InputHandler();
input.bind(window);

let state = createInitialState();
let lastTime = 0;
let accumulator = 0;
let alienShootTimer = 0;
const ALIEN_SHOOT_INTERVAL = 1.0;
const FIXED_DT = 1 / 60;

function gameLoop(time: number) {
  const frameTime = Math.min((time - lastTime) / 1000, 0.1);
  lastTime = time;
  accumulator += frameTime;

  // Alien shooting on real time
  alienShootTimer += frameTime;
  if (alienShootTimer >= ALIEN_SHOOT_INTERVAL) {
    alienShootTimer -= ALIEN_SHOOT_INTERVAL;
    state = aliensShoot(state);
  }

  // Fixed timestep: consume accumulated time in fixed steps
  const actions = input.getActions();
  while (accumulator >= FIXED_DT) {
    state = update(state, FIXED_DT, actions);
    accumulator -= FIXED_DT;
  }

  render(ctx, state);

  // Restart on Space after game over
  if ((state.gameOver || state.won) && input.getActions().shoot) {
    state = createInitialState();
  }

  requestAnimationFrame(gameLoop);
}

requestAnimationFrame(gameLoop);
