<template>
  <div id="divm">
    <div class="divo">
      <h1 id="title">欢迎来到我们真菜</h1>
      <!-- <img src="../../assets/img6.jpg" id="pic"> -->
        <el-form ref="editFormData" :label-position="labelPosition" label-width="80px" :model="formData" id="for" :rules="rules" status-icon>
          <el-form-item label="账号" class="formData" prop="account">
            <el-input v-model="formData.account"></el-input>
          </el-form-item>
          <el-form-item label="密码" class="formData" prop="password">
            <el-input type="password" v-model="formData.password" show-password></el-input>
          </el-form-item>
          <el-button type="primary" id="submit" @click="login">登录</el-button>
      </el-form>
    </div>
  </div>
</template>

<script>
import router from "@/router";
import request from "@/utils/request";
import {reactive} from "vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data(){
    //密码校验 密码不为空
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('密码不能为空,请填写密码'))
      } else {
        callback()
      }
    };
    const validateAcc = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('账号不能为空,请填写账号'))
      } else {
        callback()
      }
    };
    return{
      labelPosition: 'right',
      formData:{
        account:'',
        password:''
      },
      rules : reactive({
        account: [{ validator: validateAcc, trigger: 'blur' }],
        password: [{ validator: validatePass, trigger: 'blur' }],
      })
    }
  },
  methods:{
  login(){
      this.$refs['editFormData'].validate((valid) => {
        if (valid) {
          request.post("http://localhost:8081/seller/Sellerlogin?account="+this.formData.account+"&password="+this.formData.password).then(res=>{
            if(res.flag){
              localStorage.setItem("token",res.data)
              router.push({path: '/background/welcome'});
              this.$message({
                type: 'success',
                message: res.msg
              })
            }else {
              this.$message.error(res.msg);
              this.formData={
                account:"",
                password:""
              }
            }
          })
        } else {
          this.$message.error('账号和密码都不能为空');
          return false
        }
      })
    }
  },
}
</script>

<style scoped>
#divm{
  position: absolute;
    left: 0%;
    right: 0%;
    top: 0%;
    bottom: 0%;
    background: linear-gradient(to bottom right,#1297de, #6abedc, #f6f6f6);
}
.divo{
  position: absolute;
    left: 15%;
   right: 15%;
   top: 15%;
   height: 427px;
   border: 10px inset rgb(27, 104, 198);
   box-shadow: 20px 20px 10px 3px #000;  /*Chrome 6+, Firefox 4+, IE 9+, iOS 5+, Opera 10.50+*/
   -webkit-box-shadow: 10px 8px 10px 3px #000;
   -moz-box-shadow: 10px 8px 10px 3px #000;
     /*background-clip: padding-box;*/
  opacity: 0.9; /*透明度*/
  background-image: url(../../assets/img6.jpg);
  background-repeat: no-repeat;
  background-position-x: right;
}
#title{
  margin-left: 110px;
    margin-top: 50px;
    color:aliceblue;
}
#stitle{
  text-align: center;
  margin-top: 15px;
  color: gray;
}
#pic{
  position:absolute;
    right: 0%;
    top: 0%;
    bottom: 0%;
    width: 530px;
    height: 488px;
}
#for{
    margin-left: 68px;
    margin-top: 20px;
    font-size: 20px;
 }
.formData{
  width: 300px;
  height: 30px;
  margin-top: 40px;
}
#submit{
  position: absolute;
   width: 225px;
    height: 30px;
    margin-top: 10px;
    margin-left: 77px;
}
#regiest{
  position: absolute;
   width: 225px;
   height: 30px;
   margin-left: 77px;
   margin-top: 60px;
}
</style>
