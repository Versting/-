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

            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
              <el-form-item label="旧密码" prop="oldPass">
                <el-input type="password" v-model="ruleForm.oldPass" autocomplete="off"></el-input>
              </el-form-item>

              <el-form-item label="新密码" prop="pass">
                <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="checkPass">
                <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" :disabled="ruleForm.isSubmitDisabled" @click="submitForm()">提交</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
              </el-form-item>
            </el-form>
            <img src="@/assets/图标.png" style="position: absolute;top: 65%;left: 0;right: 0;margin: 0 auto;width: 200px;height: auto;">
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import UserAvatar from '@/components/userAvatar.vue';
import LogOut from "@/components/LogOut";
import localUserInfo from "@/testData/localUserInfo.json";
import sidebar from "@/components/sidebar";
import topBar from "@/components/topBar";

export default {
  name: 'ChangePassword',
  components: {
    UserAvatar,
    LogOut,
    sidebar,
    topBar,
  },
  data() {
    var validatePass0 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入旧密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入新密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
        this.ruleForm.isSubmitDisabled=true;

      } else {
        callback();
        if(this.ruleForm.oldPass !== ''){
          this.ruleForm.isSubmitDisabled=false;
        }
      }
    };
    return {
      input: localUserInfo.username,
      localUserInfo:localUserInfo,
      formData: {},
      ruleForm: {
        pass: '',
        checkPass: '',
        oldPass: '',
        isSubmitDisabled:true,
        id:'',
      },
      rules: {
        oldPass: [
          { validator: validatePass0, trigger: 'change' }
        ],
        pass: [
          { validator: validatePass, trigger: 'change' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'change' }
        ],
      }
    }
  },
  methods:{
    open() {
      this.$message({
            message: '密码修改成功',
            type: 'success'
          }
      );
    },
    submitForm() {
      const form = new FormData();
      form.append('id', this.id);
      form.append('oldPassword', this.ruleForm.oldPass);
      form.append('newPassword', this.ruleForm.pass);
      form.append('newPasswordAgain', this.ruleForm.checkPass);

      this.$axios.put("/user/updatePassword",form
      ).then(res => {
        console.log(res.data);
        if (res.data.msg==='旧密码输入错误'){
          this.$message.error('旧密码输入错误');
        }
        else{
        this.$message({
                    message: '密码修改成功',
                    type: 'success'
                  }
              );
        }
      }).catch(error => {
        // 处理错误
        this.$message.error(error);
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
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
.demo-ruleForm{
  position: absolute;
  top: 25%;
  width: 600px;
}
.backButton{
  position: absolute;
  left: 5%;
  top: 5%;
  width: 60px;
  height: 60px;
}
</style>
