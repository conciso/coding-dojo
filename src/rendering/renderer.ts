import type { GameState } from '../game/state';
import { GAME_WIDTH, GAME_HEIGHT } from '../game/state';
import { drawAlien, drawPlayer } from './sprites';

export function render(ctx: CanvasRenderingContext2D, state: GameState): void {
  // Clear
  ctx.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

  // Player
  drawPlayer(ctx, state.player.x, state.player.y, state.player.width, state.player.height);

  // Aliens
  for (const alien of state.aliens) {
    if (!alien.alive) continue;
    drawAlien(ctx, alien.x, alien.y, alien.width, alien.height, alien.type);
  }

  // Bullets
  for (const bullet of state.bullets) {
    ctx.fillStyle = bullet.owner === 'player' ? '#fff' : '#f88';
    ctx.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
  }

  // Shields
  for (const shield of state.shields) {
    for (const seg of shield.segments) {
      const alpha = seg.health / 3;
      ctx.fillStyle = `rgba(0, 255, 0, ${alpha})`;
      ctx.fillRect(seg.x, seg.y, seg.width, seg.height);
    }
  }

  // HUD
  ctx.fillStyle = '#fff';
  ctx.font = '18px monospace';
  ctx.textAlign = 'left';
  ctx.fillText(`Score: ${state.score}`, 10, 25);
  ctx.fillText(`Lives: ${state.player.lives}`, GAME_WIDTH - 120, 25);

  // Game Over / Win
  if (state.gameOver) {
    ctx.fillStyle = '#f00';
    ctx.font = '48px monospace';
    ctx.textAlign = 'center';
    ctx.fillText('GAME OVER', GAME_WIDTH / 2, GAME_HEIGHT / 2);
  }

  if (state.won) {
    ctx.fillStyle = '#0f0';
    ctx.font = '48px monospace';
    ctx.textAlign = 'center';
    ctx.fillText('YOU WIN', GAME_WIDTH / 2, GAME_HEIGHT / 2);
  }
}
