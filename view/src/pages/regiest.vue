<template>
  <body>
  <div>
    <img src="../assets/bg.jpg">
    <h1 class="title">欢迎来到小趴菜商城</h1>
    <h3 v-show="!show" class="t1">您已经注册过了，请前往登录</h3>
    <div v-show="show">
      <form>
        <div class="class1">姓名&nbsp; : &nbsp;<input id=name @blur="checkuser()" type="text" class="text1"
                                                      v-model="registerParams.name">
          <div class="tishi">中文</div>
          <span id="s_user" class="error"></span></div>
        <div class="class2">账号&nbsp; : &nbsp;<input id=id @blur="checkid()" type="text" class="text1"
                                                      v-model="registerParams.account">
          <div class="tishi">3-20位,无特殊字符,必须有字母</div>
          <span id="i_user" class="error"></span></div>
        <div class="class3">密码 &nbsp;: &nbsp;<input id=pwd @blur="checkpwd()" type="password" class="text1"
                                                      v-model="registerParams.password">
          <div class="tishi">密码不少于六位，含数字字母</div>
          <span id="p_user" class="error"></span></div>
        <div class="class5">确认密码 &nbsp;: &nbsp;<input id=repwd @blur="confirmpwd()" type="password" class="text1"
                                                          v-model="password2"><span id="r_user" class="error"></span>
        </div>
      </form>
      <button type="button" class="class4" @click="register">注册</button>
    </div>
  </div>
  </body>

</template>

<script>
import request from "@/utils/request";
import router from "@/router";



export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "regiest",
  data() {
    return {
      show: true,
      registerParams: {
        name: '',
        account: '',
        password: ''
      },
      password2: '',
      flag1:false,
      flag2:false,
      flag3:false,
      flag4:false,
    }
  },
  methods: {
    checkuser(){
      var name = document.getElementById("name").value;
      var s_user = document.getElementById("s_user");
      var reg_name = /[\u4E00-\u9FA5]{2,9}$/;
      var flag = reg_name.test(name);

      if(flag){
        s_user.innerHTML = "格式正确";
        this.flag1=true;
      }
      else {
        s_user.innerHTML = "用户名格式有误！";
        this.flag1=false;
    }
    return flag;
    },
    checkid(){
      // var reg=/^[0-9a-zA-Z_]{3,20}$/
      var id = document.getElementById("id").value;
      var i_user = document.getElementById("i_user");
      // var reg_id = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{3,20}$/;
      // var flag = reg_id.test(id);
      if((id.length<=20 && id.length >= 3) && id.match(/[a-zA-Z]/) && !id.match(/[^a-zA-Z0-9]/)){
        i_user.innerHTML = "格式正确";
        this.flag2=true;
        return true;
      }
      else {
        i_user.innerHTML = "账号格式有误！";
        this.flag2=false;
        return false;
    }
    },
    checkpwd(){
      var pwd= document.getElementById("pwd").value;
      var p_user = document.getElementById("p_user");
      var reg_pwd = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/;
      var flag = reg_pwd.test(pwd);

      if(flag){
        p_user.innerHTML = "格式正确";
        this.flag3=true;
      }
      else {
        p_user.innerHTML = "密码格式有误！";
        this.flag3=false;
    }
    return flag;
    },
    confirmpwd(){
      var pwd= document.getElementById("pwd").value;
      var repwd= document.getElementById("repwd").value;
      var r_user=document.getElementById("r_user");

      if(pwd!=repwd){
        r_user.innerHTML = "两次密码不一致";
        this.flag4=false;
      }
      else {
        r_user.innerHTML = "";
        this.flag4=true;
    }
    },
    register() {
      if(this.flag1!=false&&this.flag2!=false&&this.flag3!=false&&this.flag4!=false)
      request.post("http://localhost:8081/seller/register", this.registerParams).then(res => {
        if (res.flag) {
          router.push({path: '/background/login'});
        }
      })
    }
  },
  created() {
    request.post("http://localhost:8081/seller/registerComfirm").then(res => {
      if(!res.data){
        this.show = false;
      }else {
        this.show = true;
      }
    })
  }
}


</script>

<style scoped>
@import "../pages/regiest.css";
</style>

