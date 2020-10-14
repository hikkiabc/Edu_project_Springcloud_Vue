<template>
  <div class="container">
    <div v-if="this.user.id">
      Welcome: {{this.user.phone}}
      <br />
      <button @click="logout">Logout</button>
    </div>
    <div v-else>
      <a href="/login">
        <button>Login</button>
      </a>
      <a href="/register">
        <button>{{Register}}</button>
      </a>
    </div>

    <swiper class="swiper" ref="mySwiper" :options="swiperOptions">
      <!-- <swiper-slide class="swiper-slide" v-for="(banners,i) in banner" :key="i">
        <img v-bind:src="banners.href" />
      </swiper-slide>-->
      <swiper-slide class="swiper-slide" v-for="(item,i) in list" :key="i">
        <div>{{item.createTime}}</div>
      </swiper-slide>
      <div class="swiper-pagination" slot="pagination"></div>
      <div class="swiper-button-prev swiper-button-black" slot="button-prev"></div>
      <div class="swiper-button-next swiper-button-black" slot="button-next"></div>
    </swiper>
    <div class="link-container">
      <router-link tag="a" to="/course">
        <span class="link">Course</span>
      </router-link>

      <router-link tag="a" to="/teacher">
        <span class="link">Teacher</span>
      </router-link>
      <router-link tag="a" to="/">
        <span class="link">Home</span>
      </router-link>
    </div>
    <div>
      <ul>
        <li v-for="(item, index) in teacherList" :key="index">
          <img width="100px" :src="item.avatar" alt />
          {{item.name}}
        </li>
      </ul>
    </div>
    <hr />
    <div>
      <ul>
        <li v-for="(item, index) in courseList" :key="index">
          <img width="100px" :src="item.poster" alt />
          {{item.name}},desc:
          <span v-html="item.courseDesc.courseDesc"></span>
          <div>{{item.price}}</div>
        </li>
      </ul>
    </div>
    <keep-alive>
      <router-view></router-view>
    </keep-alive>

    <!-- <div v-if="!this.user.id"></div>
    <div v-else>Login first</div>-->

    <h1 style="margin-top:100px">footer</h1>
  </div>
</template>
<script>
import { Swiper, SwiperSlide, directive } from "vue-awesome-swiper";
import api from "@/utils/api/home";
import cookie from "js-cookie";
import { log } from "util";
// import "../assets/css/swiper.min.css";
export default {
  name: "",
  created() {},
  async mounted() {
    // const data = await Promise.all([
    //   api.getAllBanner(),
    //   api.getTeacherAndCourse(),
    // ]);
    // data.forEach((i) => {
    //   if (i.data.data.course) {
    //     this.courseList = i.data.data.course.content;
    //     this.teacherList = i.data.data.teacher.content;
    //   } else {
    //     this.list = i.data.data;
    //   }
    // });
    if (cookie.get("user")) {
      this.user = JSON.parse(cookie.get("user"));
    }
  },
  data() {
    return {
      Register: "Register",
      user: "",
      list: [],
      teacherList: "",
      courseList: "",
      banner: [
        { title: "banner1", href: require("./111.jpg") },
        { title: "banner2", href: require("./222.jpg") },
        { title: "banner3", href: require("./111.jpg") },
      ],
      swiperOptions: {
        notNextTick: true,
        //循环
        loop: true,
        //设定初始化时slide的索引
        initialSlide: 1,
        //自动播放
        autoplay: true,
        // 设置轮播

        // effect: "fade",
        //滑动速度
        speed: 800,
        //滑动方向
        direction: "horizontal",
        //小手掌抓取滑动
        // grabCursor : true,
        //左右点击
        navigation: {
          nextEl: ".swiper-button-next",
          prevEl: ".swiper-button-prev",
        },
        //分页器设置
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
        },
      },
      // swiperSlides: [1, 2, 3, 4],
    };
  },
  methods: {
    logout() {
      cookie.set("token", "");
      cookie.set("user", "");
      this.$router.go();
      // this.$router.go(0);
    },
  },
  components: {
    Swiper,
    SwiperSlide,
  },
  // watch: {
  //   $route: {
  //     immediate: true,
  //     handler: function (to, from) {},
  //   },
  // },
};
</script>
<style  scoped>
.container {
  padding: 30px;
}
.link-container {
  margin: 30px 0;
}
.swiper {
  width: 530px;
}
.link {
  font-size: 33px;
  margin-right: 66px;
}
</style>