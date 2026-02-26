import type { GameState } from './state';
import { movePlayer, playerShoot, moveAliens, moveBullets } from './state';
import { checkBulletHits, rectsOverlap } from './collision';
import type { Bullet, ShieldSegment } from './entities';

export interface Actions {
  left: boolean;
  right: boolean;
  shoot: boolean;
}

export function update(state: GameState, dt: number, actions: Actions): GameState {
  if (state.gameOver || state.won) return state;

  // 1. Player movement
  const direction = actions.left ? 'left' : actions.right ? 'right' : 'none';
  let next = movePlayer(state, direction, dt);

  // 2. Shoot
  if (actions.shoot) {
    next = playerShoot(next);
  }

  // 3. Move bullets
  next = moveBullets(next, dt);

  // 4. Move aliens
  next = moveAliens(next, dt);

  // 5. Collisions: player bullets vs aliens
  const playerBullets = next.bullets.filter(b => b.owner === 'player');
  const alienHits = checkBulletHits(playerBullets, next.aliens.filter(a => a.alive));
  const hitBullets = new Set<Bullet>(alienHits.map(h => h.bullet));
  let score = next.score;

  const newAliens = next.aliens.map(a => {
    const hit = alienHits.find(h => h.target === a);
    if (hit) {
      score += a.points;
      return { ...a, alive: false };
    }
    return a;
  });

  // 6. Collisions: alien bullets vs player
  const alienBullets = next.bullets.filter(b => b.owner === 'alien');
  const playerHits = checkBulletHits(alienBullets, [next.player]);
  let lives = next.player.lives;
  for (const hit of playerHits) {
    lives--;
    hitBullets.add(hit.bullet);
  }

  // 7. Collisions: all bullets vs shields
  const remainingBullets = next.bullets.filter(b => !hitBullets.has(b));
  const newShields = next.shields.map(shield => {
    const newSegments = shield.segments
      .map(seg => {
        for (const bullet of remainingBullets) {
          if (rectsOverlap(bullet, seg)) {
            hitBullets.add(bullet);
            return { ...seg, health: seg.health - 1 };
          }
        }
        return seg;
      })
      .filter(seg => seg.health > 0);
    return { ...shield, segments: newSegments };
  });

  // 8. Remove hit bullets
  const finalBullets = next.bullets.filter(b => !hitBullets.has(b));

  // 9. Win/Lose conditions
  const allDead = newAliens.every(a => !a.alive);
  const aliensReachedPlayer = newAliens.some(a => a.alive && a.y >= next.player.y);
  const gameOver = lives <= 0 || aliensReachedPlayer;

  return {
    ...next,
    aliens: newAliens,
    bullets: finalBullets,
    shields: newShields,
    score,
    player: { ...next.player, lives },
    gameOver,
    won: allDead,
  };
}
