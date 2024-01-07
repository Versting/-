<template>
  <div class="login-body">
    <div class="bg"></div>
    <div class="login-panel">
      <el-form :model="formData" :rules="rules" ref="formDataRef" class="login-register" @submit.prevent>
        <div class="login-title">兔兔云盘</div>
        <!-- 输入邮箱 -->
        <el-form-item prop="email">
          <el-input size="large" clearable placeholder="请输入邮箱" v-model.trim="formData.email" maxlength="150">
            <template slot="prefix">
              <span class="iconfont icon-account"></span>
            </template>
          </el-input>
        </el-form-item>
        <!-- 登录密码 -->
        <el-form-item prop="password">
          <el-input type="password" size="large" placeholder="请输入密码" v-model.trim="formData.password" show-password>
            <template slot="prefix">
              <span class="iconfont icon-password"></span>
            </template>
          </el-input>
        </el-form-item>

        <!-- 验证码 -->
        <!-- <el-form-item prop="checkcode">
          <div class="check-code-panel">
            <el-input size="large" placeholder="请输入验证码" v-model.trim="formData.checkcode">
              <template slot="prefix">
                <span class="iconfont icon-checkcode"></span>
              </template>
            </el-input>
            <img src="checkCodeUrl" class="check-code" @click="changeCheckCode(0)"> -->
            <!--src地址需要后端生成，值为checkCodeUrl先屏蔽，括号内为换验证码时传入的参数 -->
          <!-- </div>
        </el-form-item> -->


        <el-form-item>
          <div class="no-account">
            <!-- <a href="javascript:void(0)" class="a-link" @click="showResetPasswordDialog">忘记密码</a> -->
            <router-link to="/register" class="a-link">没有账号？去注册</router-link>
          </div>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button type="primary" class="op-btn" size="large" @click="handleSubmit">
            <span>登录</span>
          </el-button>
        </el-form-item>
      </el-form>      
    </div>

    <div >
    <!--忘记密码弹窗-->
    <el-dialog class="my-custom-dialog"  :close-on-click-modal="false" title="重设密码" v-model="dialogVisible"
        :visible.sync="dialogVisible" :width="dialogWidth">
        <el-form class="resetpsd-panel" :model="resetPasswordForm" :rules="resetPasswordRules" ref="resetPasswordFormRef"
           label-width="0">
          <el-form-item prop="email" aria-label="邮箱" :label-width="formLabelWidth" style="margin-left: 0px;">
            <el-input size="large" clearable placeholder="请输入邮箱" v-model.trim="resetPasswordForm.email" maxlength="150">
              <template slot="prefix">
                <span class="iconfont icon-account"></span>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password" :label-width="formLabelWidth" style="margin-left: 0px;">
            <el-input type="password" size="large" placeholder="请输入密码" v-model.trim="resetPasswordForm.password"
              show-password>
              <template slot="prefix">
                <span class="iconfont icon-password"></span>
              </template>
            </el-input>
          </el-form-item>
          <!-- 验证码 -->
          <el-form-item prop="emailcode" :label-width="formLabelWidth" style="margin-left: 0px;">
            <div class="send-email-panel">
              <el-input size="large" clearable placeholder="请输入邮箱验证码" v-model.trim="formData.emailCode" maxlength="150">
                <template slot="prefix">
                  <span class="iconfont icon-checkcode"></span>
                </template>
              </el-input>
              <el-button class="send-mail-btn" type="primary" size="lage">获取验证码</el-button>
            </div>
          </el-form-item>
        </el-form>
      </el-dialog>
  </div>
  </div>
</template>
    
<script>
  import axios from 'axios';
