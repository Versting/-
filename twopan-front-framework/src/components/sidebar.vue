<template>
<div>
    <div style="height: 100px; color: white; justify-content: center; align-items: center; display: flex;margin-top: 10px;">
      <img src="@/assets/图标.png" alt="" style="width: 110px; height: 90px;"/>
      <div style="margin-left:5px;font-size:32px;" >TwTpan</div>
    </div>

    <el-menu  router background-color="#001529" text-color="rgba(255,255,255,0.65)" active-text-color="#fff" style="border: none ;user-select:none;" :default-active="$route.path">
      <el-menu-item v-if="isChecked" index="/mainpage">
        <template slot="title">
          <i class="el-icon-house"></i>
          <span>系统首页</span>
        </template>
      </el-menu-item>
      <el-menu-item v-if="isChecked" index="/chat">
        <template slot="title">
          <i class="el-icon-tickets"></i>
          <span>文件日志</span>
        </template>
      </el-menu-item>

      <el-menu-item index="/userinfo">
        <template slot="title">
          <i class="el-icon-user"></i>
          <span>个人信息</span>
        </template>
      </el-menu-item>
      <el-submenu v-if="isManager">
        <template slot="title">
          <i class="el-icon-more"></i>
          <span>管理员页面</span>
        </template>
        <el-menu-item v-if="isCEO" index="/manage">
          <template slot="title">
            <i class="el-icon-view"></i>
            <span>成员管理</span>
          </template>
        </el-menu-item>

        <el-menu-item index="/recycle">
          <template slot="title">
            <i class="el-icon-delete"></i>
            <span>回收站</span>
          </template>
        </el-menu-item>

      </el-submenu>

    </el-menu>

</div>
</template>

<script>

export default {
  name: "sidebar",
  data(){
    return{
      id:'',
      isCEO: false,
      isManager:false,
      isChecked:false,
    }
  },
  created() {
    this.id=sessionStorage.getItem('id');
    this.getCookies();
  },
  methods:{
    getCookies() {
      this.departmentId=sessionStorage.getItem('departmentId');
      if (sessionStorage.getItem('status')==='1'){
        this.isChecked=true;
      }
      const profile= sessionStorage.getItem('role');
      switch (profile) {
        case '1':
          this.isManager=false;
          break;
        case '2' :
          this.isManager=true;
          break;
        case '3' :
          this.isCEO = true;
          this.isManager=true;
          break;
      }
    },
  }
}
</script>

<style scoped>

</style>