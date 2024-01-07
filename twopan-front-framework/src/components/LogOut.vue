<template>
  <el-row>
    <router-link to="/">
      <el-button type="danger" round class="logOutButton" :style="getStyle" @click="logOut">退 出 登 录</el-button>
    </router-link>
  </el-row>
</template>

<script>
export default {
  name: "LogOut",
  methods:{
    logOut(){
      // 用户退出登录时清除所有相关数据
      sessionStorage.removeItem('id');
      sessionStorage.removeItem('email');
      sessionStorage.removeItem('user');
      sessionStorage.removeItem('companyId');
      sessionStorage.removeItem('departmentId');
      sessionStorage.removeItem('avatar');
      sessionStorage.removeItem('role');
      sessionStorage.removeItem('status');
    }
  },
  props: {
    width: {
      type: String,
      default: "100px" // 默认宽度
    },
    height: {
      type: String,
      default: "50px" // 默认高度
    },
    left: {
      type: String,
      default: "0" // 默认左边距
    },
    top: {
      type: String,
      default: "0" // 默认上边距
    }
  },
  computed: {
    getStyle() {
      return {
        width: this.width,
        height: this.height,
        left: this.left,
        top: this.top,
        fontWeight: 'bold',
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.3)',
        position:'absolute',
      };
    }
  }
};
</script>


<style scoped>
</style>