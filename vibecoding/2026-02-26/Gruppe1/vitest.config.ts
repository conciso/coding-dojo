import { defineConfig } from 'vitest/config'

export default defineConfig({
  test: {
    exclude: ['e2e/**', 'node_modules/**'],
    coverage: {
      provider: 'v8',
      reporter: ['text', 'html', 'lcov', 'json'],
      reportsDirectory: 'coverage/unit',
      include: ['src/**/*.ts'],
    },
  },
})
