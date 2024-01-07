<template>
    <div >
      <el-container>
          <!-- 侧边栏 -->
        <el-aside style="width:250px; min-height: 100vh;background-color: #001529; overflow-x:hidden">
          <sidebar></sidebar>
        </el-aside>
        <el-container>

          <el-header>


            <top-bar></top-bar>

          </el-header>
          <el-main style="display: flex; ">
            <el-main style="width:800px;margin-right: 10px;background-color:rgba(0,255,255,0.3);">
                  <div class="top">
                    <div class="top-op">
                      <div class="btn">

                        
                        <input type="file" id="fileInput"  @change="handleFileChange"  class="input-file-button"/>  
                        <!-- <label for="fileInput" ><i class="el-icon-upload el-icon--right"></i>选择本地文件</label> -->

                        <el-button type="primary" @click="uploadFile" class="el-icon-upload">
                          <span class="iconfont icon-del"></span>
                          点击上传
                        </el-button>
                        
                        
                      </div>



                      
                      <el-button type="success" @click="toggleSelection">
                        <span class="iconfont icon-del"></span>
                        {{ isSelectMode ? '取消选中' : '批量选中' }}
                      </el-button>

                      
                      <el-button type="danger" @click="deleteSelected">
                        <span class="iconfont icon-del"></span>
                        批量删除
                      </el-button>


                      
                      
                    </div>

                    <div v-show="uploading">
                      <p>上传进度: {{ progress }}%</p>
                      <el-progress :percentage="progress" />
                    </div>
                    
                  </div>
              
                  <div class="file-list file-list-show">
                   
                    
                    <el-table :data="fileList" ref="dataTable"  @selection-change="handleSelectionChange"
                      @row-click="handleRowClick" 
                      highlight-current-row>


                      <el-table-column type="selection" v-if="isSelectMode"></el-table-column>
                      <el-table-column label="文件名">
                        <template slot-scope="scope">
                          <!-- 将文件名作为超链接，点击时触发 previewFile 方法 -->
                          <el-link @click="previewFile(scope.row.id)" type="primary">{{ scope.row.name }}</el-link>
                          
                        </template>
                      </el-table-column>
                      <el-table-column prop="size" label="文件大小" :formatter="formatFileSize"></el-table-column>
                      <el-table-column prop="createTime" label="修改日期"></el-table-column>

                      <el-table-column label="操作">
                        <template slot-scope="scope">
                          <el-button @click="downloadFile(scope.row)" type="danger" size="mini" class="el-icon-download" plain></el-button>
                          <el-button @click="shareFile(scope.row)" type="primary" size="mini" class="el-icon-share" plain></el-button>
                          <el-button @click="deleteFile(scope.row.id)" type="danger" size="mini" class="el-icon-delete" plain></el-button>
                        </template>
                      </el-table-column>
                      

                      
                    </el-table>

                    
                    

                  </div>
                  <template>
                    <div>
                      
                      <!-- el-dialog 组件 -->
                      <el-dialog :visible.sync="dialogVisible" title="文件分享" width="30%">
                        <!-- 文件名和 URL 的显示 -->
                        <p>文件名: {{ fileName }}</p>
                        <p>URL: {{ shareUrl }}</p>

                        <!-- 复制按钮，点击触发复制事件 -->
                        <button @click="copyUrl">复制 URL</button>
                      </el-dialog>
                    </div>
                  </template>

              
            
            </el-main>
            <el-main style="background-color:yellowgreen">
            消息区域2
            </el-main>
          </el-main>
          
        </el-container>
      </el-container>
    </div>
  </template>
  
<script>
import sidebar from "@/components/sidebar";

function getCurrentDateTime() {
  const now = new Date();

  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');

  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  const seconds = String(now.getSeconds()).padStart(2, '0');

  const formattedDateTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  
  return formattedDateTime;
}



import axios from 'axios';
import topBar from "@/components/topBar";

