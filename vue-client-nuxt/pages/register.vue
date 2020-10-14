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
        <button
          type="button"
          :disabled="codeSec!=0"
          @click="sendCode"
        >{{codeSec?'Wait'+codeSec+'s':'Get code'}}</button>
      </el-form-item>
      <el-form-item label="password" prop="password">
        <el-input v-model="ruleForm.password"></el-input>
      </el-form-item>
      <el-form-item label="code" prop="code">
        <el-input v-model="ruleForm.code"></el-input>
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
import { log } from "util";
export default {
  name: "",
  created() {},
  data() {
    return {
      ruleForm: {
        phone: "",
        name: "",
        password: "",
        code: "",
      },
      codeBtn: false,
      codeSec: 0,
      rules: {
        name: [{ required: true, message: "username", trigger: "blur" }],
        password: [
          { required: true, message: "input password", trigger: "blur" },
        ],
        phone: [{ required: true, message: "input phone", trigger: "blur" }],
        code: [{ required: true, message: "input code", trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          const data = await api.register(this.ruleForm);
          if (data.data.data.phone) {
            this.$router.push("/login");
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
    async sendCode() {
      if (!this.ruleForm.phone) {
        return false;
      }
      const data = await api.getCode(this.ruleForm.phone);
      this.codeSec = 16;
      let timer = setInterval(() => {
        this.codeSec--;
        if (this.codeSec == 0) clearInterval(timer);
      }, 1000);
      console.log(data);
    },
  },
};
</script>
<style  scoped>
.demo-ruleForm {
  margin: 200px auto;
  width: 600px;
}
.el-input {
  width: 300px;
}
</style>