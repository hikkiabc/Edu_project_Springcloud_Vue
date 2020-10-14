<template>
  <div class="app-container">
    <el-steps :active="active">
      <el-step title="step 1"></el-step>
      <el-step title="step 2"></el-step>
      <el-step title="step 3"></el-step>
    </el-steps>
    <el-form label-position="right" label-width="150px" width="300px" :model="course">
      <el-form-item label="course name">
        <el-input v-model="course.name"></el-input>
      </el-form-item>
      <el-form-item label="course teacher">
        <el-select v-model="course.teacherId" placeholder="select teacher">
          <el-option
            v-for="(item, index) in teacherList"
            :key="index"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="course desc">
        <editor
          id="tiny"
          v-model="course.courseDesc"
          :init="init"
          ref="editor"
          :disabled="false"
          @onClick="onClick"
        ></editor>
        <button @click="clear">clear</button>
      </el-form-item>
      <el-form-item label="course price">
        <el-input-number v-model="course.price" :precision="2" :step="0.1" :max="10000"></el-input-number>
      </el-form-item>
      <el-form-item label="subject categary">
        <el-cascader
          :props="{ expandTrigger: 'hover',label:'title',children:'children' ,value:'id'}"
          :options="subjectList"
          v-model="subjectIds"
          @change="handleChange"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="course poster">
        <el-upload
          :show-file-list="false"
          class="upload-demo"
          :on-success="uploadSuccess"
          ref="upload"
          :action="uploadUrl"
          :file-list="fileList"
        >
          <img
            width="130px"
            :src="course.poster? course.poster:  'http://skin.ybbs.ca/common/common1905/img/logo.jpg'"
          />
        </el-upload>
      </el-form-item>

      <!-- <el-form-item label="course desc">
        <el-input
          type="textarea"
          placeholder="course desc"
          v-model="course.courseDesc"
          maxlength="130"
          :rows="2"
          show-word-limit
        ></el-input>
      </el-form-item>-->
    </el-form>
    <el-button style="margin-top: 12px;" @click="next">save and next</el-button>
  </div>
</template>
<script>
import api from "@/api/subject";
import { log } from "util";
import tinymce from "tinymce/tinymce";
import Editor from "@tinymce/tinymce-vue";
import "tinymce/themes/silver";
import axios from "axios";
import "tinymce/icons/default/icons"; //解决了icons.js 报错Unexpected token '<'
// 编辑器插件plugins
// 更多插件参考：https://www.tiny.cloud/docs/plugins/
import "tinymce/plugins/image"; // 插入上传图片插件
import "tinymce/plugins/media"; // 插入视频插件
import "tinymce/plugins/table"; // 插入表格插件
import "tinymce/plugins/lists"; // 列表插件
import "tinymce/plugins/wordcount"; // 字数统计插件
import "tinymce/themes/silver/theme";
import "tinymce/plugins/link";
import "tinymce/plugins/code";
import "tinymce/plugins/contextmenu";
import "tinymce/plugins/colorpicker";
import "tinymce/plugins/textcolor";

import "tinymce/plugins/emoticons"; // 字数统计插件

export default {
  name: "",
  async created() {
    const data = await Promise.all([api.getAllTeacher(), api.getSubjectList()]);
    data.forEach((i) => {
      if (i.data[0].title) {
        this.subjectList = i.data;
      } else {
        this.teacherList = i.data;
      }
    });

    tinymce.init({});
    if (this.$route.query.courseId) {
      const data = await api.getCourseById(this.$route.query.courseId);

      this.course = data.data;
      this.course.descId = this.course.courseDesc.id;
      this.course.courseDesc = this.course.courseDesc.courseDesc;
      this.subjectIds = [
        parseInt(this.course.subjectLv1Id),
        parseInt(this.course.subjectLv2Id),
      ];
      // this.course.poster = "http://skin.ybbs.ca/common/common1905/img/logo.jpg";
    }
  },
  components: {
    Editor,
  },
  data() {
    return {
      course: {
        poster: "",
        // "https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg",
        // "/4.jpg",
        // "http://skin.ybbs.ca/common/common1905/img/logo.jpg",
      },
      active: 1,
      uploadUrl: `${process.env.VUE_APP_BASE_API}/oss/upload_avatar`,
      subjectList: [],
      teacherList: [],
      subjectIds: [],
      fileList: [],
      init: {
        // language_url: "/static/tinymce/langs/zh_CN.js",
        selector: "#tiny",
        skin_url: "/tinymce/skins/ui/oxide",
        content_css: `/tinymce/skins/content/default/content.css`,
        height: 300,
        // auto_focus: true,
        // images_upload_url: "/oss/upload_avatar",
        emoticons_database_url: "/emojis.js",
        plugins: "link lists image emoticons code table  wordcount  ",
        toolbar:
          "bold emoticons  italic underline strikethrough | fontsizeselect | forecolor backcolor | alignleft aligncenter alignright alignjustify | bullist numlist | outdent indent blockquote | undo redo | link unlink image code | removeformat ",
        branding: false,
        menubar: false,
        //此处为图片上传处理函数，这个直接用了base64的图片形式上传图片，
        //如需ajax上传可参考https://www.tiny.cloud/docs/configure/file-image-upload/#images_upload_handler
        // images_upload_handler: (blobInfo, success, failure) => {
        //   const img = "data:image/jpeg;base64," + blobInfo.base64();
        //   success(img);
        // },
        images_upload_handler: (blobInfo, success, failure) => {
          const formData = new FormData();
          formData.append("file", blobInfo.blob());
          axios
            .post("http://localhost:9001/oss/upload_avatar", formData)
            .then((res) => {
              if (res.data.success) {
                let file = res.data.data;
                success(file);
                return;
              }
              failure("上传失败");
            })
            .catch(() => {
              failure("上传出错");
            });
        },
      },
      myValue: "",
    };
  },

  methods: {
    async next() {
      // console.log(this.course);
      const data = await api.addCourse(this.course);
      if (data.success)
        this.$router.push({
          name: "course-chapter",
          params: {
            courseId: data.data.id,
          },
        });
    },
    handleChange(e) {
      this.course.subjectLv1Id = e[0];
      this.course.subjectLv2Id = e[1];
    },
    uploadSuccess(res) {
      this.course.poster = res.data;
    },
    onClick(e) {
      // this.$emit("onClick", e, tinymce);

      // tinymce.editors[0].editorManager.get("tiny").focus();
      if (document.querySelector(".tox-tbtn--enabled")) {
        // let e = document.createEvent("MouseEvents");
        // e.initEvent("click", true, true);

        document.querySelector(".tox-tbtn--enabled").click();
        tinymce.editors[0].editorManager.get("tiny").focus();
      }
    },
    clear() {
      this.$refs.editor.c;
      // console.log(tinymce.editors[0].editorManager);

      this.myValue = "";
    },
  },
  watch: {
    $route(to, from) {
      if (!this.$route.query.courseId) this.course = {};
      this.course.courseDesc = "";
      this.subjectIds = [];
      // this.course.poster = "http://skin.ybbs.ca/common/common1905/img/logo.jpg";
    },
  },
};
</script>
<style lang="scss" scoped>
.el-form {
  width: 600px;
}
</style>