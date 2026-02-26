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
let alienShootTimer = 0;
const ALIEN_SHOOT_INTERVAL = 1.0;
const FIXED_DT = 1 / 60;

function gameLoop(time: number) {
  const dt = Math.min((time - lastTime) / 1000, 0.1);
  lastTime = time;

  // Alien shooting on timer
  alienShootTimer += dt;
  if (alienShootTimer >= ALIEN_SHOOT_INTERVAL) {
    alienShootTimer -= ALIEN_SHOOT_INTERVAL;
    state = aliensShoot(state);
  }

  state = update(state, FIXED_DT, input.getActions());
  render(ctx, state);

  // Restart on Space after game over
  if ((state.gameOver || state.won) && input.getActions().shoot) {
    state = createInitialState();
  }

  requestAnimationFrame(gameLoop);
}

requestAnimationFrame(gameLoop);
