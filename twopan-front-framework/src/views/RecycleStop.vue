<template>
  <div>
    <el-container>
      <el-aside style="width:250px; min-height: 100vh;background-color: #001529; overflow-x:hidden">
        <sidebar></sidebar>
      </el-aside>
      <el-container>

        <el-header>


          <top-bar></top-bar>

        </el-header>
        <el-main>
          <!--主体部分 -->
          <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column label="名称" prop="name" width="180"></el-table-column>
            <el-table-column prop="size" label="大小" width="180">
              <template slot-scope="scope">
                {{ formatBytes(scope.row.size) }}
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="360"></el-table-column>
            <el-table-column label="操作" width="180">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="restoreFile(scope.row.id)">恢复</el-button>
                <!-- el-button type="danger" size="mini" @click="deleteFile(scope.row.id)">清除</el-button>-->
              </template>
            </el-table-column>
          </el-table>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

import axios from "axios";
import sidebar from "@/components/sidebar";
import topBar from "@/components/topBar";

export default {
  components: {
    sidebar,
    topBar,
  },
  data() {
    return {
      tableData:
          [
            /*
          {id: 1, name: "文件1", size: 1233535, createTime: "2023-12-12 00:00:00",url:gdhsgdhsgdsad.jpg},
          {id: 2, name: "文件2", size: "2MB", deleteTime: "2023-12-23 11:45:14"},
          {id: 3, name: "文件3", size: "4KB", deleteTime: "2023-11-09 16:23:33"},
          {id: 4, name: "文件4", size: "9KB", deleteTime: "2023-11-09 16:23:33"},
          {id: 5, name: "文件5", size: "267KB", deleteTime: "2023-11-09 16:23:33"},
          {id: 6, name: "文件6", size: "376KB", deleteTime: "2023-11-09 16:23:33"},
          {id: 7, name: "文件7", size: "322KB", deleteTime: "2023-11-09 16:23:33"},
          {id: 8, name: "文件8", size: "89KB", deleteTime: "2023-11-09 16:23:33"}*/
          ]
    }
  },
  methods: {
    restoreFile(id) {
      console.log("恢复文件" + id);

      this.$confirm("确定要恢复此文件？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        axios.post(`/file/recovery/${id}`, {}).then(response => {
          console.log(response.data);
        }).catch(error => {
          console.error(error)
        })
        /*
        * 恢复文件的逻辑
        * */
        this.tableData = this.tableData.filter(item => item.id !== id)
        this.getData();

        this.$message({
          type: "success",
          message: "恢复成功",
        })
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消恢复"
        })
      })
    },
    /* deleteFile(id) {
       console.log("删除文件" + id);
       this.$confirm("确定要彻底清除该文件？（无法恢复）", " 提示", {
         confirmButtonText: "确定",
         cancelButtonText: "取消",
         type: "warning"
       }).then(() => {
         this.$message({
           type: "success",
           message: "删除成功"
         })
         /!*
         * 删除文件的逻辑
         * *!/
         this.tableData = this.tableData.filter(item => item.id !== id);
       }).catch(() => {
         this.$message({
           type: "info",
           message: "已取消删除"
         })
       })
     },*/

    getData() {
      //  const company="0";
      const company = sessionStorage.getItem('departmentId') + "";

      axios.get(`/file/recycle?company=${company}`)
          .then(response => {
            //console.log(response.data);
            if (response.data.code === 1) {
              console.log(response.data)
              this.tableData = response.data.data;
            } else {
              this.$alert(response.data.msg)
              console.log(response.data)
            }
          }).catch(error => {
        console.error(error);
      })

    },

    formatBytes(bytes, decimals = 2) {//转换文件大小的单位
      if (bytes === 0) return '0 B';
      const k = 1024;
      const sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
      const i = Math.floor(Math.log(bytes) / Math.log(k));
      return parseFloat((bytes / Math.pow(k, i)).toFixed(decimals)) + ' ' + sizes[i];
    },
/*
    formateNames(name, url) {
      const str = url.split(".");
      return name + '.' + str[str.length - 1];
    }*/
  },
  mounted() {
    this.getData();
  }
}
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

.el-menu-item:hover, .el-submenu__title:hover {
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

}
</style>
