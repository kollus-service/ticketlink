const path = require('path')
module.exports = {
    outputDir: path.resolve(__dirname, "../" + "main/resources/static"),
    devServer: {
        disableHostCheck: true,
        proxy: {
            '/api': {
                target: 'http://localhost:9000',
                ws: true,
                changeOrigin: true
            }
        }
    }
}
