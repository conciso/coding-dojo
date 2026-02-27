import { describe, it, expect } from 'vitest'
import { createCell, Cell } from '../src/game/cell'

describe('Cell', () => {
  it('creates a default cell that is not a mine', () => {
    const cell = createCell()
    expect(cell.isMine).toBe(false)
    expect(cell.isRevealed).toBe(false)
    expect(cell.isFlagged).toBe(false)
    expect(cell.neighborMines).toBe(0)
  })

  it('creates a mine cell', () => {
    const cell = createCell(true)
    expect(cell.isMine).toBe(true)
    expect(cell.isRevealed).toBe(false)
    expect(cell.isFlagged).toBe(false)
    expect(cell.neighborMines).toBe(0)
  })

  it('can be revealed', () => {
    const cell = createCell()
    cell.isRevealed = true
    expect(cell.isRevealed).toBe(true)
  })

  it('can be flagged', () => {
    const cell = createCell()
    cell.isFlagged = true
    expect(cell.isFlagged).toBe(true)
  })

  it('can have neighborMines set', () => {
    const cell = createCell()
    cell.neighborMines = 3
    expect(cell.neighborMines).toBe(3)
  })

  it('can toggle flag on and off', () => {
    const cell = createCell()
    expect(cell.isFlagged).toBe(false)
    cell.isFlagged = true
    expect(cell.isFlagged).toBe(true)
    cell.isFlagged = false
    expect(cell.isFlagged).toBe(false)
  })
})
