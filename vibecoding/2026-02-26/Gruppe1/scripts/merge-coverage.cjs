const fs = require('fs')
const path = require('path')

// Merge two Istanbul coverage JSONs by picking the better coverage per file.
// When the same file appears in both reports with different statementMaps
// (common when V8 coverage comes from different tools), nyc merge produces
// garbled results. This script avoids that by using only one source per file.

const [unitPath, e2ePath, outPath] = process.argv.slice(2)

if (!unitPath || !e2ePath || !outPath) {
  console.error('Usage: node merge-coverage.cjs <unit.json> <e2e.json> <output.json>')
  process.exit(1)
}

const unit = JSON.parse(fs.readFileSync(unitPath, 'utf8'))
const e2e = JSON.parse(fs.readFileSync(e2ePath, 'utf8'))

function coveredRatio(coverageObj) {
  const s = coverageObj.s || {}
  const values = Object.values(s)
  if (values.length === 0) return 0
  const covered = values.filter(v => v > 0).length
  return covered / values.length
}

const merged = {}

// Add all unit coverage entries
for (const [filePath, data] of Object.entries(unit)) {
  merged[filePath] = data
}

// Add/override with E2E coverage entries
for (const [filePath, data] of Object.entries(e2e)) {
  if (!merged[filePath] || coveredRatio(data) > coveredRatio(merged[filePath])) {
    merged[filePath] = data
  }
}

fs.mkdirSync(path.dirname(outPath), { recursive: true })
fs.writeFileSync(outPath, JSON.stringify(merged, null, 2))
console.log(`Merged ${Object.keys(unit).length} unit + ${Object.keys(e2e).length} e2e entries → ${Object.keys(merged).length} files`)
