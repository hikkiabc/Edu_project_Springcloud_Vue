<template>
  <div class="app-container">
    <el-upload
      class="upload-demo"
      ref="upload"
      :action="url+'/teacher/subject/upload'"
      :on-remove="handleRemove"
      :file-list="fileList"
      :on-success="handleAvatarSuccess"
      :auto-upload="false"
      :before-upload="beforeAvatarUpload"
      accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    >
      <el-button slot="trigger" size="small" type="primary">select</el-button>
      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">upload</el-button>
      <div slot="tip" class="el-upload__tip">only excel</div>
    </el-upload>
  </div>
</template>
<script>
export default {
  name: "",
  data() {
    return {
      url: process.env.VUE_APP_BASE_API,
      fileList: [],
    };
  },
  methods: {
    submitUpload() {
      this.$refs.upload.submit();
    },
    beforeAvatarUpload(file) {
      // const isJPG = file.type === "image/jpeg";
      // const isLt2M = file.size / 1024 / 1024 < 2;

      // if (!isJPG) {
      //   this.$message.error("上传头像图片只能是 JPG 格式!");
      // }
      // if (!isLt2M) {
      //   this.$message.error("上传头像图片大小不能超过 2MB!");
      // }
      // return isJPG && isLt2M;
      return true;
    },
    handleAvatarSuccess() {
      this.$message("ok");
      this.$router.push("/subject/table");
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
  },
};
</script>
<style lang="scss" scoped>
</style>