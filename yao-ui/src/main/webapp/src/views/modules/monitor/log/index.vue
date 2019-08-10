<template>
    <div class="app-container">
        <!--表格渲染-->
        <el-table v-loading="pageLoading" :data="pageInfo.records" size="small" style="width: 100%;">
            <el-table-column prop="c" label="用户名"></el-table-column>
            <el-table-column prop="requestIp" label="IP"></el-table-column>
            <el-table-column prop="description" label="描述"></el-table-column>
            <el-table-column :show-overflow-tooltip="true" prop="method" label="方法名称"></el-table-column>
            <el-table-column :show-overflow-tooltip="true" prop="params" label="参数"></el-table-column>
            <el-table-column prop="time" label="请求耗时" align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.time <= 300">{{ scope.row.time }}ms</el-tag>
                    <el-tag v-else-if="scope.row.time <= 1000" type="warning">{{ scope.row.time }}ms</el-tag>
                    <el-tag v-else type="danger">{{ scope.row.time }}ms</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建日期" width="180px">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.createTime) }}</span>
                </template>
            </el-table-column>
        </el-table>
        <!--分页组件-->
        <el-pagination
                :total="pageInfo.total"
                :current-page="pageInfo.current"
                style="margin-top: 8px;"
                layout="total, prev, pager, next, sizes"
                @size-change="sizeChange"
                @current-change="pageChange">
        </el-pagination>
    </div>
</template>

<script>
    import page from '@/mixins/page'
    import { parseTime } from '@/utils/index'
    export default {
        mixins: [page],
        created() {
            this.$nextTick(() => {
                this.page()
            })
        },
        methods: {
            parseTime,
            pageInit() {
                this.pageUrl = 'log/log/page';
                this.pageInfo.descs = ['create_time'];
                this.pageInfo.params = {
                    username: this.user.username
                };
                return true
            }
        }
    }
</script>

<style scoped>

</style>
