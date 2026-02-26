import type { Rect, Bullet, Alien } from './entities';

export interface Hit {
  bullet: Bullet;
  target: Rect & { alive?: boolean };
}

export function rectsOverlap(a: Rect, b: Rect): boolean {
  return (
    a.x < b.x + b.width &&
    a.x + a.width > b.x &&
    a.y < b.y + b.height &&
    a.y + a.height > b.y
  );
}

export function checkBulletHits(
  bullets: Bullet[],
  targets: (Rect & { alive?: boolean })[],
): Hit[] {
  const hits: Hit[] = [];
  for (const bullet of bullets) {
    for (const target of targets) {
      if (target.alive === false) continue;
      if (rectsOverlap(bullet, target)) {
        hits.push({ bullet, target });
        break;
      }
    }
  }
  return hits;
}
