<template>
  <div>
    <div
        style="display: flex; align-items: center; justify-content: space-between; margin-left: 10px;margin-top: 10px;">
      <el-breadcrumb separator-class="el-icon-arrow-right"
                     style="display: inline;font-size: 18px;transform: translate(0,-100%)">
        <el-breadcrumb-item style="font-weight:700;">{{ isManager ? '管理员' : '普通用户' }}</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentRouteName }}</el-breadcrumb-item>
      </el-breadcrumb>

      <el-dropdown placement="bottom">
        <div style="display: flex; align-items: center;" class="user-avatar">
          <img :src="patraitUrl" alt="" class="avatar-image">
          <el-dropdown-menu slot="dropdown">
            <router-link to="/userInfo" style="text-decoration: none;">
              <el-dropdown-item>个人信息</el-dropdown-item>
            </router-link>
            <router-link to="/changePassword" style="text-decoration: none;">
              <el-dropdown-item>修改密码</el-dropdown-item>
            </router-link>
            <router-link to="/" style="text-decoration: none;" @click="logOut">
              <el-dropdown-item>退出登录</el-dropdown-item>
            </router-link>

          </el-dropdown-menu>
        </div>
      </el-dropdown>
    </div>

  </div>
</template>

<script>
import router from "@/router";

export default {
  name: "topBar",
  data() {
    return {
      patraitUrl: '',
      id: '',
      currentRouteName: '',
      department_id: '',
      // role:'',
      isCEO: false,
      isStaff: true,
      isManager: false,
      departmentId: '',
    }
  },
  methods: {
    logOut() {
      // 用户退出登录时清除所有相关数据
      sessionStorage.removeItem('id');
      sessionStorage.removeItem('email');
      sessionStorage.removeItem('user');
      sessionStorage.removeItem('companyId');
      sessionStorage.removeItem('departmentId');
      sessionStorage.removeItem('avatar');
      sessionStorage.removeItem('role');
      sessionStorage.removeItem('status');
    },
    getCookies() {
      this.departmentId = sessionStorage.getItem('departmentId');
      const profile = sessionStorage.getItem('role');
      switch (profile) {
        case '1':
          this.isManager = false;
          break;
        case '2' :
          this.isManager = true;
          break;
        case '3' :
          this.isCEO = true;
          this.isManager = true;
          break;
      }
    },

    getPatrait() {
      this.$axios({
        method: 'get',
        url: `/user/preview?id=${this.id}`,
        responseType: 'blob', // 设置响应数据类型为二进制
      })
          .then(response => {
            // 创建一个 Blob 对象，该对象包含了文件内容
            const blob = new Blob([response.data], {type: response.headers['content-type']});

            // 创建一个临时的URL，用于预览
            this.patraitUrl = URL.createObjectURL(blob);

          })
          .catch(error => {
            console.error('Failed to load patrait:', error);
          });
    },
  },
  created() {
    this.currentRouteName = router.currentRoute.name;
    this.id = sessionStorage.getItem('id');
    this.getCookies();
    this.getPatrait();
  }
}
</script>

<style scoped>
.user-avatar {
  /*position: absolute;*/
  /*left: 7%;*/
  /*top: 7%;*/
  width: 80px;
  height: 80px;
  border-radius: 50%;
  overflow: hidden;
  background-color: #ccc; /* 设置默认背景颜色，根据需要调整 */
  z-index: 1;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  margin-right: 10px;
}

.avatar-image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 保持图片比例，裁剪超出部分 */
}

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