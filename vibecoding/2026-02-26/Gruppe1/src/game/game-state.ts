import { Board } from './board'

export type GameStatus = 'playing' | 'won' | 'lost'

export interface Difficulty {
  name: string
  rows: number
  cols: number
  mines: number
}

export const DIFFICULTIES: Record<string, Difficulty> = {
  beginner: { name: 'Beginner', rows: 9, cols: 9, mines: 10 },
  intermediate: { name: 'Intermediate', rows: 16, cols: 16, mines: 40 },
  expert: { name: 'Expert', rows: 16, cols: 30, mines: 99 },
}

export class GameState {
  board: Board
  status: GameStatus = 'playing'
  elapsedSeconds = 0
  private timerInterval: ReturnType<typeof setInterval> | null = null
  private onTimerTick: (() => void) | null = null

  constructor(difficulty: Difficulty) {
    this.board = new Board(difficulty.rows, difficulty.cols, difficulty.mines)
  }

  revealCell(row: number, col: number): void {
    if (this.status !== 'playing') return

    const revealed = this.board.revealCell(row, col)
    if (revealed.length === 0) return

    this.startTimer()

    const hitMine = revealed.some((cell) => cell.isMine)
    if (hitMine) {
      this.status = 'lost'
      this.board.revealAllMines()
      this.stopTimer()
      return
    }

    if (this.board.revealedCount === this.board.totalNonMineCells) {
      this.status = 'won'
      this.stopTimer()
    }
  }

  toggleFlag(row: number, col: number): void {
    if (this.status !== 'playing') return
    this.board.toggleFlag(row, col)
    this.startTimer()
  }

  get remainingMines(): number {
    return this.board.totalMines - this.board.flagCount
  }

  setTimerCallback(callback: () => void): void {
    this.onTimerTick = callback
  }

  private startTimer(): void {
    if (this.timerInterval !== null) return
    this.timerInterval = setInterval(() => {
      this.elapsedSeconds++
      this.onTimerTick?.()
    }, 1000)
  }

  stopTimer(): void {
    if (this.timerInterval !== null) {
      clearInterval(this.timerInterval)
      this.timerInterval = null
    }
  }

  reset(difficulty: Difficulty): void {
    this.stopTimer()
    this.board = new Board(difficulty.rows, difficulty.cols, difficulty.mines)
    this.status = 'playing'
    this.elapsedSeconds = 0
  }
}
