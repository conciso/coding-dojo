# Minesweeper

Ein klassisches Minesweeper-Spiel im Retro-Windows-95-Style, umgesetzt mit TypeScript und vanilla DOM (kein UI-Framework).

## Technologien

| Technologie | Version | Zweck |
|---|---|---|
| **Node.js** | v24 | Runtime |
| **TypeScript** | 5.9 | Typsichere Entwicklung (strict mode) |
| **Vite** | 7.3 | Build-Tool & Dev-Server |
| **Vitest** | 4.0 | Unit-Tests + Coverage (v8) |
| **Playwright** | 1.58 | End-to-End-Tests |

## Voraussetzungen

- Node.js v24 oder höher
- npm

## Installation

```bash
npm install
npx playwright install chromium   # Browser für E2E-Tests installieren
```

## Projekt starten

```bash
npm run dev
```

Der Dev-Server startet unter [http://localhost:5173](http://localhost:5173).

## Build

```bash
npm run build
```

## Tests

### Unit-Tests (Watch-Modus)

```bash
npm test
```

### Unit-Test-Coverage

```bash
npm run test:coverage
```

Erzeugt einen Coverage-Report für die Spiellogik (`src/game/`).

### E2E-Tests

```bash
npm run test:e2e
```

Führt die Playwright-Tests im Chromium-Browser aus.

### Kombinierte Coverage (Unit + E2E)

```bash
npm run test:coverage:all
```

Führt Unit- und E2E-Tests aus, mergt die Coverage-Daten und erzeugt einen kombinierten Report unter `coverage/combined/`. Dieser deckt sowohl die Spiellogik als auch die UI ab (>98% Statements, >92% Branches).
