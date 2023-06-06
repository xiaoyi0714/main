<template>
  <div class="div1">
    <div class="div2">后台管理</div>
    <div style="flex: 1"></div>
    <div class="div3">
      <el-dropdown>
    <span class="el-dropdown-link">
      {{ name }}
      <el-icon class="el-icon--right">
        <arrow-down/>
      </el-icon>
    </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item divided @click="logout">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import router from "@/router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  data(){
    return{
      name: '',
    }
  },
  methods:{
    getUser(){
      request.get("http://localhost:8081/seller/getLoginUser").then(res =>{
        if(res.flag){
          this.name = res.data.name
          this.id = res.data.id
          this.$message({
            type: 'success',
            message: res.msg
          })
        }else {
          this.$message.error(res.msg);
        }
      })
    },
    logout(){
      localStorage.removeItem('token')
      request.get("http://localhost:8081/seller/logout").then(res =>{
        if(res.flag){
          this.$message({
            type: 'success',
            message: res.msg
          })
        }
      })
      router.push({path: '/background/login'});
    }
  },
  created() {
    this.getUser()
  }
}
</script>

<style scoped>
.div1 {
  background:  #f0f9eb;
  height: 80px;
  line-height: 30px;
  border-bottom: 1px solid #11cd40;
  display: flex
}

.div2 {
  width: 200px;
  padding-left: 30px;
  font-weight: bold;
  color: blue;
}

.div3 {
  width: 100px;
  padding-top: 10px;
  font-weight: bold;
  color: blue;
}
</style>
