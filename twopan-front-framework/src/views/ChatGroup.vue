<template>
  <div>
    <el-container>
      <!-- 侧边栏 -->
      <el-aside style="width:250px; min-height: 100vh;background-color: #001529; overflow-x:hidden">
        <sidebar></sidebar>
      </el-aside>
      <el-container>

        <el-header>


          <top-bar></top-bar>

        </el-header>
        <el-main>
          <div class="file-list" style="margin-top: 20px">
            <!-- <el-table :data="fileList" ref="dataTable" :row-key="row => row.id" @selection-change="handleSelectionChange"> -->
            <el-table :data="fileList" ref="dataTable">
              <el-table-column prop="id" label="日志ID"></el-table-column>
              <el-table-column prop="userId" label="用户id"></el-table-column>

              <el-table-column prop="createTime" label="修改日期"></el-table-column>
              <el-table-column label="操作日志" prop="content"></el-table-column>
            </el-table>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import axios from 'axios';
import sidebar from "@/components/sidebar";
import topBar from "@/components/topBar";
export default {
  data() {
    return {
      fileList: [],
      username:'',
      cnt:1,

    };
  },
  components: {
    sidebar,
    topBar,
  },
  methods: {
    getFileLogList() {

      
      
      const id = sessionStorage.getItem('departmentId').toString();//将部门id转为字符串型

      axios.get('/operation/list/' + id, {
        // params: {
        //   id: 1
        // }
      })
        .then(response => {
          var names = [];
          this.fileList = response.data.data;
          response.data.data.forEach(item => {
            const userId = item.userId;
            const userid=userId.toString();
            this.$axios.get("/user/"+userid).then(res => {



            }).catch(error => {
              // 处理错误
              this.$message.error(error);
            });

          });

          console.log(names);
          this.$message.success("日志获取成功");
        })
        .catch(error => {
          console.error('Request failed:', error.message);
        });
    },
   
  },
  mounted() {
   
       this.getFileLogList();//在 Vue 组件的 mounted() 钩子函数中调用 getFileLogList() 方法，在组件加载完成后自动获取数据并刷新表格
  }
      
  
};
</script>

<style>
* {
  box-sizing: border-box;
}

body {
  color: #333;
  font-size: 14px;
  margin: 0;
  padding: 0;
}

.el-aside {
  box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
}

.el-menu--inline .el-menu-item {
  background-color: #001529 !important;
  padding-left: 50px;
}

.el-menu-item:hover,
.el-submenu__title:hover {
  background-color: #000c17 !important;
  color: #fff !important;
}

.el-submenu__title:hover i {
  color: #fff !important;
}

.el-menu-item.is-active {
  background-color: #1890ff !important;

  margin: 4px;

}

.el-menu-item {
  height: 40px;
  line-height: 40px;
  margin: 4px;
}

.el-submenu__title {
  height: 40px;
  line-height: 40px;
}

.el-header {
  box-shadow: 2px 0 6px rgba(0, 21, 41, .35);

}</style>