export default {
  data() {
    return {
      isPass:false,
      isManager: false,
      isCEO: false,
      isstaff: true,
      isSelectMode: false,
      departmentId:'',
      userId: '',
      uploading: false,
      dialogVisible:false,
      progress: 0,

      patraitUrl: '',

      fileList: [
      // { id: 1, name: 'File 1', size: '10 MB', modifiedDate: '2023-01-01' ,url: '12345678'},
      //   { id: 2, name: 'File 2', size: '15 MB', modifiedDate: '2023-01-02' },
      //   { id: 3, name: 'File 3', size: '8 MB', modifiedDate: '2023-01-03' },
      ],
      selectedFiles: [],
      selectedFile: null,
      fileName: '',
      shareUrl: '',
      
      progress: 0,

      

      warehouse:'https://jkmzj.oss-cn-chengdu.aliyuncs.com/'
    };
  },
  components: {
    sidebar,
    topBar,
  },
  methods: {

    getCookies() {
     this.isPass=sessionStorage.getItem('status');



      const profile=sessionStorage.getItem('role');

      switch (profile) {
        case '2' :
          this.isManager = true;
          break;
        case '3' :
          this.isCEO = true;
          this.isManager = true;
          break;
        }

        console.log(this.isManager);
        //console.log(sessionStorage.getItem('id'));
      this.userId=sessionStorage.getItem('id');
        //console.log(this.userId);

      this.departmentId=sessionStorage.getItem('departmentId');

    },
    
    getPatrait(){
      axios({
        method: 'get',
        url: `/user/preview?id=${this.userId}`,

        responseType: 'blob', // 设置响应数据类型为二进制
      })
        .then(response => {
          // 创建一个 Blob 对象，该对象包含了文件内容
          const blob = new Blob([response.data], { type: response.headers['content-type'] });

          // 创建一个临时的URL，用于预览
          this.patraitUrl = URL.createObjectURL(blob);

          // 在新窗口中打开预览
          
        })
        .catch(error => {
          console.error('Failed to load patrait:', error);
        });
    },


    previewFile(id) {

      
      axios({
        method: 'get',
        url: `/file/preview?id=${id}`,
        responseType: 'blob', // 设置响应数据类型为二进制
      })
        .then(response => {
          // 创建一个 Blob 对象，该对象包含了文件内容
          const blob = new Blob([response.data], { type: response.headers['content-type'] });
          const fileType = response.data.contentType;
          
          // 创建一个临时的URL，用于预览
          const url = URL.createObjectURL(blob);

          // 在新窗口中打开预览
          window.open(url, '_blank');
        })
        .catch(error => {
          console.error('Failed to preview file:', error);
        });

      
    },
    

    handleRowClick(row) {
      
    },
    shareFile(file) {

        // 假设后端返回的数据包含一个名为 "url" 的属性
        this.shareUrl = this.warehouse+file.url;
        
        this.fileName = file.name;
        console.log(this.shareUrl);
        this.dialogVisible = true;
      
    },
    closeShareDialog() {
      // 关闭对话框
      this.dialogVisible = false;
    },
    copyUrl() {
      // 复制分享链接
      const textArea = document.createElement("textarea");
      textArea.value = this.shareUrl;
      document.body.appendChild(textArea);
      textArea.select();
      document.execCommand("copy");
      document.body.removeChild(textArea);
    },
  


    downloadFile(file) {
      // 使用模板字符串构建下载链接
      const downloadUrl = `${this.warehouse}${file.url}`;

      // 创建隐藏的 a 元素
      const link = document.createElement('a');
      this.uploading = true;
      link.href = downloadUrl;
      link.target = '_blank';
      link.download = file.name; // 设置 download 属性为文件名，确保浏览器触发下载

      // 将 a 元素添加到页面并触发点击事件
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link); // 移除添加的元素
    },
  

    
    handleFileChange(event) {
      // 获取选择的文件
      this.selectedFile = event.target.files[0];
    },
    uploadFile() {
      
      if (!this.selectedFile) {
        alert('请选择文件');

        return;
      }
      this.uploading = true;
      // 创建 FormData 对象
      let formData = new FormData();
        console.log(this.userId);

      formData.append('file', this.selectedFile);

      formData.append('userId',this.userId);
      formData.append('directoryId',this.departmentId);
      formData.append('create_time',getCurrentDateTime());
      formData.append('name',this.selectedFile.name);
      formData.append('is_delete',0);
      // formData.append('is_delete',0);


      if(formData.entries().next().done){
        alert('为空');
      }
      alert('不为空');
      // 使用 Axios 发送 POST 请求
      axios.post('/file/upload', formData, {
        onUploadProgress: (progressEvent) => {
          if (progressEvent.lengthComputable) {
            this.progress = Math.round((progressEvent.loaded / progressEvent.total) * 100);
          }
        },
      })
        .then(response => {
          // 处理上传成功的逻辑
          // console.log(response.data);
          alert('文件上传成功');
          
        })
        .catch(error => {
          // 处理上传失败的逻辑
          console.error(error);
          alert('文件上传失败');
        });
        this.selectedFile = null;
        

    },


  


    // 获取文件列表
    getFileList() {

      axios.get(`/file/find?company=${this.departmentId}`,{
          params: {
           //company:this.departmentId,
          }
          })
        .then(response => {
          // 使用箭头函数确保 this 指向 Vue 组件实例
          this.fileList = response.data.data;

        })
        .catch(error => {
          console.error('Request failed:', error.message);
        });
    },



    toggleSelection() {
      this.isSelectMode = !this.isSelectMode;

      if (!this.isSelectMode) {

        const dataTable = this.$refs.dataTable;
        if (dataTable) {
          const rows = dataTable.selection;
          rows.forEach((row) => {
            row.selected = false;
          });
          // 清空表格的选中状态
          dataTable.clearSelection();
          this.selectedFiles = null;
        }
        
      }
      
    },
    handleSelectionChange(selection) {
      // 将选中文件的 ID 存入 selectedFiles 数组中
      this.selectedFiles = selection.map(item => item.id);
      console.log(this.selectedFiles);
      
    },


    

    // 批量删除选中按钮点击事件
    deleteSelected() {
      const selectedIds = this.selectedFiles;
      if(!selectedIds){
        alert('文件未选择');
      }
      // 使用 Axios 发送 POST 请求
      axios.post(`/file/batch?ids=${selectedIds}`)
        .then(response => {
          // 处理上传成功的逻辑

          console.log(selectedIds);
          alert('文件批量删除成功');
          
        })
        .catch(error => {
          // 处理上传失败的逻辑
          console.error(error);
          alert('文件批量删除失败');
        });
        console.log('Delete file with IDs:', selectedIds);
        // 清空选中的文件

        this.selectedFiles = [] ;
      
      
    },

    deleteFile(id) {
      // 向后端发送文件ID，可以在这里执行你的删除操作
      

      axios.post(`/file/delete?id=${id}`)
        .then(response => {
          // 处理上传成功的逻辑
          // console.log(response.data);
          alert('文件删除成功');
          
        })
        .catch(error => {
          // 处理上传失败的逻辑
          console.error(error);
          alert('文件删除失败');
        });
      console.log('Delete file with ID:', id);
      this.getFileList();
    },
    
    
    handleProgress(event, file, fileList) {
      // 上传进度的处理逻辑
      this.uploading = true;
      this.uploadPercentage = event.percent || 0;
      // 更新文件列表中每个文件的上传进度
      this.fileList = fileList.map((item) => {
        if (item.uid === file.uid) {
          return { ...item, uploading: true, uploadPercentage: event.percent || 0 };
        }
        return item;
      });
      if (this.uploadPercentage === 100) {
        setTimeout(() => {
          this.uploading = false;
        }, 500);
      }
    },
  },


    getPatrait(){
      axios({
        method: 'get',
        url: '/user/preview',
        params: {
          id: this.userId,
        },
        responseType: 'blob', // 设置响应数据类型为二进制
      })
        .then(response => {
          // 创建一个 Blob 对象，该对象包含了文件内容
          const blob = new Blob([response.data], { type: response.headers['content-type'] });

          // 创建一个临时的URL，用于预览
          this.patraitUrl = URL.createObjectURL(blob);
          

          // 在新窗口中打开预览
          
        })
        .catch(error => {
          console.error('Failed to load patrait:', error);
        });
    },

  mounted() {

    this.getCookies();
    this.getFileList(); // 页面加载时获取文件列表
    this.getPatrait();
  },
};
  </script>
  
  <style>
  
    * {
      box-sizing: border-box;
    }
    .file-list-show {
        overflow-y:none;
        max-height:400px;
    }
    .modal {
    display: none;
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0, 0, 0, 0.4);
  }

  .modal-content {
    background-color: #fefefe;
    margin: 10% auto;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
  }

  .close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
  }

  .close:hover,
  .close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
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
      height: 50px;
      line-height: 50px;
      margin: 4px;
    }
    .el-submenu__title {
      height: 40px;
      line-height: 40px;
    }
  
    .el-header {
      box-shadow: 2px 0 6px rgba(0,21,41,.35);
  
    }
    .search-panel {
      height: 30px;
    }

  .upload-demo {
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .input-file-button {
    
    padding: 8px 7px;
    
    margin-right: 10px;

    background: #00bfff;
    border-radius: 4px;
    color: white;
    cursor: pointer;
  }
  </style>

<style lang="scss" scoped>

  @import "../assets/file.list.scss";
</style>
  
  