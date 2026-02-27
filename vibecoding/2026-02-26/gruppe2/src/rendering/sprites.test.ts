import { describe, it, expect, vi } from 'vitest';
import { drawAlien, drawPlayer } from './sprites';

function createMockCtx() {
  return {
    fillStyle: '',
    fillRect: vi.fn(),
  } as unknown as CanvasRenderingContext2D;
}

describe('drawAlien', () => {
  it('should draw top alien with red color', () => {
    const ctx = createMockCtx();
    drawAlien(ctx, 0, 0, 40, 30, 'top');
    expect(ctx.fillStyle).toBe('#ff2222');
    expect(ctx.fillRect).toHaveBeenCalled();
  });

  it('should draw middle alien with yellow color', () => {
    const ctx = createMockCtx();
    drawAlien(ctx, 0, 0, 40, 30, 'middle');
    expect(ctx.fillStyle).toBe('#ffff44');
  });

  it('should draw bottom alien with cyan color', () => {
    const ctx = createMockCtx();
    drawAlien(ctx, 0, 0, 40, 30, 'bottom');
    expect(ctx.fillStyle).toBe('#44ffff');
  });

  it('should only draw filled pixels', () => {
    const ctx = createMockCtx();
    drawAlien(ctx, 0, 0, 40, 30, 'top');
    // Top alien has 22 filled pixels out of 70 total
    const calls = (ctx.fillRect as ReturnType<typeof vi.fn>).mock.calls.length;
    expect(calls).toBeGreaterThan(10);
    expect(calls).toBeLessThan(70);
  });
});

describe('drawPlayer', () => {
  it('should draw player with green color', () => {
    const ctx = createMockCtx();
    drawPlayer(ctx, 0, 0, 50, 30);
    expect(ctx.fillStyle).toBe('#33ff33');
    expect(ctx.fillRect).toHaveBeenCalled();
  });

  it('should draw at correct position', () => {
    const ctx = createMockCtx();
    drawPlayer(ctx, 100, 200, 50, 30);
    const calls = (ctx.fillRect as ReturnType<typeof vi.fn>).mock.calls;
    // First filled pixel should be offset from origin
    expect(calls[0][0]).toBeGreaterThanOrEqual(100);
    expect(calls[0][1]).toBeGreaterThanOrEqual(200);
  });
});
