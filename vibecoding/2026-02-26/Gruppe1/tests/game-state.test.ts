import { describe, it, expect, beforeEach, afterEach, vi } from 'vitest'
import { GameState, DIFFICULTIES } from '../src/game/game-state'

describe('GameState', () => {
  let game: GameState

  beforeEach(() => {
    vi.useFakeTimers()
    game = new GameState(DIFFICULTIES.beginner)
  })

  afterEach(() => {
    game.stopTimer()
    vi.useRealTimers()
  })

  describe('initialization', () => {
    it('starts with playing status', () => {
      expect(game.status).toBe('playing')
    })

    it('starts with 0 elapsed seconds', () => {
      expect(game.elapsedSeconds).toBe(0)
    })

    it('creates a board with correct difficulty', () => {
      expect(game.board.rows).toBe(9)
      expect(game.board.cols).toBe(9)
      expect(game.board.totalMines).toBe(10)
    })

    it('has correct remaining mines count', () => {
      expect(game.remainingMines).toBe(10)
    })
  })

  describe('difficulties', () => {
    it('has beginner difficulty', () => {
      expect(DIFFICULTIES.beginner).toEqual({
        name: 'Beginner',
        rows: 9,
        cols: 9,
        mines: 10,
      })
    })

    it('has intermediate difficulty', () => {
      expect(DIFFICULTIES.intermediate).toEqual({
        name: 'Intermediate',
        rows: 16,
        cols: 16,
        mines: 40,
      })
    })

    it('has expert difficulty', () => {
      expect(DIFFICULTIES.expert).toEqual({
        name: 'Expert',
        rows: 16,
        cols: 30,
        mines: 99,
      })
    })
  })

  describe('revealCell', () => {
    it('reveals a cell and keeps playing status', () => {
      game.revealCell(0, 0)
      expect(game.board.cells[0][0].isRevealed).toBe(true)
      expect(game.status).toBe('playing')
    })

    it('does nothing when game is already lost', () => {
      game.status = 'lost'
      game.revealCell(0, 0)
      expect(game.board.cells[0][0].isRevealed).toBe(false)
    })

    it('does nothing when game is already won', () => {
      game.status = 'won'
      game.revealCell(0, 0)
      expect(game.board.cells[0][0].isRevealed).toBe(false)
    })

    it('sets status to lost when revealing a mine', () => {
      // Reveal first cell to trigger mine placement
      game.revealCell(0, 0)
      // Find a mine and reveal it
      for (let r = 0; r < game.board.rows; r++) {
        for (let c = 0; c < game.board.cols; c++) {
          if (game.board.cells[r][c].isMine && !game.board.cells[r][c].isRevealed) {
            game.revealCell(r, c)
            expect(game.status).toBe('lost')
            return
          }
        }
      }
    })

    it('sets status to won when all non-mine cells are revealed', () => {
      // Create a tiny board for easy win
      game = new GameState({ name: 'Test', rows: 2, cols: 2, mines: 1 })
      game.revealCell(0, 0) // safe cell, triggers mine placement

      // Reveal all non-mine cells
      for (let r = 0; r < game.board.rows; r++) {
        for (let c = 0; c < game.board.cols; c++) {
          if (!game.board.cells[r][c].isMine && !game.board.cells[r][c].isRevealed) {
            game.revealCell(r, c)
          }
        }
      }

      if (game.board.revealedCount === game.board.totalNonMineCells) {
        expect(game.status).toBe('won')
      }
    })

    it('reveals all mines when game is lost', () => {
      game.revealCell(0, 0) // place mines
      // Find and reveal a mine
      for (let r = 0; r < game.board.rows; r++) {
        for (let c = 0; c < game.board.cols; c++) {
          if (game.board.cells[r][c].isMine && !game.board.cells[r][c].isRevealed) {
            game.revealCell(r, c)
            break
          }
        }
        if (game.status === 'lost') break
      }

      // All mines should be revealed
      for (let r = 0; r < game.board.rows; r++) {
        for (let c = 0; c < game.board.cols; c++) {
          if (game.board.cells[r][c].isMine) {
            expect(game.board.cells[r][c].isRevealed).toBe(true)
          }
        }
      }
    })
  })

  describe('toggleFlag', () => {
    it('toggles flag on a cell', () => {
      game.toggleFlag(0, 0)
      expect(game.board.cells[0][0].isFlagged).toBe(true)
    })

    it('decreases remaining mines when flagging', () => {
      game.toggleFlag(0, 0)
      expect(game.remainingMines).toBe(9)
    })

    it('does nothing when game is not playing', () => {
      game.status = 'lost'
      game.toggleFlag(0, 0)
      expect(game.board.cells[0][0].isFlagged).toBe(false)
    })
  })

  describe('timer', () => {
    it('starts timer on first reveal', () => {
      const callback = vi.fn()
      game.setTimerCallback(callback)
      game.revealCell(0, 0)
      vi.advanceTimersByTime(3000)
      expect(game.elapsedSeconds).toBe(3)
      expect(callback).toHaveBeenCalledTimes(3)
    })

    it('starts timer on first flag', () => {
      game.toggleFlag(0, 0)
      vi.advanceTimersByTime(2000)
      expect(game.elapsedSeconds).toBe(2)
    })

    it('stops timer when game is lost', () => {
      game.revealCell(0, 0)
      vi.advanceTimersByTime(2000)
      // Find and click a mine
      for (let r = 0; r < game.board.rows; r++) {
        for (let c = 0; c < game.board.cols; c++) {
          if (game.board.cells[r][c].isMine && !game.board.cells[r][c].isRevealed) {
            game.revealCell(r, c)
            break
          }
        }
        if (game.status === 'lost') break
      }
      const timeAtLoss = game.elapsedSeconds
      vi.advanceTimersByTime(3000)
      expect(game.elapsedSeconds).toBe(timeAtLoss)
    })
  })

  describe('reset', () => {
    it('resets the game to initial state', () => {
      game.revealCell(0, 0)
      vi.advanceTimersByTime(5000)
      game.reset(DIFFICULTIES.beginner)

      expect(game.status).toBe('playing')
      expect(game.elapsedSeconds).toBe(0)
      expect(game.board.revealedCount).toBe(0)
    })

    it('can reset to a different difficulty', () => {
      game.reset(DIFFICULTIES.intermediate)
      expect(game.board.rows).toBe(16)
      expect(game.board.cols).toBe(16)
      expect(game.board.totalMines).toBe(40)
    })
  })
})
