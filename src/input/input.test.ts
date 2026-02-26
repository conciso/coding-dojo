import { describe, it, expect } from 'vitest';
import { InputHandler } from './input';

describe('InputHandler', () => {
  it('should start with no actions active', () => {
    const input = new InputHandler();
    const actions = input.getActions();
    expect(actions.left).toBe(false);
    expect(actions.right).toBe(false);
    expect(actions.shoot).toBe(false);
  });

  it('should set left on ArrowLeft keydown', () => {
    const input = new InputHandler();
    input.handleKeyDown('ArrowLeft');
    expect(input.getActions().left).toBe(true);
    expect(input.getActions().right).toBe(false);
    expect(input.getActions().shoot).toBe(false);
  });

  it('should set right on ArrowRight keydown', () => {
    const input = new InputHandler();
    input.handleKeyDown('ArrowRight');
    expect(input.getActions().right).toBe(true);
    expect(input.getActions().left).toBe(false);
    expect(input.getActions().shoot).toBe(false);
  });

  it('should set shoot on Space keydown', () => {
    const input = new InputHandler();
    input.handleKeyDown(' ');
    expect(input.getActions().shoot).toBe(true);
    expect(input.getActions().left).toBe(false);
    expect(input.getActions().right).toBe(false);
  });

  it('should clear left on ArrowLeft keyup', () => {
    const input = new InputHandler();
    input.handleKeyDown('ArrowLeft');
    input.handleKeyUp('ArrowLeft');
    expect(input.getActions().left).toBe(false);
  });

  it('should clear right on ArrowRight keyup', () => {
    const input = new InputHandler();
    input.handleKeyDown('ArrowRight');
    input.handleKeyUp('ArrowRight');
    expect(input.getActions().right).toBe(false);
  });

  it('should clear shoot on Space keyup', () => {
    const input = new InputHandler();
    input.handleKeyDown(' ');
    input.handleKeyUp(' ');
    expect(input.getActions().shoot).toBe(false);
  });

  it('should not change any action on unmapped keydown', () => {
    const input = new InputHandler();
    input.handleKeyDown('a');
    expect(input.getActions().left).toBe(false);
    expect(input.getActions().right).toBe(false);
    expect(input.getActions().shoot).toBe(false);
  });

  it('should not change any action on unmapped keyup', () => {
    const input = new InputHandler();
    input.handleKeyDown('ArrowLeft');
    input.handleKeyUp('a');
    // ArrowLeft should still be pressed
    expect(input.getActions().left).toBe(true);
  });

  it('should bind to an EventTarget and respond to events', () => {
    const input = new InputHandler();
    const target = new EventTarget();
    input.bind(target);
    target.dispatchEvent(Object.assign(new Event('keydown'), { key: 'ArrowLeft' }));
    expect(input.getActions().left).toBe(true);
    target.dispatchEvent(Object.assign(new Event('keyup'), { key: 'ArrowLeft' }));
    expect(input.getActions().left).toBe(false);
  });

  it('should return a copy of actions, not the internal state', () => {
    const input = new InputHandler();
    const actions1 = input.getActions();
    input.handleKeyDown('ArrowLeft');
    const actions2 = input.getActions();
    expect(actions1.left).toBe(false);
    expect(actions2.left).toBe(true);
  });
});
