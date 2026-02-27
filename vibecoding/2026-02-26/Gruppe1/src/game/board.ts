import { Cell, createCell } from './cell'

export class Board {
  readonly rows: number
  readonly cols: number
  readonly totalMines: number
  cells: Cell[][]
  private minesPlaced = false

  constructor(rows: number, cols: number, mines: number) {
    this.rows = rows
    this.cols = cols
    this.totalMines = mines
    this.cells = this.createEmptyGrid()
  }

  private createEmptyGrid(): Cell[][] {
    const grid: Cell[][] = []
    for (let r = 0; r < this.rows; r++) {
      const row: Cell[] = []
      for (let c = 0; c < this.cols; c++) {
        row.push(createCell())
      }
      grid.push(row)
    }
    return grid
  }

  placeMines(safeRow: number, safeCol: number): void {
    if (this.minesPlaced) return

    const positions: [number, number][] = []
    for (let r = 0; r < this.rows; r++) {
      for (let c = 0; c < this.cols; c++) {
        if (r === safeRow && c === safeCol) continue
        positions.push([r, c])
      }
    }

    // Fisher-Yates shuffle
    for (let i = positions.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1))
      ;[positions[i], positions[j]] = [positions[j], positions[i]]
    }

    const minePositions = positions.slice(0, this.totalMines)
    for (const [r, c] of minePositions) {
      this.cells[r][c].isMine = true
    }

    this.calculateNeighborMines()
    this.minesPlaced = true
  }

  private calculateNeighborMines(): void {
    for (let r = 0; r < this.rows; r++) {
      for (let c = 0; c < this.cols; c++) {
        if (this.cells[r][c].isMine) continue
        let count = 0
        for (const [nr, nc] of this.getNeighbors(r, c)) {
          if (this.cells[nr][nc].isMine) count++
        }
        this.cells[r][c].neighborMines = count
      }
    }
  }

  getNeighbors(row: number, col: number): [number, number][] {
    const neighbors: [number, number][] = []
    for (let dr = -1; dr <= 1; dr++) {
      for (let dc = -1; dc <= 1; dc++) {
        if (dr === 0 && dc === 0) continue
        const nr = row + dr
        const nc = col + dc
        if (nr >= 0 && nr < this.rows && nc >= 0 && nc < this.cols) {
          neighbors.push([nr, nc])
        }
      }
    }
    return neighbors
  }

  revealCell(row: number, col: number): Cell[] {
    const cell = this.cells[row][col]
    if (cell.isRevealed || cell.isFlagged) return []

    if (!this.minesPlaced) {
      this.placeMines(row, col)
    }

    cell.isRevealed = true
    const revealed: Cell[] = [cell]

    if (cell.isMine) return revealed

    // Flood-fill for cells with 0 neighbor mines
    if (cell.neighborMines === 0) {
      for (const [nr, nc] of this.getNeighbors(row, col)) {
        revealed.push(...this.revealCell(nr, nc))
      }
    }

    return revealed
  }

  toggleFlag(row: number, col: number): boolean {
    const cell = this.cells[row][col]
    if (cell.isRevealed) return false
    cell.isFlagged = !cell.isFlagged
    return true
  }

  get flagCount(): number {
    let count = 0
    for (let r = 0; r < this.rows; r++) {
      for (let c = 0; c < this.cols; c++) {
        if (this.cells[r][c].isFlagged) count++
      }
    }
    return count
  }

  get revealedCount(): number {
    let count = 0
    for (let r = 0; r < this.rows; r++) {
      for (let c = 0; c < this.cols; c++) {
        if (this.cells[r][c].isRevealed) count++
      }
    }
    return count
  }

  get totalNonMineCells(): number {
    return this.rows * this.cols - this.totalMines
  }

  revealAllMines(): void {
    for (let r = 0; r < this.rows; r++) {
      for (let c = 0; c < this.cols; c++) {
        if (this.cells[r][c].isMine) {
          this.cells[r][c].isRevealed = true
        }
      }
    }
  }
}
