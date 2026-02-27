import './style.css'
import { DIFFICULTIES, Difficulty, GameState } from './game/game-state'
import { Renderer } from './ui/renderer'
import { Controls } from './ui/controls'

let currentDifficulty: Difficulty = DIFFICULTIES.beginner
let gameState = new GameState(currentDifficulty)

const app = document.getElementById('app')!

const boardEl = document.createElement('div')
boardEl.id = 'board'

const controls = new Controls(app, {
  onDifficultyChange: (difficulty) => {
    currentDifficulty = difficulty
    resetGame()
  },
  onReset: () => resetGame(),
})

app.appendChild(boardEl)

const renderer = new Renderer(
  boardEl,
  (row, col) => {
    gameState.revealCell(row, col)
    updateUI()
  },
  (row, col) => {
    gameState.toggleFlag(row, col)
    updateUI()
  },
)

gameState.setTimerCallback(() => {
  controls.updateTimer(gameState.elapsedSeconds)
})

function updateUI(): void {
  const gameOver = gameState.status !== 'playing'
  renderer.render(gameState.board, gameOver)
  controls.updateMineCount(gameState.remainingMines)
  controls.updateTimer(gameState.elapsedSeconds)
  controls.updateStatus(gameState.status)
}

function resetGame(): void {
  gameState.stopTimer()
  gameState = new GameState(currentDifficulty)
  gameState.setTimerCallback(() => {
    controls.updateTimer(gameState.elapsedSeconds)
  })
  ;(window as any).__gameState = gameState
  updateUI()
}

// Expose game state for E2E tests
;(window as any).__gameState = gameState

updateUI()
