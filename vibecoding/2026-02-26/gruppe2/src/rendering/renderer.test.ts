import { describe, it, expect, vi } from 'vitest';
import { render } from './renderer';
import { createInitialState } from '../game/state';

function createMockCtx() {
  return {
    fillStyle: '',
    font: '',
    textAlign: '',
    fillRect: vi.fn(),
    fillText: vi.fn(),
    clearRect: vi.fn(),
  } as unknown as CanvasRenderingContext2D;
}

describe('render', () => {
  it('should clear the canvas', () => {
    const ctx = createMockCtx();
    const state = createInitialState();
    render(ctx, state);
    expect(ctx.clearRect).toHaveBeenCalledWith(0, 0, 800, 600);
  });

  it('should draw the player as sprite pixels', () => {
    const ctx = createMockCtx();
    const state = createInitialState();
    render(ctx, state);
    // Player sprite draws many small rects, not one big one
    expect(ctx.fillRect).toHaveBeenCalled();
  });

  it('should draw alive aliens as sprite pixels', () => {
    const ctx = createMockCtx();
    const state = createInitialState();
    render(ctx, state);
    // 55 aliens * ~20-30 pixels each + player pixels + shield segments = lots of calls
    const totalCalls = (ctx.fillRect as ReturnType<typeof vi.fn>).mock.calls.length;
    expect(totalCalls).toBeGreaterThan(100);
  });

  it('should draw far fewer rects when all aliens dead', () => {
    const ctx = createMockCtx();
    const state = createInitialState();
    const aliveState = createInitialState();
    render(ctx, aliveState);
    const aliveCalls = (ctx.fillRect as ReturnType<typeof vi.fn>).mock.calls.length;

    const ctx2 = createMockCtx();
    for (const alien of state.aliens) alien.alive = false;
    render(ctx2, state);
    const deadCalls = (ctx2.fillRect as ReturnType<typeof vi.fn>).mock.calls.length;

    expect(deadCalls).toBeLessThan(aliveCalls);
  });

  it('should draw bullets', () => {
    const ctx = createMockCtx();
    const state = createInitialState();
    state.bullets = [{ x: 100, y: 200, width: 3, height: 10, vy: -400, owner: 'player' as const }];
    render(ctx, state);
    expect(ctx.fillRect).toHaveBeenCalledWith(100, 200, 3, 10);
  });

  it('should draw score and lives text', () => {
    const ctx = createMockCtx();
    const state = createInitialState();
    render(ctx, state);
    const textCalls = (ctx.fillText as ReturnType<typeof vi.fn>).mock.calls;
    const texts = textCalls.map((c: unknown[]) => c[0] as string);
    expect(texts.some(t => t.includes('Score'))).toBe(true);
    expect(texts.some(t => t.includes('Lives'))).toBe(true);
  });

  it('should draw game over text when game is over', () => {
    const ctx = createMockCtx();
    const state = createInitialState();
    state.gameOver = true;
    render(ctx, state);
    const textCalls = (ctx.fillText as ReturnType<typeof vi.fn>).mock.calls;
    const texts = textCalls.map((c: unknown[]) => c[0] as string);
    expect(texts.some(t => t.includes('GAME OVER'))).toBe(true);
  });

  it('should draw win text when won', () => {
    const ctx = createMockCtx();
    const state = createInitialState();
    state.won = true;
    render(ctx, state);
    const textCalls = (ctx.fillText as ReturnType<typeof vi.fn>).mock.calls;
    const texts = textCalls.map((c: unknown[]) => c[0] as string);
    expect(texts.some(t => t.includes('YOU WIN'))).toBe(true);
  });
});
