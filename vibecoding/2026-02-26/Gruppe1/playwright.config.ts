import { defineConfig } from '@playwright/test'
import path from 'path'

const rootDir = process.cwd()

export default defineConfig({
  testDir: './e2e',
  use: {
    baseURL: 'http://localhost:5173',
    launchOptions: {
      slowMo: Number(process.env.SLOWMO) || 0,
    },
  },
  projects: [
    {
      name: 'chromium',
      use: { browserName: 'chromium' },
    },
  ],
  reporter: [
    ['list'],
    ['monocart-reporter', {
      name: 'E2E Coverage Report',
      outputFile: 'coverage/e2e/report.html',
      coverage: {
        entryFilter: (entry: { url: string }) => entry.url.includes('/src/'),
        sourceFilter: () => true,
        sourcePath: (filePath: string, info: { distFile?: string }) => {
          // Map sourcemap-extracted filenames back to absolute paths
          // using distFile which has the form "localhost-5173/src/path/file.ts"
          if (info.distFile) {
            const srcIndex = info.distFile.indexOf('/src/')
            if (srcIndex !== -1) {
              return path.resolve(rootDir, info.distFile.slice(srcIndex + 1))
            }
          }
          // For direct entries (e.g. style.css), filePath is "localhost-5173/src/..."
          const srcIndex = filePath.indexOf('/src/')
          if (srcIndex !== -1) {
            return path.resolve(rootDir, filePath.slice(srcIndex + 1))
          }
          return filePath
        },
        reports: [
          ['json', { file: 'coverage-final.json' }],
          ['v8'],
          ['console-details'],
        ],
      },
    }],
  ],
  webServer: {
    command: 'npm run dev',
    url: 'http://localhost:5173',
    reuseExistingServer: !process.env.CI,
  },
})
