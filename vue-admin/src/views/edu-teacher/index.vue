<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="Keyword">
        <el-input v-model="keyword" placeholder="keyword"></el-input>
      </el-form-item>
      <el-form-item label="Date">
        <el-col :span="11">
          <el-date-picker type="date" placeholder="choose min" v-model="min" style="width: 100%;"></el-date-picker>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
        <el-col :span="11">
          <el-date-picker type="date" placeholder="choose max" v-model="max" style="width: 100%;"></el-date-picker>
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
          <span style="margin-left: 10px">{{ scope.row.startTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Name" width="150">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>name: {{ scope.row.name }}</p>
            <p>id: {{ scope.row.id }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="Deleted?" width="220">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.deleted?'danger':''"
            size="medium"
          >{{ scope.row.deleted?'Deleted':'Not Deleted' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="right">
        <template slot="header" slot-scope="scope">
          <!-- style="margin-right:60px" -->
          <span>Actions</span>
        </template>
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button size="mini">Edit</el-button>
          </router-link>

          <el-button size="mini" type="danger" @click="deleteById(scope.row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagi-wrapper">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[1, 5]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>
  </div>
</template>
<script>
import teacherApi from "@/api/teacher";
export default {
  name: "",
  data() {
    return {
      pageNum: 1,
      pageSize: 5,
      total: 0,
      keyword: null,
      min: null,
      max: null,
      queryConditions: {},
      list: [],
    };
  },
  async created() {
    this.handleCurrentChange();
  },
  methods: {
    reset() {
      (this.max = null),
        (this.min = null),
        (this.keyword = null),
        this.handleCurrentChange();
    },
    search() {
      this.handleCurrentChange();
    },
    handleSizeChange(e) {
      this.pageSize = e;
      this.handleCurrentChange(this.pageNum);
    },
    async handleCurrentChange(e = 1) {
      this.pageNum = e;
      const data = await teacherApi.findByConditions({
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        max: this.max,
        min: this.min,
        keyword: this.keyword,
      });
      this.list = data.data.content;
      this.total = data.data.totalElements;
    },
    deleteById(id) {
      this.$confirm(`Delete ${id}?`, {
        confirmButtonText: "Yes",
        cancelButtonText: "Canel",
        type: "warning",
      })
        .then(async () => {
          const data = await teacherApi.deleteById(id);
          console.log(data);
          this.$message({
            type: "success",
            message: "deleted",
          });
          this.handleCurrentChange();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "cancel",
          });
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