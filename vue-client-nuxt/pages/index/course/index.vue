<template>
  <div>
    <div class="category">
      <div class="condition-btn">
        <button @click.prevent="subLv1AllClick" :class="{active:condition.subLv1Id==null}">All</button>

        <button
          :class="{active:condition.subLv1Id==item.id}"
          @click.prevent="subLv1Click(item.id,index)"
          v-for="(item, index) in subjectList"
          :key="index"
        >{{item.title}}</button>
      </div>
      <div class="condition-btn">
        <button @click.prevent="subLv2AllClick" :class="{active:condition.subLv2Id==null}">All</button>
        <button
          :class="{active:condition.subLv2Id==item.id}"
          @click.prevent="subLv2Click(item.id)"
          v-for="(item, index) in subjectLv2List"
          :key="index"
        >{{item.title}}</button>
      </div>
      <div class="condition-btn">
        <button @click.prevent="orderIdChange">
          Sort by : id
          <span v-if="condition.sortBy=='id'">{{condition.order==0?'asce':'desc'}}</span>
        </button>
        <button @click.prevent="orderPriceChange">
          Sort by : Price
          <span v-if="condition.sortBy=='price'">{{condition.order==0?'asce':'desc'}}</span>
        </button>
      </div>
      <div></div>
    </div>
    <ul>
      <li v-for="(item, index) in courseList" :key="index">
        <div>id:{{item.id}}</div>
        <div>Price:{{item.price}}</div>
        <div>
          <router-link tag="button" :to="'/course/'+item.id">{{item.name}}</router-link>
        </div>
        <div>Desc:{{item.courseDesc.courseDesc}}</div>
        <div>create time:{{item.createTime}}</div>
      </li>
    </ul>
    <div style="margin:30px 0">
      <a href="#" @click.prevent="goTo(1)">First</a>
      <a
        :class="{diabled:condition.pageNum<2}"
        href="#"
        @click.prevent="goTo(condition.pageNum-1)"
      >Previous</a>
      <a
        :class="{diabled:condition.pageNum==item,current:condition.pageNum==item}"
        href="#"
        @click.prevent="goTo(item)"
        v-for="(item, index) in pages"
        :key="index"
      >page{{item}}</a>
      <a
        :class="{diabled:condition.pageNum>pages.length-1}"
        href="#"
        @click.prevent="goTo(condition.pageNum+1)"
      >Next</a>
      <a href="#" @click.prevent="goTo(pages.length)">Last</a>
    </div>
  </div>
</template>
<script>
import { log } from "util";
import api from "@/utils/api/course";
export default {
  name: "",
  async created() {
    this.getAllCourse();
    const data = await api.getAllSubject();
    this.subjectList = data.data.data;
  },
  data() {
    return {
      subjectList: [],
      courseList: [],
      pages: [],
      subLv1Index: null,
      condition: {
        subLv1Id: null,
        subLv2Id: null,
        pageNum: 1,
        pageSize: 3,
        sortBy: "id",
        order: 0,
      },
    };
  },
  methods: {
    async getAllCourse() {
      const data = await api.getFrontCourseByCondition(this.condition);
      this.courseList = data.data.data.content;
      console.log(data);
      this.pages = [];
      if (data.data.data.totalPages > 0) {
        let i = 1;
        while (i <= data.data.data.totalPages) {
          this.pages.push(i);
          i++;
        }
      }
    },
    subLv1Click(lv1Id, index) {
      this.condition.subLv1Id = lv1Id + "";
      this.subLv1Index = index;
      this.getAllCourse();
    },
    subLv1AllClick() {
      this.condition.subLv1Id = null;
      this.subLv1Index = null;
      this.condition.subLv2Id = null;
      this.getAllCourse();
    },
    subLv2AllClick() {
      this.condition.subLv2Id = null;
      this.getAllCourse();
    },
    subLv2Click(id) {
      this.condition.subLv2Id = id + "";
      this.getAllCourse();
    },
    orderIdChange() {
      this.condition.sortBy = "id";
      this.condition.order = this.condition.order == 0 ? 1 : 0;
      this.getAllCourse();
    },
    orderPriceChange() {
      this.condition.sortBy = "price";
      this.condition.order = this.condition.order == 0 ? 1 : 0;
      this.getAllCourse();
    },
    goTo(num) {
      this.condition.pageNum = num;

      this.getAllCourse();
    },
  },
  computed: {
    subjectLv2List() {
      if (this.subLv1Index == null) {
        return this.subjectList.reduce((pre, current) => {
          return [...pre, ...current.children];
        }, []);
      } else {
        return this.subjectList[this.subLv1Index].children;
      }
    },
  },
};
</script>
<style  scoped>
.category button {
  margin: 0 20px;
}
.category {
  margin: 50px;
}
.active {
  color: salmon;
}
.condition-btn {
  margin-bottom: 10px;
}
.diabled {
  pointer-events: none;
}
.current {
  color: rgb(240, 136, 62);
}
</style>