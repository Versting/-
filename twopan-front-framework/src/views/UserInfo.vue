<template>
  <div>
    <el-container>
      <el-aside style=" width:250px;min-height: 100vh;background-color: #001529;">
        <sidebar></sidebar>
      </el-aside>
      <el-container>
        <el-header>
          <top-bar></top-bar>
        </el-header>

        <el-main>
          <div class="RoundedRectangle">
            <div class="userInfo" style="display: flex;flex-direction: row;">
              <div class="userName" style="position: absolute;left: 30%;top:10%;font-size: 40px">{{ username }}</div>
              <router-link to="/changeName">
                <el-button style="position: absolute;left: 70%;top:12%;font-size: 15px">修改用户名</el-button>
              </router-link>
            </div>
            <div class="EnterpriseInfo" style="display: flex;flex-direction: row;">
              <div class="EnterpriseName" style="position: absolute;left: 10%;top:32%;font-size: 20px">{{
                  organization?organization:'填写公司与部门信息并通过审核后才能使用其它功能'
                }}
              </div>
              <router-link to="/changeOrg">
                <el-button v-if="noCompany" style="position: absolute;left: 70%;top:31.5%;font-size: 15px">
                  填写公司与部门ID
                </el-button>
              </router-link>

            </div>
            <div class="MailInfo" style="display: flex;flex-direction: row;">
              <div class="Mail" style="position: absolute;left: 10%;top:55%;font-size: 20px">{{ mail }}</div>
              <router-link to="/changeMail">
                <el-button style="position: absolute;left: 70%;top:53.5%;font-size: 15px">修改绑定邮箱</el-button>
              </router-link>
            </div>
            <div class="PassWord" style="display: flex;flex-direction: row;">
              <div class="Mail" style="position: absolute;left: 10%;top:75%;font-size: 20px">{{ passwordState }}</div>
              <router-link to="/changePassword">
                <el-button style="position: absolute;left: 70%;top:73.5%;font-size: 15px">修改密码</el-button>
              </router-link>
            </div>
            <user-avatar></user-avatar>
            <LogOut :width="'500px'" :height="'60px'" :left="'20%'" :top="'700px'"/>
          </div>
        </el-main>
      </el-container>

    </el-container>
  </div>
</template>

<script>
import UserAvatar from '@/components/userAvatar.vue';
import LogOut from "@/components/LogOut";
import sidebar from "@/components/sidebar";
import topBar from "@/components/topBar";

export default {
  name: 'Userinfo',
  components: {
    UserAvatar,
    LogOut,
    sidebar,
    topBar,
  },
  created() {
    // The created lifecycle hook is called when the component is created

    this.id = sessionStorage.getItem('id');
    // console.log(sessionStorage.getItem('role'));
    //
    this.loadInfo();
  },
  methods: {
    loadInfo() {
      this.$axios.get("/user/" + this.id).then(res => {
        // 处理响应
        const userData = res.data.data; // 假设响应包含用户数据
        // 根据需要更新组件的数据
        // this.id = userData.id;
        this.username = userData.username;
        // this.password = userData.password;
        this.mail = userData.email;
        // this.role = userData.role;

        //userData.status===1时用户不可修改
        if (userData.status === 1) {
          this.noCompany = false;
        }
        //导入并显示用户的所在公司与部门信息
        this.$axios.get("/company/" + userData.companyId).then(resp => {
          this.organization += resp.data.data.name;
          this.$axios.get("/department/" + userData.departmentId).then(resp => {
            this.organization += ' ' + resp.data.data.name;
          })
        });
      }).catch(error => {
        // 处理错误
        this.$message.error(error);
      });
    }
  },
  data() {
    return {
      id: '',
      username: '',
      mail: '',
      organization: '',
      passwordState: '已设置',
      noCompany: true,
    }
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

  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;

}

.RoundedRectangle {
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
</style>
