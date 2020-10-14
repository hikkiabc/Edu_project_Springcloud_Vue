<template></template>
<script>
import { log } from "util";
import api from "@/utils/api/teacher";
export default {
  name: "",
  created() {
    this.getTeacherAndCourse(this.$route.params.id);
  },
  data() {
    return {};
  },
  methods: {
    async getTeacherAndCourse(id) {
      const data = await api.getTeacherAndCourseByTeacherId(id);
      console.log(data);
    },
  },
};
</script>
<style lang="scss" scoped>
</style>