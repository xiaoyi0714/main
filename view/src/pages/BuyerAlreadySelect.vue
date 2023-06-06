<template>
    <div id="con">
        <div id="div1">
      <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="name" label="买家姓名"/>
      <el-table-column prop="tele" label="电话号码"/>
      <el-table-column prop="block" label="交易时间"/>
      <el-table-column prop="tradePlace" label="交易地点"/>

    </el-table>
    </div>
    <div id="div2">
      <h5>买家申请修改信息</h5>
      <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="name" label="买家姓名"/>
      <el-table-column prop="tele" label="电话号码"/>
      <el-table-column prop="block" label="交易时间"/>
      <el-table-column prop="tradePlace" label="交易地点"/>
      <el-table-column label="操作" prop="compile">
            <template>
              <el-button type="button" size="mini" @click="handlePass(row, $index)">同意</el-button>
            </template>
          </el-table-column>

    </el-table>
    </div>
    </div>
    
  </template>

  
  <script>
  export default {
    data() {
      return {
        tableData: [{
          name: '王小虎',
          tele:1234567,
          block:'2022-09-09',
          tradePlace:'上海市普陀区金沙江路 1517 弄',
        }]
      }
    }
  }

  </script>
  
  <style scoped>
  #con{
    position: relative;
  }
  #div2{
    margin-top: 200px;
  }
  </style>
  