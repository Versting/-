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
            <el-button type="primary" style="margin: 20px 20px auto auto" class="submitButton" @click="open();changeName(input)">确认更改</el-button>


            <el-input v-model="input" maxlength="16" size="medium" show-word-limit placeholder="请输入用户名" class="textArea"></el-input>
            <img src="@/assets/图标.png" style="position: absolute;top: 50%;left: 0;right: 0;margin: 0 auto;width: 200px;height: auto;">
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

import sidebar from "@/components/sidebar";
import topBar from "@/components/topBar";

export default {
  name: 'ChangeName',
  components: {
    sidebar,
    topBar,
  },
  data() {
    return {
      input:'',
      id:'',
      username:'',
    }
  },
  methods:{
    open() {
      this.$message({
            message: '用户名修改成功',
            type: 'success'
          }
      );
    },
    loadInfo() {
      this.$axios.get("/user/"+this.id).then(res => {
        console.log(res.data);
        console.log(res.data.data);
        // 处理响应
        const userData = res.data.data; // 假设响应包含用户数据
        // 根据需要更新组件的数据
        this.input = userData.username;
      }).catch(error => {
        // 处理错误
        this.$message.error(error);
      });
    },
    changeName(userInput){
      this.username=userInput;
      // console.log(this.userInfomation);

      const params = new FormData();
      params.append('id', this.id);
      params.append('username', this.username);

      this.$axios.put("/user",params).then(res => {
        console.log(res.data.data);
      }).catch(error => {
        this.$message.error(error);
      });
    }
  },
  created() {
    // The created lifecycle hook is called when the component is created
    this.id=sessionStorage.getItem('id');//从session中获取当前登录用户的id
    this.loadInfo(); // Call your method here
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
.textArea{
  position: absolute;
  left: 5%;
  top: 25%;
  width:500px;
}
.submitButton{
  position: absolute;
  right: 10%;
  top: 21.5%;

}

</style>
