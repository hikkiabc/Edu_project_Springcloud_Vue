<template>
  <div>
    <ul>
      <li v-for="(item, index) in list" :key="index">
        <span>{{item.id}}</span>|
        <span>{{item.name}}</span>
        <a :href="'/teacher/'+item.id" :title="item.name">
          <img width="100px" :src="item.avatar" />
        </a>
      </li>
    </ul>
    <div style="margin:30px 0">
      <a href="#" @click.prevent="goTo(1)">First</a>
      <a :class="{diabled:pageNum<2}" href="#" @click.prevent="goTo(pageNum-1)">Previous</a>
      <a
        :class="{diabled:pageNum==item,current:pageNum==item}"
        href="#"
        @click.prevent="goTo(item)"
        v-for="(item, index) in pages"
        :key="index"
      >page{{item}}</a>
      <a :class="{diabled:pageNum>pages.length-1}" href="#" @click.prevent="goTo(pageNum+1)">Next</a>
      <a href="#" @click.prevent="goTo(pages.length)">Last</a>
    </div>
  </div>
</template>
<script>
import api from "@/utils/api/teacher";
import { log } from "util";
export default {
  created() {
    this.getTeachers();
  },
  name: "",
  data() {
    return {
      pageNum: 1,
      pageSize: 6,

      list: "",
      pages: [],
    };
  },
  methods: {
    async getTeachers(pageNum, pageSize) {
      const data = await api.getTeachers(this.pageNum, this.pageSize);
      console.log(data);
      this.list = data.data.data.content;
      this.pages = [];
      let i = 1;
      while (i <= data.data.data.totalPages) {
        this.pages.push(i);
        i++;
      }
    },
    goTo(num) {
      this.pageNum = num;
      this.getTeachers();
    },
  },
};
</script>
<style  scoped>
a {
  text-decoration: none;
  color: inherit;
  margin: 0 20px;
}
.diabled {
  pointer-events: none;
}
.current {
  color: rgb(240, 136, 62);
}
</style>