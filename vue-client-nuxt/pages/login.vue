<template>
  <div>
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="phone" prop="phone">
        <el-input v-model="ruleForm.phone"></el-input>
      </el-form-item>
      <el-form-item label="password" prop="password">
        <el-input v-model="ruleForm.password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">Login</el-button>
        <el-button @click="resetForm('ruleForm')">Reset</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import api from "@/utils/api/user";
import cookie from "js-cookie";
import { log } from "util";
export default {
  name: "",
  created() {},
  data() {
    return {
      ruleForm: {
        phone: "",
        password: "",
      },
      rules: {
        phone: [{ required: true, message: "username", trigger: "blur" }],
        password: [
          { required: true, message: "input password", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          const data = await api.login(this.ruleForm);
          if (data.data.success) {
            cookie.set("token", data.data.data, { domain: "localhost" });
            const data1 = await api.getUserByToken();
            console.log(data1);
            if (data1.data.success) {
              cookie.set("user", data1.data.data);
              this.$router.push("/");
            }
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>
<style  scoped>
.demo-ruleForm {
  margin: 200px auto;
  width: 400px;
}
</style>