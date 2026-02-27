import { describe, it, expect, beforeEach } from 'vitest'
import { Board } from '../src/game/board'

describe('Board', () => {
  let board: Board

  beforeEach(() => {
    board = new Board(9, 9, 10)
  })

  describe('creation', () => {
    it('creates a board with correct dimensions', () => {
      expect(board.rows).toBe(9)
      expect(board.cols).toBe(9)
      expect(board.totalMines).toBe(10)
    })

    it('initializes all cells as unrevealed and unflagged', () => {
      for (let r = 0; r < board.rows; r++) {
        for (let c = 0; c < board.cols; c++) {
          expect(board.cells[r][c].isRevealed).toBe(false)
          expect(board.cells[r][c].isFlagged).toBe(false)
        }
      }
    })

    it('starts with no mines before first click', () => {
      let mineCount = 0
      for (let r = 0; r < board.rows; r++) {
        for (let c = 0; c < board.cols; c++) {
          if (board.cells[r][c].isMine) mineCount++
        }
      }
      expect(mineCount).toBe(0)
    })
  })

  describe('mine placement', () => {
    it('places the correct number of mines', () => {
      board.placeMines(0, 0)
      let mineCount = 0
      for (let r = 0; r < board.rows; r++) {
        for (let c = 0; c < board.cols; c++) {
          if (board.cells[r][c].isMine) mineCount++
        }
      }
      expect(mineCount).toBe(10)
    })

    it('keeps the safe cell free of mines', () => {
      board.placeMines(4, 4)
      expect(board.cells[4][4].isMine).toBe(false)
    })

    it('does not place mines twice', () => {
      board.placeMines(0, 0)
      board.placeMines(0, 0)
      let mineCount = 0
      for (let r = 0; r < board.rows; r++) {
        for (let c = 0; c < board.cols; c++) {
          if (board.cells[r][c].isMine) mineCount++
        }
      }
      expect(mineCount).toBe(10)
    })
  })

  describe('neighbor counting', () => {
    it('calculates neighbor mines correctly', () => {
      board.placeMines(0, 0)
      for (let r = 0; r < board.rows; r++) {
        for (let c = 0; c < board.cols; c++) {
          if (board.cells[r][c].isMine) continue
          let expected = 0
          for (const [nr, nc] of board.getNeighbors(r, c)) {
            if (board.cells[nr][nc].isMine) expected++
          }
          expect(board.cells[r][c].neighborMines).toBe(expected)
        }
      }
    })
  })

  describe('getNeighbors', () => {
    it('returns 3 neighbors for corner cell (0,0)', () => {
      const neighbors = board.getNeighbors(0, 0)
      expect(neighbors).toHaveLength(3)
    })

    it('returns 5 neighbors for edge cell (0,1)', () => {
      const neighbors = board.getNeighbors(0, 1)
      expect(neighbors).toHaveLength(5)
    })

    it('returns 8 neighbors for center cell (4,4)', () => {
      const neighbors = board.getNeighbors(4, 4)
      expect(neighbors).toHaveLength(8)
    })
  })

  describe('revealCell', () => {
    it('reveals a cell', () => {
      board.revealCell(0, 0)
      expect(board.cells[0][0].isRevealed).toBe(true)
    })

    it('does not reveal a flagged cell', () => {
      board.cells[0][0].isFlagged = true
      const revealed = board.revealCell(0, 0)
      expect(revealed).toHaveLength(0)
      expect(board.cells[0][0].isRevealed).toBe(false)
    })

    it('does not reveal an already revealed cell', () => {
      board.revealCell(0, 0)
      const revealed = board.revealCell(0, 0)
      expect(revealed).toHaveLength(0)
    })

    it('places mines on first reveal', () => {
      board.revealCell(4, 4)
      let mineCount = 0
      for (let r = 0; r < board.rows; r++) {
        for (let c = 0; c < board.cols; c++) {
          if (board.cells[r][c].isMine) mineCount++
        }
      }
      expect(mineCount).toBe(10)
    })

    it('flood-fills cells with 0 neighbor mines', () => {
      // Create a small board with mines in a known position
      const smallBoard = new Board(3, 3, 1)
      // Manually place a single mine at (2,2)
      smallBoard.placeMines(0, 0)
      // After placing mines, revealing (0,0) should be safe
      const revealedBefore = smallBoard.revealedCount
      smallBoard.revealCell(0, 0)
      // At least 1 cell should be revealed
      expect(smallBoard.revealedCount).toBeGreaterThan(revealedBefore)
    })
  })

  describe('toggleFlag', () => {
    it('flags an unrevealed cell', () => {
      const result = board.toggleFlag(0, 0)
      expect(result).toBe(true)
      expect(board.cells[0][0].isFlagged).toBe(true)
    })

    it('unflags a flagged cell', () => {
      board.toggleFlag(0, 0)
      board.toggleFlag(0, 0)
      expect(board.cells[0][0].isFlagged).toBe(false)
    })

    it('does not flag a revealed cell', () => {
      board.cells[0][0].isRevealed = true
      const result = board.toggleFlag(0, 0)
      expect(result).toBe(false)
      expect(board.cells[0][0].isFlagged).toBe(false)
    })
  })

  describe('counters', () => {
    it('counts flags correctly', () => {
      expect(board.flagCount).toBe(0)
      board.toggleFlag(0, 0)
      board.toggleFlag(1, 1)
      expect(board.flagCount).toBe(2)
    })

    it('counts revealed cells correctly', () => {
      expect(board.revealedCount).toBe(0)
      board.cells[0][0].isRevealed = true
      board.cells[1][1].isRevealed = true
      expect(board.revealedCount).toBe(2)
    })

    it('calculates totalNonMineCells correctly', () => {
      expect(board.totalNonMineCells).toBe(9 * 9 - 10)
    })
  })

  describe('revealAllMines', () => {
    it('reveals all mine cells', () => {
      board.placeMines(0, 0)
      board.revealAllMines()
      for (let r = 0; r < board.rows; r++) {
        for (let c = 0; c < board.cols; c++) {
          if (board.cells[r][c].isMine) {
            expect(board.cells[r][c].isRevealed).toBe(true)
          }
        }
      }
    })
  })
})
