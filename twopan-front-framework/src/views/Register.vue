<template>
    <div class="login-body">
        <div class="bg"></div>
        <div class="login-panel">
            <el-form :model="formData" :rules="rules" ref="formDataRef" class="login-register"
                @submit.prevent="handleSubmit">
                <div class="login-title">兔兔云盘</div>
                <!-- 输入邮箱 -->
                <el-form-item prop="email">
                    <el-input size="large" clearable placeholder="请输入邮箱" v-model.trim="formData.email" maxlength="150">
                        <template slot="prefix">
                            <span class="iconfont icon-account"></span>
                        </template>
                    </el-input>
                </el-form-item>

                <!-- 邮箱验证码 -->
                <div>
                    <el-form-item prop="emailcode">
                        <div class="send-email-panel">
                            <el-input size="large" clearable placeholder="请输入邮箱验证码" v-model.trim="formData.emailCode"
                                maxlength="150">
                                <template slot="prefix">
                                    <span class="iconfont icon-checkcode"></span>
                                </template>
                            </el-input>
                            <el-button class="send-mail-btn" type="primary" size="large"
                                @click="sendEmailCode">获取验证码</el-button>
                        </div>
                    </el-form-item>

                    <!-- 注册密码 -->
                    <el-form-item prop="registerPassword">
                        <el-input type="password" size="large" show-password placeholder="请输入密码"
                            v-model.trim="formData.registerPassword">
                            <template slot="prefix">
                                <span class="iconfont icon-password"></span>
                            </template>
                        </el-input>
                    </el-form-item>
                    <!-- 确认密码 -->
                    <el-form-item prop="reRegisterPassword">
                        <el-input type="password" size="large" show-password placeholder="请再次确认密码"
                            v-model.trim="formData.reRegisterPassword">
                            <template slot="prefix">
                                <span class="iconfont icon-password"></span>
                            </template>
                        </el-input>
                    </el-form-item>
                </div>

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
                <!-- 转登录 -->
                <el-form-item>
                    <div class="no-account">
                        <router-link to="/" class="a-link">去登录？</router-link>
                    </div>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" class="op-btn" size="large" @click="handleSubmit">
                        <span>注册</span>
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {

    data() {
        var validatePass = (rule, value, callback) => {//验证两次密码是否一致
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.formData.registerPassword) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            formData: {},
            rules: {
                email: [{ required: true, message: "请输入您的邮箱" },
                { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                ],
                registerPassword: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    {
                        pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,16}$/,
                        message: '长度在8-16位，至少包含一个大写字母、一个小写字母和一个数字',
                        trigger: ['blur', 'change']
                    }
                ],
                reRegisterPassword: [
                    { validator: validatePass, trigger: ['blur', 'change'] }
                ],
            },
        };
    },
    methods: {

        sendEmailCode() {
            // 获取表单中的邮箱
            const email = this.formData.email;
            // 如果邮箱为空，给出提示
            if (!email) {
                this.$message.warning('请输入邮箱');
                return;
            }
            // 邮箱格式验证
            const emailRegex = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
            if (!emailRegex.test(email)) {
                this.$message.warning('邮箱格式不正确，请输入有效的邮箱地址');
                return;
            }
            // 发送请求
            // 使用axios库发送请求
            axios.post('/user/sendEmail', { email }, { // 设置局部请求头为undefined
                headers: {
                    'Content-Type': undefined
                }
            })
                .then(response => {
                    // 请求成功，显示成功提示
                    console.log(response.data);
                    console.log(email);
                    this.$message.success('验证码已发送到您的邮箱，请注意查收');
                })
                .catch(error => {
                    // 请求失败，显示错误提示
                    this.$message.error('发送验证码失败，请稍后重试');
                });
        },
        handleSubmit() {
            // 构造请求的URL和数据
            const url = '/user'; // 替换为你的后端注册API地址
            const data = {
                email: this.formData.email,
                password: this.formData.registerPassword,
                //confirmPassword: this.formData.ReregisterPassword,
                code: this.formData.emailCode
            };
            console.log(data);
            // 发送POST请求
            axios.post(url, {
                email: this.formData.email,
                password: this.formData.registerPassword,
                //confirmPassword: this.formData.ReregisterPassword,
                code: this.formData.emailCode
            })
                .then(response => {
                    // 请求成功，响应数据
                    if (response.data.code === 1) {
                        console.log(response.data);
                        this.$message.success('注册成功'); // 显示成功提示
                    }
                    else {
                        console.log(response.data);
                        this.$message.error(response.data.msg); // 显示失败提示
                    }



                })
                .catch(error => {
                    // 请求失败，处理错误

                    console.error(error);
                    this.$message.error('注册失败，请重试'); // 显示失败提示
                });
        }
    },
};
</script>

<style scoped>
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
        margin-top: calc((100vh - 500px) / 2);

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

        .check-code {
            margin-left: 5px;
            cursor: pointer;
            width: 200px;
        }
    }

    .resetpsd-panel {
        padding: 25px;
        border-radius: 5px;
        width: 430px;
    }

    .el-dialog__wrapper {
        padding: 0px;
        margin: 0px;
    }

}
</style>
