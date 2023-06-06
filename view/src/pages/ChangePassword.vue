<template>
  <div class="div">
    <div class="div1">
      <el-form ref="form" :model="form" label-width="120px" :rules="rules">
      <el-form-item class="item" label="旧密码:"  prop="oldPassword" >
        <el-input
            v-model="form.oldPassword"
            type="password"
            placeholder="请输入旧密码"
            show-password
        />
      </el-form-item>
      <el-form-item class="item" label="新密码:" prop="newPassword1">
        <el-input
            v-model="form.newPassword1"
            type="password"
            placeholder="请输入新密码"
            show-password
        />
      </el-form-item>
      <el-form-item class="item" label="确认密码:" prop="newPassword2">
        <el-input
            v-model="form.newPassword2"
            type="password"
            placeholder="请确认密码"
            show-password
        />
      </el-form-item>
      <el-form-item class="submit">
        <el-button type="primary" @click="submitForm(form)">确认</el-button>
        <el-button @click="resetForm(form)">重置</el-button>
      </el-form-item>
    </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import {reactive} from "vue";

export default {
  name: "ChangePassword",
  data() {
    const validateOldPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('未填写旧密码'));
      } else {
        callback();
      }
    };
    const validateNewPass1 = (rule, value, callback) => {
      var reg_pwd = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/;
      if (value === '') {
        callback(new Error('新密码不能为空'));
      }else if(!reg_pwd.test(value)){
        callback(new Error('输入密码不符合格式'))
        this.$message.error("输入密码不符合格式");
      } else {
        callback();
      }
    };
    const validateNewPass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次填写新密码'));
      } else if(value !== this.form.newPassword1){
        callback(new Error('两次密码输入不一致'));
        this.$message.error("两次输入的密码不一致");
      }else{
        callback()
      }
    };
    return {
      form: {
        oldPassword: '',
        newPassword1: '',
        newPassword2: '',
      },
      rules : reactive({
        oldPassword: [{ validator: validateOldPass, trigger: 'blur' }],
        newPassword1: [{ validator: validateNewPass1, trigger: 'blur' }],
        newPassword2: [{ validator: validateNewPass2, trigger: 'blur' }],
      })
    }
  },
  methods:{
    resetForm(form){
      form.newPassword1 = '';
      form.oldPassword = '';
      form.newPassword2 = '';
    },
    submitForm(form){
      this.$refs['form'].validate((valid) =>{
        if(valid){
        request.post("http://localhost:8081/seller/Passwordupdate?password="+form.newPassword1).then(res =>{
          if (res.flag) {
            this.$message({
              type: 'success',
              message: res.msg
            })
          } else {
            this.$message.error(res.msg);
          }
        })}
      })
    }
  }
}
</script>

<style scoped>
  .div{
    background-color:	#E0EEE0;
  }
  .div1{
    position: absolute;
    left: 35%;
    right: 25%;
    top: 20%;
    bottom: 25%;
    text-align: center;
    line-height:80px;
    padding: 80px;
    border: 10px inset 	#43CD80;
  }
  .item{
     width:300px;
     height: 40px;
     margin-top: 30px;
  }

  .submit{
    color: #010905;
    padding-top: 50px;
    margin-left: 10px;
  }
</style>


