<template>
  <div>
    <el-card class="box-card" shadow="hover">
      <div slot="header" class="clearfix">
        <span>用户信息</span>
      </div>
      <!--      {{ o +'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+labelsToValue(o)}}-->

      <div v-for="o in labels" :key="o" class="text item"
           style="text-align: left;display: flex;justify-content: space-between;text-align: left;">
        {{ o }}
        <div style=" text-align: right;" v-if="o === '状态:'">
          <el-select v-model="value"  placeholder="请选择" @change="handleSelectChange" >
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                :style="item.style"
            >
            </el-option>
          </el-select>
        </div>
        <div style=" text-align: right;" v-else>
          {{ labelsToValue(o) }}
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "userCard",
  data() {
    return {
      labels: [
        '用户ID:',
        '用户名:',
        '邮箱:',
        '所在公司:',
        '所在部门:',
        '职位:',
        '状态:',
      ],
      options: [{
        value: '1',
        label: '通过',
        style:'color: limegreen;',
      }, {
        value: '2',
        label: '未通过',
        style:'color: red;',
      },
      ],
      value: this.status,
    }
  },
  props: {
    id: {
      type: Number,  // 修改类型为 Number
      default: 1  // 默认宽度
    },
    username: {
      type: String,
      default: "0" // 默认上边距
    },
    companyName: {
      type: String,
      default: "0" // 默认高度
    },
    departmentName: {
      type: String,
      default: "0" // 默认左边距
    },
    email: {
      type: String,
      default: "0" // 默认上边距
    },
    role: {
      type: Number,
      default: 1 // 默认上边距
    },
    status: {
      type: Number,
      default: 0 // 默认上边距
    },
  },
  methods: {
    handleSelectChange(selectedValue) {
      const params = new FormData();
      params.append('id', this.id);
      if (selectedValue === '1') {
        console.log('选中了"通过"，值为1');
        // 在这里可以执行针对"通过"的逻辑
        params.append('status', 1);

      } else if (selectedValue === '2') {
        console.log('选中了"未通过"，值为2');
        // 在这里可以执行针对"未通过"的逻辑
        params.append('status', 0);
      }

      this.$axios.put("/user",params).then(res => {
        console.log(res.data.data);
      }).catch(error => {
        this.$message.error(error);
      });
    },
    labelsToValue(label) {
      if (label === '用户ID:') {
        return this.id;
      } else if (label === '用户名:') {
        return this.username;
      } else if (label === '邮箱:') {
        return this.email;
      } else if (label === '所在公司:') {
        return this.companyName;
      } else if (label === '所在部门:') {
        return this.departmentName;
      } else if (label === '职位:') {
        var roleLabel = '';
        if (this.role === 1) {
          roleLabel = '员工';
        } else if (this.role === 2) {
          roleLabel = '部门主管';
        } else if (this.role === 3) {
          roleLabel = 'CEO';
        }

        return roleLabel
      } else if (label === '状态:') {
        return this.status;

        // var statusLabel = '';

        //
        // if (this.status === 0) {
        //   statusLabel = '未通过';
        // } else if (this.status === 1) {
        //   statusLabel = '通过';
        // }

        // return statusLabel;
      }
    }
  },
  computed: {

    getUsersInfo() {
      return {
        companyName: this.companyName,
        departmentName: this.departmentName,
        email: this.email,
        id: this.id,
        password: this.password,
        role: this.role,
        status: this.status,
        username: this.username,
      };
    }
  }
}
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 480px;
  margin-top: 30px;
  margin-left: 30px;

}

/*.text:hover{*/
/*  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);*/
/*  !*border: 1px solid cornflowerblue;*!*/

/*  border-radius: 5px;*/
/*}*/

/*.el-select [name="my-select-"] {*/

/*  color: limegreen;*/

/*}*/
/*.el-select [name="my-select-2"] {*/

/*  color: red;*/

/*}*/
</style>