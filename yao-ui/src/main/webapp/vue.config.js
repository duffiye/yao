//基础路径 注意发布之前要先修改这里
//打包使用

// 基础路径 注意发布之前要先修改这里
let publicPath = '/'

module.exports = {
    publicPath,
    lintOnSave: true,
    devServer: {
        publicPath,
        disableHostCheck: true,
    },
    // 打包时不生成.map文件 避免看到源码
    productionSourceMap: false
}