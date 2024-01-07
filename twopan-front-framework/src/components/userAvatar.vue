<template>
  <div class="user-avatar">
    <img :src="imageUrl" alt="加载中..." class="avatar-image" style="width: 100px;height: auto;transform: translate(0,0)">
    <el-upload
        class="avatar-uploader"
        action=""
        :show-file-list="false"
        headers=''
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload">
      <div class="overlay">
        <div class="text">
          修改头像
        </div>
      </div>
    </el-upload>

  </div>
</template>

<script>
export default {
  name: "userAvatar",
  data() {
    return {
      imageUrl:'',
      id:'',
    };
  },
  methods: {
    previewFile() {
      this.$axios({
        method: 'get',
        url: `/user/preview?id=${this.id}`,
        responseType: 'blob', // 设置响应数据类型为二进制
      })
          .then(response => {
            // console.log(response.data);
                if (response.data.type === 'application/json') {
                  this.imageUrl=require('/src/assets/图标.png') ;
                }
                else {
                  // 创建一个 Blob 对象，该对象包含了文件内容
                  const blob = new Blob([response.data], { type: response.headers['content-type'] });

                  // 创建一个临时的URL，用于预览
                  const url = URL.createObjectURL(blob);

                  // console.log(url);
                  this.imageUrl=url;
                  // 在新窗口中打开预览
                  // window.open(url, '_blank');
                }
          })
          .catch(error => {
            console.error('Failed to preview file:', error);
          });
    },

    handleAvatarSuccess(res,file) {

    },
    beforeAvatarUpload(file) {

      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';

      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG) {
        this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      else {
        this.previewFile();
        //
        // 在上传之前将用户 ID 附加到 FormData 中
        const formData = new FormData();
        // console.log(file);
        formData.append('file', file);
        formData.append('id', this.id);
        this.$message.success('上传成功!');
        this.$axios.put("/user",formData,{
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        }).then(res => {
          this.previewFile();
        }).catch(error => {
          // console.log(error);
          // 处理错误
          this.$message.error(error);
        });

      }

      return isLt2M ;
    }
  },
  created() {
    // The created lifecycle hook is called when the component is created
    this.id=sessionStorage.getItem('id');
    this.previewFile();
  },
};
</script>

<style scoped>
.user-avatar {
  position: absolute;
  left: 7%;
  top: 7%;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  overflow: hidden;
  background-color: #ccc; /* 设置默认背景颜色，根据需要调整 */
  z-index: 1;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.avatar-image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 保持图片比例，裁剪超出部分 */
}

.overlay {

  width: 100px;
  height: 100px;
  border-radius: 50%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 14px;
  font-weight: bold;
  background: rgba(0, 0, 0, 0.7);
  z-index: 3;

  justify-content: center;
  text-align: center;
  align-content: center;

  display: none;
}
.text{
  align-self: center;
  user-select: none;
}

.user-avatar:hover{
  cursor: pointer;
}
.user-avatar:hover .overlay{
  display: flex;
}

</style>
