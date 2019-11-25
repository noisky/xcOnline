<template>
  <div>
    <el-button type="primary" v-on:click="query"  size="small">查询</el-button>
    <el-table
      :data="list"
      stripe
      style="width: 100%">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="pageName" label="页面名称" width="120">
      </el-table-column>
      <el-table-column prop="pageAliase" label="别名" width="120">
      </el-table-column>
      <el-table-column prop="pageType" label="页面类型" width="150">
      </el-table-column>
      <el-table-column prop="pageWebPath" label="访问路径" width="250">
      </el-table-column>
      <el-table-column prop="pagePhysicalPath" label="物理路径" width="250">
      </el-table-column>
      <el-table-column prop="pageCreateTime" label="创建时间" width="180" >
      </el-table-column>
    </el-table>
    <el-pagination
      layout="prev, pager, next"
      :page-size="this.params.size"
      v-on:current-change="changePage"
      :total="total" :current-page="this.params.page" style="float:right;">
    </el-pagination>
  </div>
</template>
​
<script>
    import * as cmsApi from '../api/cms';
    export default {
        data() {
            return {
                list:[],
                total:50,
                params:{
                    page:1,//页码
                    size:5//每页显示个数
                }
            }
        },
        methods:{
            //分页查询，接受page页码参数
            changePage:function (page) {
                this.params.page = page;
                this.query()
            },
            //分页查询
            query:function () {
                cmsApi.page_list(this.params.page,this.params.size,this.params).then((res)=>{
                    console.log(res)
                    this.total = res.queryResult.total
                    this.list = res.queryResult.list
                })
            }
        },
        //页面加载完毕后立即查询
        mounted() {
            //默认查询页面
            this.query()
        }
    }

</script>
​
