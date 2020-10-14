<template>
  <div>
    <div>Subject: {{detail.subject1}}---{{detail.subject2}}</div>
    <div>Name: {{detail.courseName}}</div>

    <ul>
      <li v-for="(item, index) in chapter" :key="index">
        {{item.name}}
        <ul>
          <li v-for="(item, index) in item.children" :key="index">{{item.name}}</li>
        </ul>
      </li>
    </ul>
    <button @click="buyClick">
      <h3>Buy this</h3>
    </button>
    <div style="margin-top:30px">
      <h2>Comments:</h2>
      <ul>
        <li v-for="(item,k, index) in commentList" :key="index">
          <!-- <span>{{k}}</span> -->
          username:{{users[item.userId].username}}, Date:{{item.createDate}}
          <h2>content:{{item.content}}</h2>
          <ul>
            <li v-for="(item1, index) in item.children" :key="index">
              username:{{users[item1.userId].username}}, Date:{{item1.createDate}}
              <div
                style="color:blue"
                v-if="item1.replyTo"
              >Reply to {{users[item.children.find(i=>i.id==item1.replyTo).userId].username}} : {{item.children.find(i=>i.id==item1.replyTo).content}}</div>
              <h2>
                <div>content:{{item1.content}}</div>
              </h2>
              <button
                @click="subReplyClick(item1.id,item.id)"
              >{{replyTo==item1.id? subReplyOrCancel:"Reply"}}</button>
              <div v-if="replyTo==item1.id&&subReplyOrCancel=='Cancel'">
                <div>reply to: {{item1.content}}</div>
                <textarea
                  v-model="replyContent"
                  style="width: 200px; height: 50px"
                  placeholder="reply..."
                ></textarea>
                <button @click="saveComment()">Reply</button>
              </div>
            </li>
          </ul>
          <button @click="replyClick(item.id)">{{pid==item.id?replyOrCancel:'Reply'}}</button>
          <div v-if="pid==item.id&&replyOrCancel=='Cancel'">
            <div>reply to: {{item.content}}</div>
            <textarea
              v-model="replyContent"
              style="width: 200px; height: 50px"
              placeholder="reply..."
            ></textarea>
            <button @click="saveComment()">Reply</button>
          </div>
        </li>
      </ul>
      <textarea v-model="content" style="width: 200px; height: 100px" placeholder="comments..."></textarea>
      <button @click="saveComment()">submit</button>
    </div>
  </div>
</template>
<script>
import api from "@/utils/api/course";
import orderApi from "@/utils/api/order";
import cookie from "js-cookie";
import { log } from "util";
export default {
  deactivated() {
    this.$destroy();
  },
  created() {
    this.getCourseDetailById();
    // console.log(JSON.parse(cookie.get("user")));
    let user = cookie.get("user");
    if (user) {
      this.user = JSON.parse(user);
    }
    this.getComment();
    // this.user = JSON.parse(cookie.get("user"));
  },
  name: "",
  data() {
    return {
      replyOrCancel: "Reply",
      users: {},
      pid: null,
      commentList: {},
      chapter: [],
      detail: "",
      user: "",
      content: "",
      replyTo: "",
      replyContent: "",
      subReplyOrCancel: "Reply",
    };
  },
  methods: {
    async getCourseDetailById() {
      const data = await api.getCourseDetailById(this.$route.params.id);
      this.chapter = data.data.data.chapter;
      this.detail = data.data.data.detail;
    },
    async getComment() {
      const data = await api.getComment(this.$route.params.id);
      // console.log(data);
      data.data.data[0].forEach((i) => {
        this.commentList[i.id] = i;
      });
      this.commentList = { ...this.commentList };
      console.log(this.commentList);
      data.data.data[1].forEach((i) => {
        this.users[i.id] = i;
      });
    },
    async saveComment(pid) {
      if (!this.user) {
        this.$router.push("/login");
        return;
      }
      const data = await api.saveComment({
        content: this.replyContent ? this.replyContent : this.content,
        replyTo: this.replyTo,
        userId: this.user.id,
        courseId: this.$route.params.id,
        pid: this.pid,
      });
      this.getComment();
      this.$router.go(0);
    },
    replyClick(pid) {
      this.pid = pid;
      this.replyOrCancel = this.replyOrCancel == "Reply" ? "Cancel" : "Reply";
      if (this.replyOrCancel == "Reply") {
        (this.pid = null), (this.replyTo = "");
      }
    },
    subReplyClick(subId, pid) {
      this.replyTo = subId;
      this.pid = pid;
      this.subReplyOrCancel =
        this.subReplyOrCancel == "Reply" ? "Cancel" : "Reply";
      if (this.subReplyOrCancel == "Reply") {
        (this.pid = null), (this.replyTo = "");
      }
    },
    async buyClick() {
      const data = await orderApi.saveOrder({
        courseId: this.$route.params.id,
      });
      // console.log(data.data.data);
      if (data.data.data.id) {
        this.$router.push({
          path: "/order",
          query: {
            data: data.data.data,
            orderNo: data.data.data.orderNo,
          },
        });
      }
    },
  },
};
</script>
<style lang="scss" scoped>
</style>