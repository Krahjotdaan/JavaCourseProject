module.exports = {
  testEnvironment: 'jsdom',
  transform: {
    '^.+\\.jsx?$': 'babel-jest',
    '^.+\\.js?$': 'babel-jest',
  },
  setupFilesAfterEnv: ['<rootDir>/src/test/setupTests.js'],
};