<template>
  <img src="../assets/img4.jpg" id="pic">
  <div id="div">
    <div>
      <h1 id="title">欢迎来到我们真菜</h1>
      <el-button class="btn" @click="redirectToFront">小趴菜商城</el-button>
      <br>
      <el-button class="btn1" @click="redirectToLogin">卖家管理后台</el-button>
    </div>
  </div>
</template>

<script>
import router from "@/router";
import request from "@/utils/request";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Main",
  methods: {
    redirectToLogin() {
      request.get("http://localhost:8081/seller/registerComfirm").then(res => {
            if (!res.data) {
              router.push({path: '/background/login', query: {date: new Date().getTime()}});
            } else {
              router.push({path: '/registry'});
            }
          }
      )
    },
    redirectToFront() {
      router.push({path: '/front/home'});
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#pic {
  position: absolute;
  right: 0%;
  top: 0%;
  bottom: 0%;
}

#div {
  position: absolute;
  left: 30%;
  right: 30%;
  top: 0%;
  bottom: 0%;
  background: linear-gradient(to bottom right, #0783c1, #6abedc, #f6f6f6);
  border: 10px inset rgb(40, 235, 50);
  box-shadow: 0px -10px 10px 0px #ff0000,
  -10px 0px 10px 0px #3bee17,
  10px 0px 10px 0px #2279ee,
  0px 10px 19px 0px #eede15;


}

#title {
  text-align: center;
  margin-top: 100px;
}

.btn {
  transform: scale(1);
  margin-left: 200px;
  margin-top: 70px;
  width: 200px;
  height: 50px;
  background: orange;
  padding: 15px 24px;
  font-size: 1.4rem;
  outline: none;
  box-shadow: -6px 6px 0 hsl(132, 78%, 45%);
}

.btn1 {
  transform: scale(1);
  margin-left: 200px;
  margin-top: 70px;
  width: 200px;
  height: 50px;
  background: orange;
  padding: 15px 24px;
  font-size: 1.4rem;
  outline: none;
  box-shadow: -6px 6px 0 hsl(132, 78%, 45%);
}
</style>
