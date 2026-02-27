export interface Cell {
  isMine: boolean
  isRevealed: boolean
  isFlagged: boolean
  neighborMines: number
}

export function createCell(isMine = false): Cell {
  return {
    isMine,
    isRevealed: false,
    isFlagged: false,
    neighborMines: 0,
  }
}
