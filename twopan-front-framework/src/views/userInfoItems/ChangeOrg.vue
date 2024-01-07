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
          <div class="RoundedRectangle">
            <router-link to="/userInfo">
              <el-button icon="el-icon-arrow-left" circle class="backButton"></el-button>
            </router-link>
            <el-button type="primary" style="margin: 20px 20px auto auto" class="submitButton" @click="changeOrg()">确认更改</el-button>

            <el-input v-model="inputCompanyId" size="medium" placeholder="请输入公司ID" class="changeCompanyName" type="number"></el-input>
            <el-input v-model="inputDepartmentId" size="medium" placeholder="请输入部门ID" class="changeDepartmentName" type="number"></el-input>
            <img src="@/assets/图标.png" style="position: absolute;top: 50%;left: 0;right: 0;margin: 0 auto;width: 200px;height: auto;">
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import UserInfo from "@/views/UserInfo.vue";
import sidebar from "@/components/sidebar";
import topBar from "@/components/topBar";

export default {
  name: 'ChangeOrg',
  components: {
    sidebar,
    topBar,
  },
  data() {
    return {
      inputCompanyId: '',
      inputDepartmentId: '',
      id:'',
    }
  },
  methods:{
    changeOrg(){
      const params = new FormData();

      params.append('companyId', this.inputCompanyId);
      params.append('departmentId', this.inputDepartmentId);
      // params.append('email', this.inputDepartmentName);
      params.append('id', this.id);

      this.$axios.put("/user",params).then(res => {
        this.$message({
              message: '公司部门修改成功',
              type: 'success'
            }
        );
      }).catch(error => {
        // 处理错误
        this.$message.error(error);
      });
    },
  },
  created() {
    // The created lifecycle hook is called when the component is created
    this.id=sessionStorage.getItem('id');//从session中获取当前登录用户的id
  },
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

  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  /*width: 100%;*/
  /*height:100%;*/
  /*display: flex;*/
  /*position: relative;*/
}
.el-aside {
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
}
.el-menu--inline .el-menu-item {
  background-color: #001529 !important;
  padding-left: 50px;
}
.el-menu-item:hover, .el-submenu__title:hover{
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
  box-shadow: 2px 0 6px rgba(0,21,41,.35);

}
.RoundedRectangle{
  display: inline-block;
  margin: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 800px;
  height: 600px;
  position: absolute;
  left: 30%;
  top: 15%;
  border-radius: 20px;
}
/*本页面css*/
.backButton{
  position: absolute;
  left: 5%;
  top: 5%;
  width: 60px;
  height: 60px;
}
.changeCompanyName{
  position: absolute;
  left: 5%;
  top: 25%;
  width:500px;
}
.changeDepartmentName{
  position: absolute;
  left: 5%;
  top: 35%;
  width:500px;
}
.submitButton{
  position: absolute;
  right: 10%;
  top: 31.5%;

}

</style>
