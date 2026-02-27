import { test, expect } from './fixtures'

test.describe('Minesweeper', () => {
  test.beforeEach(async ({ page }) => {
    await page.goto('/')
  })

  test('should load the page with correct title and visible elements', async ({ page }) => {
    await expect(page).toHaveTitle('Minesweeper')
    await expect(page.locator('#board')).toBeVisible()
    await expect(page.locator('#difficulty')).toBeVisible()
    await expect(page.locator('#reset')).toBeVisible()
    await expect(page.locator('#mine-count')).toBeVisible()
    await expect(page.locator('#timer')).toBeVisible()
  })

  test('should display initial board with 9x9 grid, mine counter 010, timer 000', async ({ page }) => {
    const cells = page.locator('#board .cell')
    await expect(cells).toHaveCount(81)

    // All cells should be unrevealed
    const revealedCells = page.locator('#board .cell.revealed')
    await expect(revealedCells).toHaveCount(0)

    await expect(page.locator('#mine-count')).toHaveText('010')
    await expect(page.locator('#timer')).toHaveText('000')
  })

  test('should reveal cells on click', async ({ page }) => {
    await page.locator('.cell[data-row="4"][data-col="4"]').click()

    const revealedCells = page.locator('#board .cell.revealed')
    expect(await revealedCells.count()).toBeGreaterThan(0)
  })

  test('should place a flag on right-click and decrement mine counter', async ({ page }) => {
    const cell = page.locator('.cell[data-row="0"][data-col="0"]')
    await cell.click({ button: 'right' })

    await expect(cell).toHaveText('🚩')
    await expect(page.locator('#mine-count')).toHaveText('009')
  })

  test('should remove a flag on second right-click and restore mine counter', async ({ page }) => {
    const cell = page.locator('.cell[data-row="0"][data-col="0"]')

    await cell.click({ button: 'right' })
    await expect(cell).toHaveText('🚩')
    await expect(page.locator('#mine-count')).toHaveText('009')

    await cell.click({ button: 'right' })
    await expect(cell).not.toHaveText('🚩')
    await expect(page.locator('#mine-count')).toHaveText('010')
  })

  test('should reset the board when reset button is clicked', async ({ page }) => {
    // Reveal some cells first
    await page.locator('.cell[data-row="4"][data-col="4"]').click()
    expect(await page.locator('#board .cell.revealed').count()).toBeGreaterThan(0)

    // Click reset
    await page.locator('#reset').click()

    // Board should be fully reset
    await expect(page.locator('#board .cell.revealed')).toHaveCount(0)
    await expect(page.locator('#mine-count')).toHaveText('010')
    await expect(page.locator('#timer')).toHaveText('000')
    await expect(page.locator('#status')).toHaveText('')
  })

  test('should switch to intermediate difficulty with 16x16 grid', async ({ page }) => {
    await page.locator('#difficulty').selectOption('intermediate')

    const cells = page.locator('#board .cell')
    await expect(cells).toHaveCount(256)
  })

  test('should show Game Over when a mine is hit', async ({ page }) => {
    // Click cells systematically until we hit a mine
    let gameOver = false
    for (let r = 0; r < 9 && !gameOver; r++) {
      for (let c = 0; c < 9 && !gameOver; c++) {
        const cell = page.locator(`.cell[data-row="${r}"][data-col="${c}"]`)
        // Skip already revealed or flagged cells
        if (await cell.evaluate(el => el.classList.contains('revealed'))) continue
        await cell.click()
        const statusText = await page.locator('#status').textContent()
        if (statusText === 'Game Over!') {
          gameOver = true
        }
      }
    }

    expect(gameOver).toBe(true)
    await expect(page.locator('#status')).toHaveText('Game Over!')
    await expect(page.locator('#reset')).toHaveText('😵')

    // All mines should be visible
    const mines = page.locator('#board .cell.mine')
    expect(await mines.count()).toBeGreaterThan(0)
  })

  test('should show You Win when all non-mine cells are revealed', async ({ page }) => {
    // First click to trigger mine placement (center cell is always safe)
    await page.locator('.cell[data-row="4"][data-col="4"]').click()

    // Read mine positions from exposed game state
    const minePositions = await page.evaluate(() => {
      const gs = (window as any).__gameState
      const mines: [number, number][] = []
      for (let r = 0; r < gs.board.rows; r++) {
        for (let c = 0; c < gs.board.cols; c++) {
          if (gs.board.cells[r][c].isMine) {
            mines.push([r, c])
          }
        }
      }
      return mines
    })

    const mineSet = new Set(minePositions.map(([r, c]) => `${r},${c}`))

    // Click all non-mine, non-revealed cells
    for (let r = 0; r < 9; r++) {
      for (let c = 0; c < 9; c++) {
        if (mineSet.has(`${r},${c}`)) continue
        const cell = page.locator(`.cell[data-row="${r}"][data-col="${c}"]`)
        const isRevealed = await cell.evaluate(el => el.classList.contains('revealed'))
        if (isRevealed) continue
        await cell.click()
      }
    }

    await expect(page.locator('#status')).toHaveText('You Win!')
    await expect(page.locator('#reset')).toHaveText('😎')
  })
})
