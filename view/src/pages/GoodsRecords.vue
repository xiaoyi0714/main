<template>
  <div>
    <el-table :data="tableData">
      <el-table-column prop="goods.name" label="商品名称"></el-table-column>
      <el-table-column prop="goods.description" label="商品描述"></el-table-column>
      <el-table-column prop="goods.price" label="价格"></el-table-column>
      <el-table-column prop="goods.supplyTime" label="上架时间"></el-table-column>
      <el-table-column label="交易人" align="center">
        <template v-slot:default="scope">
          <el-tooltip placement="right" v-if="scope.row.user">
            <template #content>
              <table>
                <tr>
                  <td>电话号码:</td>
                  <td>{{scope.row.user.tele}}</td>
                </tr>
                <tr>
                  <td>交易时间</td>
                  <td>{{scope.row.tradeAction.tradeTime}}</td>
                </tr>
                <tr>
                  <td>交易地点</td>
                  <td>{{scope.row.tradeAction.tradePlace}}</td>
                </tr>
              </table>
            </template>
            <el-tag type="success">{{ scope.row.user.name }}</el-tag>
          </el-tooltip>
          <el-tag type="danger" v-else>未选择交易人</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button @click="Showbuyer(scope.row)">查看意向购买人</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="wantbuy" title="查看信息" :before-close="close">
      <el-table
          :data="tableDat">
        <el-table-column
            prop="name"
            label="姓名"
            width="180">
        </el-table-column>
        <el-table-column
            prop="tele"
            label="电话"
            width="180">
        </el-table-column>
        <el-table-column
            prop="trade_time"
            label="交易时间"
            width="180">
        </el-table-column>
        <el-table-column
            prop="trade_place"
            label="交易地点"
            width="180">
        </el-table-column>
      </el-table>
    </el-dialog>
    <el-dialog v-model="dialogFormVisible" title="购买" :before-close="close">
      <el-descriptions title="交易信息" :column="2">
        <el-descriptions-item label="姓名:">{{ userinfo.name }}</el-descriptions-item>
        <el-descriptions-item label="电话号码:">{{ userinfo.tele }}</el-descriptions-item>
        <el-descriptions-item label="交易时间:">{{ userinfo.tradeTime }}</el-descriptions-item>
        <el-descriptions-item label="交易地点:">{{ userinfo.tradePlace }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false"
        >确定</el-button
        >
      </span>
      </template>
    </el-dialog>

  </div>
</template>


<script>
import request from "@/utils/request";

export default {
  name: "GoodsRecords",
  data() {
    return {
      userinfo: {},
      wantbuy: false,
      dialogFormVisible: false,
      tableData: [],
      tableDat: []
    }
  },
  created() {
    request.get("http://localhost:8081/goods/viewGoods").then(res => {
      this.tableData = res.data.list
      if (res.flag) {
        this.$message({
          type: 'success',
          message: res.msg
        })
      } else {
        this.$message.error(res.msg);
      }
    })
  },
  methods: {
    Showbuyer(row) {
      this.wantbuy = true
      request.get("http://localhost:8081/seller/intendingUSer?goods_id=" + row.goods.id).then(res => {
        if (res.flag) {
          this.tableDat = res.data;
          this.$message({
            type: 'success',
            message: res.msg
          })
        } else {
          this.$message.error(res.msg);
        }
      })

    }
  }

}
</script>

<style scoped>
.buttom1 {
  width: 40px;
  height: 20px;
  margin-left: 400px;
  background-color: blue;
}
</style>
