import { Board } from '../game/board'
import { Cell } from '../game/cell'

const NUMBER_COLORS: Record<number, string> = {
  1: '#0000ff',
  2: '#008000',
  3: '#ff0000',
  4: '#000080',
  5: '#800000',
  6: '#008080',
  7: '#000000',
  8: '#808080',
}

export class Renderer {
  private boardEl: HTMLElement
  private onCellClick: (row: number, col: number) => void
  private onCellRightClick: (row: number, col: number) => void

  constructor(
    boardEl: HTMLElement,
    onCellClick: (row: number, col: number) => void,
    onCellRightClick: (row: number, col: number) => void,
  ) {
    this.boardEl = boardEl
    this.onCellClick = onCellClick
    this.onCellRightClick = onCellRightClick
  }

  render(board: Board, gameOver: boolean): void {
    this.boardEl.innerHTML = ''
    this.boardEl.style.gridTemplateColumns = `repeat(${board.cols}, 30px)`
    this.boardEl.style.gridTemplateRows = `repeat(${board.rows}, 30px)`

    for (let r = 0; r < board.rows; r++) {
      for (let c = 0; c < board.cols; c++) {
        const cell = board.cells[r][c]
        const cellEl = document.createElement('div')
        cellEl.className = 'cell'
        cellEl.dataset.row = String(r)
        cellEl.dataset.col = String(c)

        this.applyCellStyle(cellEl, cell, gameOver)

        cellEl.addEventListener('click', () => this.onCellClick(r, c))
        cellEl.addEventListener('contextmenu', (e) => {
          e.preventDefault()
          this.onCellRightClick(r, c)
        })

        this.boardEl.appendChild(cellEl)
      }
    }
  }

  private applyCellStyle(el: HTMLElement, cell: Cell, gameOver: boolean): void {
    if (cell.isRevealed) {
      el.classList.add('revealed')
      if (cell.isMine) {
        el.textContent = '💣'
        el.classList.add('mine')
      } else if (cell.neighborMines > 0) {
        el.textContent = String(cell.neighborMines)
        el.style.color = NUMBER_COLORS[cell.neighborMines] ?? '#000'
      }
    } else if (cell.isFlagged) {
      el.textContent = '🚩'
    } else if (gameOver) {
      // keep unrevealed style
    }
  }
}
