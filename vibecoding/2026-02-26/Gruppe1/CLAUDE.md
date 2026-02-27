# CLAUDE.md

## Projekt

Minesweeper-Spiel in TypeScript (vanilla DOM, kein Framework). Vite als Build-Tool.

## Befehle

```bash
npm run dev              # Vite Dev-Server (http://localhost:5173)
npm run build            # tsc && vite build
npm test                 # Vitest im Watch-Modus
npm run test:coverage    # Unit-Coverage (nur src/game/)
npm run test:e2e         # Playwright E2E-Tests (Chromium)
npm run test:coverage:all # Kombinierte Coverage: Unit + E2E → merged report
```

## Projektstruktur

```
src/
├── game/              # Spiellogik (reine Logik, kein DOM)
│   ├── cell.ts        # Cell-Interface + Factory
│   ├── board.ts       # Board-Klasse (Minen, Reveal, Flood-Fill)
│   └── game-state.ts  # GameState (Timer, Status, Schwierigkeit)
├── ui/                # DOM-basierte UI
│   ├── renderer.ts    # Board-Rendering (CSS Grid)
│   └── controls.ts    # Steuerung (Difficulty, Reset, Counter, Timer)
├── main.ts            # Entry-Point, verbindet Game + UI
└── style.css          # Retro Windows-95-Style

tests/                 # Vitest Unit-Tests (49 Tests)
e2e/                   # Playwright E2E-Tests (9 Tests)
├── fixtures.ts        # V8-Coverage-Fixture (monocart-reporter)
└── minesweeper.spec.ts
scripts/
└── merge-coverage.cjs # Mergt Unit- und E2E-Coverage (Istanbul JSON)
```

## Architektur

- **Game-Logik** (`src/game/`) ist komplett DOM-frei und direkt unit-testbar
- **UI** (`src/ui/`, `src/main.ts`) wird nur durch E2E-Tests abgedeckt
- `window.__gameState` wird für E2E-Tests exponiert (Minenpositionen auslesen)

## Testing

- **Unit-Tests** (Vitest + v8): Decken `src/game/` ab (~99% Coverage)
- **E2E-Tests** (Playwright + monocart-reporter): Decken `src/ui/` und `src/main.ts` ab
- **Coverage-Merge**: `scripts/merge-coverage.cjs` wählt pro Datei die bessere Coverage-Quelle (vermeidet Istanbul-StatementMap-Konflikte zwischen Vitest und monocart)
- **Kombinierte Coverage**: >98% Statements, >92% Branches

## Konventionen

- TypeScript strict mode
- Keine externen Runtime-Dependencies
- Node v24 (siehe `.nvmrc`)
- ESM (`"type": "module"` in package.json)
