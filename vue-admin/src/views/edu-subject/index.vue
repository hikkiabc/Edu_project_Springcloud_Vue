<template>
  <div class="app-container">
    <el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>

    <el-tree
      class="filter-tree"
      :data="data"
      :props="defaultProps"
      default-expand-all
      :filter-node-method="filterNode"
      ref="tree"
    ></el-tree>
  </div>
</template>
<script>
import api from "@/api/subject";
export default {
  name: "",
  async created() {
    const data = await api.getSubjectList();
    console.log(data);
    this.data = data.data;
  },
  data() {
    return {
      filterText: "",
      data: [],
      defaultProps: {
        children: "children",
        label: "title",
      },
    };
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      if (data.title.indexOf(value) !== -1) {
      }
      return data.title.indexOf(value) !== -1;
    },
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
};
</script>
<style lang="scss" scoped>
</style>