export default {
  data() {
    return {
      formData: {
        email:'',
        password:''
      },
      dialogVisible: false,
      dialogWidth: '30%',
      formLabelWidth: '120px',
      resetPasswordForm: {
        email: '',
        code: '',
        password: ''
      },
      
      rules: {
        email: [{ required: true, message: "请输入您的邮箱" },
        { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          // {
          //   pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,16}$/,
          //   message: '长度在8-16位，至少包含一个大写字母、一个小写字母和一个数字',
          //   trigger: ['blur', 'change'] 
          // }
        ]

      },
      //checkCodeUrl: "/api/checkCode",
    };
  },
  methods: {
    //待完善的功能
    // changeCheckCode(type) {
    //   this.checkCodeUrl = "/api/checkCode?type=" + type + "&time=" + new Date().getTime();
    // },
    // showResetPasswordDialog() {
    //   this.dialogVisible = true;
    // },
    // resetPassword() {
    //   // 处理重置密码逻辑
    //   // ...
    //   // 关闭对话框
    //   this.dialogVisible = false;
    // },
    handleSubmit() {

      this.$refs.formDataRef.validate((valid) => {
        if (valid) {
          // 发送登录请求
          axios.post('/user/login', {
            email: this.formData.email,
            password: this.formData.password},
            { // 设置局部请求头为undefined
            headers: {
                'Content-Type': undefined
            }
          })
            .then(response => {
              const { data } = response;
              if (response.data.code===1) {
                // 登录成功，保存用户信息和令牌到sessionStorage

                sessionStorage.setItem('id', data.data.id);
                sessionStorage.setItem('email', data.data.email);
                sessionStorage.setItem('user', data.data.username);
                sessionStorage.setItem('companyId', data.data.companyId);
                sessionStorage.setItem('departmentId', data.data.departmentId);
                sessionStorage.setItem('avatar', data.data.avatar);
                sessionStorage.setItem('role', data.data.role);
                sessionStorage.setItem('status', data.data.status);
                
                

                // 跳转到注册页面
                console.log(data);
                console.log(data.data.username);
                console.log(data.data.companyId);
                console.log(data.msg);
                this.$router.replace('/userInfo');
                this.$message.success("登录成功");
              } else {
                this.$message.error(data.msg);
              }
            })
            .catch(error => {
              console.error(error);
              this.$message.error('账号或密码错误');
            });
        } else {
          return false;
        }
      });
      
    },
  },
};
</script>
    
<style lang="scss" scoped>
.login-body {
  height: calc(100vh);
  background-size: cover;
  background: url("../assets/bg3.jpg") no-repeat center center/cover;
  display: flex;

  .bg {
    flex: 1;
    background-size: cover;
    background-position: center;
    background-size: 800px;
    background-repeat: no-repeat;
    background-image: url("../assets/透明底.png");
  }

  .login-panel {
    width: 430px;
    margin-right: 15%;
    /*margin-top: 10vh;*/
    margin-top: calc((100vh - 400px) / 2);

    .login-register {
      padding: 25px;
      background-color: #fff;
      border-radius: 5px;

      .login-title {
        text-align: center;
        font-size: 18px;
        font-weight: bold;
        margin-bottom: 20px;
      }

      .send-email-panel {
        display: flex;
        width: 100%;
        justify-content: space-between;

        .send-mail-btn {
          margin-left: 5px;
          height: auto;
        }
      }

      .rememberme-panel {
        width: 100%;
      }

      .no-account {
        width: 100%;
        display: flex;
        justify-content: space-between;
      }

      .op-btn {
        width: 100%;
      }
    }
  }

  .check-code-panel {
    width: 100%;
    display: flex;

    
  }



  /*忘记密码样式*/
  .my-custom-dialog {
  width: 1500px;
}

.resetpsd-panel {
  margin-top: 20px;
  text-align: left;
}


.el-form-item__content{
margin-left: 0px;
}
.my-custom-dialog .send-email-panel {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

.my-custom-dialog .send-email-panel .el-input {
  flex: 1;
  margin-right: 10px;
}

.my-custom-dialog .send-mail-btn {
  min-width: 100px;
}

}
</style>