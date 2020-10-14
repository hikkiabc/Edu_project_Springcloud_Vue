<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="Keyword">
        <el-input v-model="query.keyword" placeholder="keyword"></el-input>
      </el-form-item>
      <el-form-item label="Date">
        <el-col :span="11">
          <el-date-picker
            type="date"
            placeholder="choose min"
            v-model="query.min"
            style="width: 100%;"
          ></el-date-picker>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
        <el-col :span="11">
          <el-date-picker
            type="date"
            placeholder="choose max"
            v-model="query.max"
            style="width: 100%;"
          ></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">Search</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="reset">Reset</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="list" style="width: 100%">
      <el-table-column label="Index" width="80">
        <template slot-scope="scope">{{( pageNum-1)*pageSize+scope.$index+1 }}</template>
      </el-table-column>
      <el-table-column prop="id" label="Id" width="80"></el-table-column>
      <el-table-column label="Create Date" width="220">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Name" width="150">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>name: {{ scope.row.name }}</p>
            <p>desc: {{ scope.row.courseDesc?scope.row.courseDesc.courseDesc:'no desc' }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="Publish" width="220">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.status=='publish'?'':'danger'"
            size="medium"
          >{{ scope.row.status=='publish'?'Publish':'Not Publish' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="right">
        <template slot="header" slot-scope="scope">
          <!-- style="margin-right:60px" -->
          <span>Actions</span>
        </template>
        <template slot-scope="scope">
          <el-button @click="editCourse(scope.row.id)" size="mini">Edit Course</el-button>

          <el-button @click="editChapter(scope.row.id)" size="mini">Edit Chapter</el-button>

          <el-button size="mini" type="danger" @click="deleteById(scope.row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagi-wrapper">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="getAllCourse"
        :current-page="pageNum"
        :page-sizes="[1,2, 5]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>
  </div>
</template>
<script>
import api from "@/api/subject";
import { log } from "util";
export default {
  name: "",
  created() {
    this.getAllCourse();
  },
  data() {
    return {
      pageNum: 1,
      pageSize: 5,
      total: 0,
      query: { keyword: null, min: null, max: null },
      list: [],
    };
  },
  methods: {
    async getAllCourse(e = 1) {
      this.pageNum = e;
      const data = await api.getAllCourse({
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        query: this.query,
      });
      this.list = data.data.content;
      this.total = data.data.totalElements;
    },
    reset() {
      this.query.keyword = null;
      this.query.min = null;
      this.query.max = null;
      this.getAllCourse();
    },
    search() {
      this.getAllCourse();
    },

    handleSizeChange(e) {
      this.pageSize = e;
      this.getAllCourse(this.pageNum);
    },
    async deleteById(id) {
      const data = await api.deleteCourseById(id);
      if (data.success) this.getAllCourse();
    },
    editCourse(id) {
      this.$router.push({
        path: "/course/add",
        query: { courseId: id },
      });
    },
    editChapter(id) {
      this.$router.push({
        path: "/course/chapter",
        query: { courseId: id },
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.name-wrapper {
  display: inline-block;
}
.el-pagination {
  // margin: 0 auto;
  // padding: 0;
  // width: 60%;
  display: inline-block;
}
.pagi-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
.el-col-2 {
  text-align: center;
  width: 5%;
}
</style>