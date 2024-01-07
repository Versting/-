<!-- ShareDialog.vue -->

<template>
    <el-dialog :visible.sync="dialogVisible" title="分享文件">
      <!-- el-dialog 的内容 -->
      <p>文件名：{{ fileName }}</p>
      <p>分享链接：{{ fileUrl }}</p>
      <!-- 其他内容... -->
    </el-dialog>
  </template>
  
  <script>
  export default {
    props: {
      fileName: String,
      fileUrl: String,
      dialogVisible: Boolean,
    },
  };
  </script>
  