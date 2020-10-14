<template>
  <div class="app-container">
    <el-form
      :model="teacherForm"
      :rules="rules"
      ref="teacherForm"
      label-width="120px"
      class="demo-teacherForm"
    >
      <el-form-item label="Teacher Name" prop="name">
        <el-input v-model="teacherForm.name"></el-input>
      </el-form-item>
      <el-form-item label="deleted" prop="deleted">
        <el-select v-model="teacherForm.deleted" placeholder="deleted?">
          <el-option label="deleted" value="1"></el-option>
          <el-option label="not deleted" value="0"></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="description" prop="desc">
        <el-input type="textarea" v-model="teacherForm.desc"></el-input>
      </el-form-item>-->

      <el-form-item>
        <!-- <el-upload action="#" list-type="picture-card" :auto-upload="false" :file-list="fileList">
          <i slot="default" class="el-icon-plus"></i>
          <div slot="file" slot-scope="{file}">
            <img class="el-upload-list__item-thumbnail" :src="file.url" alt />
            <span class="el-upload-list__item-actions">
              <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
                <i class="el-icon-zoom-in"></i>
              </span>
              <span
                v-if="!disabled"
                class="el-upload-list__item-delete"
                @click="handleDownload(file)"
              >
                <i class="el-icon-download"></i>
              </span>
              <span
                v-if="!disabled"
                class="el-upload-list__item-delete"
                @click="handleRemove(file)"
              >
                <i class="el-icon-delete"></i>
              </span>
            </span>
          </div>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt />
        </el-dialog>-->
        <el-upload
          action="http://localhost:9001/oss/upload_avatar"
          ref="upload"
          name="file"
          list-type="picture-card"
          :on-preview="handlePictureCardPreview"
          :on-remove="handleRemove"
          :auto-upload="true"
          :on-error="uploadFalse"
          :on-success="uploadSuccess"
          :file-list="fileList"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt />
        </el-dialog>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="submitForm('teacherForm')"
        >{{$route.params.id?'Update':'Add'}}</el-button>
        <el-button @click="resetForm('teacherForm')">Reset</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import teacherApi from "@/api/teacher";
export default {
  name: "",
  data() {
    return {
      teacherForm: {
        // id:null,
        name: null,
        deleted: null,
        avatar: [],
      },
      fileList: [],
      tempList: [],
      dialogVisible: false,
      dialogImageUrl: "",
      disabled: false,
      rules: {
        name: [
          { required: true, message: "input name", trigger: "blur" },
          { min: 1, max: 10, message: "between 1-10", trigger: "blur" },
        ],
        deleted: [
          {
            required: true,
            message: "select deleted or not",
            trigger: "change",
          },
        ],
        date1: [
          {
            type: "date",
            required: true,
            message: "Select Date",
            trigger: "change",
          },
        ],
        type: [
          {
            type: "array",
            required: true,
            message: "请至少选择一个活动性质",
            trigger: "change",
          },
        ],

        desc: [{ required: true, message: "请填写活动形式", trigger: "blur" }],
      },
    };
  },
  async created() {
    if (this.$route.params.id) {
      const data = await teacherApi.findById(this.$route.params.id);
      this.teacherForm = data.data;
    }
  },
  methods: {
    submitForm(formName) {
      console.log(this.teacherForm);
      // this.$refs.upload.submit();
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          delete this.teacherForm.startTime;

          const data = await teacherApi.add(this.teacherForm);
          this.$router.push("/teacher/table");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      console.log(file, fileList);
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    uploadSuccess(response, file, fileList) {
      this.fileList = fileList;
      if (response.success) {
        // this.teacherForm.avatar.push(response.data);
        this.teacherForm.avatar = response.data;
        this.$message({
          message: "导入成功",
          type: "success",
        });
      } else {
        this.fileList = this.fileList.filter((i) => i.name != file.name);
        this.$message({
          message: "导入失败",
          type: "error",
        });
      }
    },
    //文件上传失败触发
    uploadFalse(response, file, fileList) {
      this.fileList.push(file);
      this.$message({
        message: "文件上传失败！",
        type: "error",
      });
    },
  },
  watch: {
    $route: {
      handler(newVal, objVal) {
        console.log(newVal, objVal);
      },
      deep: true,
    },
    teacherForm: {
      handler(newVal, objVal) {
        // if(newVal.obj || objVal.obj != objVal.obj){
        //    console.log(22);    //控制台并没有打印   监测为对象的时候，newVal == oldVal
        //      }
        // console.log(33); //打印为33
      },
      deep: true,
    },
  },
};
</script>
<style lang="scss" scoped>
.demo-teacherForm {
  width: 500px;
}
</style>