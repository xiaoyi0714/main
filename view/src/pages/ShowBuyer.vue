<template>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="name" label="买家姓名"/>
      <el-table-column prop="tele" label="电话号码"/>
      <el-table-column prop="tradeTime" label="交易时间"/>
      <el-table-column prop="tradePlace" label="交易地点"/>

      <el-table-column label="操作" prop="compile">
            <template>
              <el-button type="button" size="mini" @click="handlePass(row, $index)">选择</el-button>
            </template>
          </el-table-column>

    </el-table>
  </template>

  <script>
  import request from "@/utils/request";
  export default {
    name:"ShowBuyer",
    data() {
      return{
        tableData:[]
      }
    },
    methods:{
      ShowBuyer(){
        request.post("http://localhost:8081/seller/intendingUSer?goods_id="+1, this.registerParams).then(res => {
          this.tableData=res.data
        })
      }
    },
    created() {
      this.ShowBuyer();
    }

  }

  </script>

  <style scoped>

  </style>
