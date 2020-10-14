<template>
  <div class="app-container">
    <el-steps :active="active">
      <el-step title="step 1"></el-step>
      <el-step title="step 2"></el-step>
      <el-step title="step 3"></el-step>
    </el-steps>
    <ul>
      <li v-for="(item, index) in courseEntrys" :key="index">
        <span v-if="item[0]=='course_desc'">
          {{item[0]}}:
          <span v-html="item[1]"></span>
        </span>
        <span v-else-if="item[0]=='poster'">
          {{item[0]}}:
          <!-- <img :src="item[1]?item[1]:defaultPoster" alt /> -->
          <span v-if="item[1]">
            <img :src="item[1]" alt />
          </span>
          <span v-else>
            <img :src="defaultPoster" alt />
          </span>
        </span>
        <span v-else>{{item[0]}}:{{item[1]}}</span>
      </li>
    </ul>
    <el-button style="margin-top: 12px;" @click="last">Last</el-button>
    <el-button style="margin-top: 12px;" @click="next">Publish</el-button>
  </div>
</template>
<script>
import api from "@/api/subject";
import { log } from "util";
export default {
  name: "",
  created() {
    this.getCoursePublishById();
  },
  data() {
    return {
      defaultPoster: "http://skin.ybbs.ca/common/common1905/img/logo.jpg",
      active: 3,
      courseEntrys: [],
      course: { id: this.$route.query.courseId || 73 },
      courseId: this.$route.query.courseId || 73,
    };
  },

  methods: {
    async getCoursePublishById() {
      const data = await api.getCoursePublishById(this.course.id);

      this.courseEntrys = Object.entries(data.data);
      this.course = Object.values(data.data);
    },
    last() {
      this.$router.push({
        path: "/course/chapter",
        query: { courseId: this.course.id },
      });
    },
    async next() {
      const data = await api.publishCourse(this.courseId);
      if (data.success) {
        this.$router.push("/course/table");
      }
    },
  },
};
</script>
<style lang="scss" scoped>
</style>