import { test as testBase } from '@playwright/test'
import { addCoverageReport } from 'monocart-reporter'

const test = testBase.extend({
  autoTestFixture: [async ({ page }, use) => {
    const isChromium = test.info().project.name === 'chromium'

    if (isChromium) {
      await page.coverage.startJSCoverage({ resetOnNavigation: false })
    }

    await use('autoTestFixture')

    if (isChromium) {
      const jsCoverage = await page.coverage.stopJSCoverage()
      await addCoverageReport(jsCoverage, test.info())
    }
  }, {
    scope: 'test',
    auto: true,
  }],
})

export { test }
export { expect } from '@playwright/test'
