<template>
  <div class="app-container">
    <el-steps :active="active">
      <el-step title="step 1"></el-step>
      <el-step title="step 2"></el-step>
      <el-step title="step 3"></el-step>
    </el-steps>
    <div>
      <ul>
        <li v-for="(item, index) in chapterList" :key="index">
          <div class="chapter">
            <span>{{item.name}}</span>
            <span>
              <el-button @click="openAddSubDialog(item)">Add Sub Chapter</el-button>
              <el-button @click="openAddDialog(item)">Edit Chapter</el-button>
              <el-button @click="deleteChapter(item.id)">Delete Chapter</el-button>
            </span>
          </div>
          <ul v-for="(subChapter, index1) in item.children" :key="index1">
            <li>
              <div class="chapter">
                <span>{{subChapter.name}}</span>
                <span>
                  <el-button @click="openAddSubDialog(subChapter)">Edit SubChapter</el-button>
                  <el-button @click="deleteSubChapter(subChapter.id)">Delete SubChapter</el-button>
                </span>
              </div>
            </li>
          </ul>
        </li>
      </ul>
    </div>
    <div>
      <el-button @click="openAddDialog('')">Add Chapter</el-button>
    </div>

    <el-dialog title="Add Chapter" :visible.sync="dialogFormVisible">
      <el-form :model="chapter">
        <el-form-item label="Chapter Name" :label-width="formLabelWidth">
          <el-input v-model="chapter.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Sort of Chapter" :label-width="formLabelWidth">
          <el-input v-model="chapter.sort" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="addChapter ">Add</el-button>
      </div>
    </el-dialog>
    <el-dialog title="Add Sub-Chapter" :visible.sync="dialogFormVisibleSub">
      <el-form :model="subChapter">
        <el-form-item label="SubChapter Name" :label-width="formLabelWidth">
          <el-input v-model="subChapter.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Sort of Chapter" :label-width="formLabelWidth">
          <el-input-number v-model="subChapter.sort" :min="0" :max="999" label="描述文字"></el-input-number>
        </el-form-item>
        <el-form-item label="Chapter Free" :label-width="formLabelWidth">
          <el-radio @change="radioChange" v-model="subChapter.free" label="0">Free</el-radio>
          <el-radio @change="radioChange" v-model="subChapter.free" label="1">Not Free</el-radio>
        </el-form-item>
        <el-form-item label="upload Video" :label-width="formLabelWidth">
          <el-button type="primary" @click="addChapter ">video</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleSub = false">Cancel</el-button>
        <el-button type="primary" @click="addSubChapter ">Add</el-button>
      </div>
    </el-dialog>
    <el-button style="margin-top: 12px;" @click="last">Last</el-button>
    <el-button style="margin-top: 12px;" @click="next">Next</el-button>
  </div>
</template>
<script>
import { log } from "util";
import api from "@/api/subject";
export default {
  async created() {
    // const data = await api.getAllChapters(this.$route.params.courseId);
    this.getAllChapters();
  },
  name: "",
  data() {
    return {
      active: 2,
      chapterList: [],
      subChapter: {
        name: "",
        free: "0",
        sort: 0,
        courseId: this.$route.params.courseId || this.$route.query.courseId,
      },

      chapter: {
        courseId: this.$route.params.courseId || this.$route.query.courseId,
        name: "",
        sort: "",
      },
      dialogFormVisible: false,
      dialogFormVisibleSub: false,
      formLabelWidth: "200px",
    };
  },

  methods: {
    openAddDialog(item) {
      this.dialogFormVisible = true;
      if (item) {
        this.chapter.name = item.name;
        this.chapter.sort = item.sort;
        this.chapter.id = item.id;
      } else {
        this.chapter.name = "";
        this.chapter.sort = "";
        this.chapter.id = null;
      }
    },
    openAddSubDialog(item) {
      this.dialogFormVisibleSub = true;
      if (item.chapterId) {
        this.subChapter.name = item.name;
        this.subChapter.sort = item.sort;
        this.subChapter.free = item.free;
        this.subChapter.chapterId = item.chapterId;
        this.subChapter.id = item.id;
      } else {
        this.subChapter.free = "0";
        this.subChapter.sort = item.children.length + 1;
        this.subChapter.name = "";
        this.subChapter.chapterId = item.id;
        this.subChapter.id = null;
      }
    },

    async addChapter() {
      this.dialogFormVisible = false;
      const data = await api.addChapter(this.chapter);
      if (data.success) {
        this.getAllChapters();
      }
    },
    async deleteChapter(id) {
      try {
        const data = await api.deleteChapterById(id);
      } catch (e) {
      } finally {
      }
    },
    async addSubChapter() {
      this.dialogFormVisibleSub = false;
      // console.log(this.subChapter);
      const data = await api.addSubChapter(this.subChapter);
      if (data.success) {
        this.getAllChapters();
      }
    },
    async deleteSubChapter(id) {
      const data = await api.deleteSubChapterById(id);
      if (data.success) {
        this.getAllChapters();
      }
    },

    last() {
      this.$router.push({
        path: "/course/add",
        query: { courseId: this.chapter.courseId },
      });
    },
    next() {
      this.$router.push({
        path: "/course/publish",
        query: { courseId: this.chapter.courseId },
      });
    },
    async getAllChapters() {
      const data = await api.getAllChapters(this.chapter.courseId);
      this.chapterList = data.data;
    },
    radioChange(e) {
      this.subChapter.free = e;
    },
  },
};
</script>
<style lang="scss" scoped>
.chapter {
  border: 3px solid rebeccapurple;
  padding: 11px;
  width: 80%;
  display: flex;
  justify-content: space-between;
  margin: 15px 0;
  align-items: center;
}
</style>