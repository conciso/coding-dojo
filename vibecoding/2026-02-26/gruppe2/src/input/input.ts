import type { Actions } from '../game/update';

const KEY_MAP: Record<string, keyof Actions> = {
  ArrowLeft: 'left',
  ArrowRight: 'right',
  ' ': 'shoot',
};

export class InputHandler {
  private pressed: Actions = { left: false, right: false, shoot: false };

  handleKeyDown(key: string): void {
    const action = KEY_MAP[key];
    if (action) this.pressed[action] = true;
  }

  handleKeyUp(key: string): void {
    const action = KEY_MAP[key];
    if (action) this.pressed[action] = false;
  }

  getActions(): Actions {
    return { ...this.pressed };
  }

  bind(target: EventTarget): void {
    target.addEventListener('keydown', (e) => this.handleKeyDown((e as KeyboardEvent).key));
    target.addEventListener('keyup', (e) => this.handleKeyUp((e as KeyboardEvent).key));
  }
}
