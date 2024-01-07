<template>
  <div>
    <el-container>
        <el-aside style="width: 250px; min-height: 100vh;background-color: #001529;">
          <sidebar></sidebar>
        </el-aside>
      <el-container>
        
        <el-header>

          <top-bar></top-bar>


        </el-header>
        <el-main>
          <div ref="userCardContainer" style=" display: flex;flex-wrap: wrap;"></div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import userCard from "@/components/userCard";
import Vue from "vue";
import sidebar from "@/components/sidebar";
import topBar from "@/components/topBar";

export default {
  name: 'userManage',

  components: {
    userCard,
    sidebar,
    topBar,
  },

  data(){
    return{
      id:1,
      companyId:'1',
    }
  },

  created() {
    // The created lifecycle hook is called when the component is created
    this.loadInfo();
  },

  methods: {

    loadInfo() {
      this.$axios.get("/user/list/"+this.companyId).then(res => {
        const userData = res.data.data;
        var company_name,department_name;

        userData.forEach(user=>{
          // console.log(user.id,user.username);
          this.$axios.get("/company/" + user.companyId).then(resp => {
            company_name = resp.data.data.name;
            this.$axios.get("/department/" + user.departmentId).then(resp => {
              department_name =  resp.data.data.name;

              const container = this.$refs.userCardContainer;

              // 创建 userCard 组件的子类
              const userCardComponent = Vue.extend(userCard);
              // 创建组件实例并传递参数
              // console.log(user.status === 0 ? '未通过' : (user.status === 1 ? '通过' : ''));
              const userCardInstance = new userCardComponent({
                propsData: {
                  id: user.id,
                  username: user.username,
                  email:user.email,
                  companyName:company_name,
                  departmentName:department_name,
                  role:user.role,
                  status:user.status === 0 ? '未通过' : (user.status === 1 ? '通过' : -1),
                }
              });

              // 手动挂载实例
              userCardInstance.$mount();

              // 将组件的根元素添加到容器中
              container.appendChild(userCardInstance.$el);
            })
          });

        })
      }).catch(error => {
        // 处理错误
        this.$message.error(error);
      });
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
</style>
