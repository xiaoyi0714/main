const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    // 自动打开浏览器
    open: false,
    port: 8080,
    proxy: {
      '/api': {
        target: `http://localhost:8081`,
        changeOrigin: true,//开启代理
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
})
