import { DIFFICULTIES, Difficulty } from '../game/game-state'

export interface ControlsCallbacks {
  onDifficultyChange: (difficulty: Difficulty) => void
  onReset: () => void
}

export class Controls {
  private timerEl: HTMLElement
  private mineCountEl: HTMLElement
  private statusEl: HTMLElement

  constructor(container: HTMLElement, callbacks: ControlsCallbacks) {
    const controlsEl = document.createElement('div')
    controlsEl.className = 'controls'

    // Difficulty selector
    const select = document.createElement('select')
    select.id = 'difficulty'
    for (const [key, diff] of Object.entries(DIFFICULTIES)) {
      const option = document.createElement('option')
      option.value = key
      option.textContent = diff.name
      select.appendChild(option)
    }
    select.addEventListener('change', () => {
      const difficulty = DIFFICULTIES[select.value]
      if (difficulty) callbacks.onDifficultyChange(difficulty)
    })

    // Reset button
    const resetBtn = document.createElement('button')
    resetBtn.id = 'reset'
    resetBtn.textContent = '🙂'
    resetBtn.addEventListener('click', () => callbacks.onReset())

    // Mine counter
    this.mineCountEl = document.createElement('span')
    this.mineCountEl.id = 'mine-count'
    this.mineCountEl.className = 'counter'

    // Timer
    this.timerEl = document.createElement('span')
    this.timerEl.id = 'timer'
    this.timerEl.className = 'counter'

    // Status
    this.statusEl = document.createElement('span')
    this.statusEl.id = 'status'
    this.statusEl.className = 'status'

    controlsEl.appendChild(this.mineCountEl)
    controlsEl.appendChild(select)
    controlsEl.appendChild(resetBtn)
    controlsEl.appendChild(this.statusEl)
    controlsEl.appendChild(this.timerEl)

    container.appendChild(controlsEl)
  }

  updateTimer(seconds: number): void {
    this.timerEl.textContent = String(seconds).padStart(3, '0')
  }

  updateMineCount(count: number): void {
    this.mineCountEl.textContent = String(count).padStart(3, '0')
  }

  updateStatus(status: 'playing' | 'won' | 'lost'): void {
    const resetBtn = document.getElementById('reset')
    if (status === 'won') {
      this.statusEl.textContent = 'You Win!'
      if (resetBtn) resetBtn.textContent = '😎'
    } else if (status === 'lost') {
      this.statusEl.textContent = 'Game Over!'
      if (resetBtn) resetBtn.textContent = '😵'
    } else {
      this.statusEl.textContent = ''
      if (resetBtn) resetBtn.textContent = '🙂'
    }
  }
}
