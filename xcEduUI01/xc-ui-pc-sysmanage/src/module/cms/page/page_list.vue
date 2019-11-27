<template>
  <div>
    <!--<el-button type="primary" v-on:click="query"  size="small">查询</el-button>-->
    <!--查询表单-->
    <el-form :model="queryParam">
      <el-select v-model="queryParam.siteId" placeholder="请选择站点" size="small">
        <el-option
          v-for="item in siteList"
          :key="item.siteId"
          :label="item.siteName"
          :value="item.siteId">
        </el-option>
      </el-select>
      <el-input v-model="queryParam.pageName" style="width: 150px" size="small" placeholder="页面名称"></el-input>
      <el-input v-model="queryParam.pageAliase" style="width: 150px" size="small" placeholder="页面别名"></el-input>
      <el-input v-model="queryParam.pageType" style="width: 180px" size="small" placeholder="页面类型(0:静态/1:动态)"></el-input>
      <el-button type="primary" v-on:click="query" size="small">查询</el-button>
      <router-link class="mui-tab-item" :to="{path:'/cms/page/add/', query: {
        page: this.params.page,
        siteId: this.queryParam.siteId
      }}">
        <el-button type="primary" size="small">新增页面</el-button>
      </router-link>
    </el-form>
    <el-table
      :data="list"
      stripe
      style="width: 100%">
      <el-table-column type="index" label="序号" width="50">
      </el-table-column>
      <el-table-column prop="pageName" label="页面名称">
      </el-table-column>
      <el-table-column prop="pageAliase" label="别名" width="120">
      </el-table-column>
      <el-table-column prop="pageType" label="页面类型" width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.pageType == '0' ? '静态' : scope.row.pageType == '1' ? '动态' : '未定义'}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="pageWebPath" label="访问路径" width="250">
      </el-table-column>
      <el-table-column prop="pagePhysicalPath" label="物理路径">
      </el-table-column>
      <el-table-column prop="pageCreateTime" label="创建时间">
      </el-table-column>
      <el-table-column label="操作" align="center" width="180">
        <template slot-scope="page">
          <el-button
            size="mini" type="primary"
            @click="pageEdit(page.row)">编辑
          </el-button>
          <el-button
            size="mini" type="danger"
            @click="pageDelete(page.row)">删除
          </el-button>
        </template>
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
                siteList: [],
                list: [],
                total: 50,
                params: {
                    page: 1,//页码
                    size: 5//每页显示个数
                },
                queryParam: {
                    siteId: '',
                    pageAliase: '',
                    pageName: '',
                    pageType: ''
                }
            }
        },
        methods: {
            //分页查询，接受page页码参数
            changePage: function (page) {
                this.params.page = page;
                this.query()
            },
            //分页查询
            query: function () {
                //this.queryParam.pageType = this.queryParam.pageType == "静态" ? 0 : (this.queryParam.pageType == "动态") ? 1 : "";
                cmsApi.page_list(this.params.page, this.params.size, this.queryParam).then((res) => {
                    console.log(res);
                    this.total = res.queryResult.total;
                    this.list = res.queryResult.list;
                })
            },
            //页面编辑
            pageEdit(row) {
                //alert(row.pageId);
                this.$router.push({
                    path: '/cms/page/edit/' + row.pageId, query: {
                        page: this.params.page,
                        siteId: this.queryParam.siteId
                    }
                });
            },
            //页面删除
            pageDelete(row) {
                //alert(row.pageId);
                this.$confirm("确认要删除吗？", "提示", {
                    type: "warning",
                }).then(() => {
                    cmsApi.page_del(row.pageId).then((res) => {
                        if (res.success) {
                            this.$message({
                                type: 'success',
                                message: '删除成功'
                            });
                        } else {
                            //删除失败
                            this.$message.error(res.message);
                        }
                    }).finally(() => {
                        //刷新页面
                        this.query();
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '删除已取消'
                    });
                });
            }

        },
        //页面渲染之前，将添加页面返回操作路由里面的数据拿出来进行页面的回显
        created() {
            //取出参数并赋值
            this.params.page = Number.parseInt(this.$route.query.page || 1);
            this.queryParam.siteId = this.$route.query.siteId || "";
        },
        //页面加载完毕后立即查询
        mounted() {
            //默认查询页面
            this.query();
            //初始化站点列表
            this.siteList = [
                {
                    siteId: '5a751fab6abb5044e0d19ea1',
                    siteName: '门户主站'
                },
                {
                    siteId: '102',
                    siteName: '测试站'
                }
            ]
        }
    }

</script>
​
