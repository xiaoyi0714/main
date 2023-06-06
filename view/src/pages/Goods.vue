<template>
  <div class="div">
    <el-descriptions
        class="margin-top"
        title="未交易完成的商品列表"
        :column="1"
        size="mediun"
        border
    >
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <user/>
            </el-icon>
            商品名称
          </div>
        </template>
        {{ tableData[0].name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <user/>
            </el-icon>
            图片
          </div>
        </template>
        <img
            :src="'data:image/png;base64,'+tableData[0].imgurl"
            class="image"
        />
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <user/>
            </el-icon>
            商品价格
          </div>
        </template>
        {{ tableData[0].price }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <user/>
            </el-icon>
            商品描述
          </div>
        </template>
        {{ tableData[0].description }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <user/>
            </el-icon>
            上架时间
          </div>
        </template>
        {{ tableData[0].supplyTime }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <user/>
            </el-icon>
            状态
          </div>
        </template>
        {{ tableData[0].StrStatus }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <user/>
            </el-icon>
            商品操作
          </div>
        </template>
        <el-button @click="freezeGoods" v-show="tableData[0].status == 0">冻结</el-button>
        <el-button @click="select" v-show="tableData[0].status == 1">查看交易人</el-button>
        <el-button @click="dialogVisible = true" v-show="tableData[0].status == 1"> 交易成功下架商品</el-button>
        <el-button @click="dialogVisible2 = true" v-show="tableData[0].status == 1">交易失败重新上线商品</el-button>
        <el-button @click="removeGoods2" v-show="tableData[0].status == 2"> 下架商品</el-button>
        <el-button @click="putawayGoods2" v-show="tableData[0].status == 2">上线商品</el-button>
        <el-badge :value="number" :max="99">
        <el-button @click="Showbuyer()" v-show="tableData[0].status == 0 || tableData[0].status == 1">查看意向购买人信息</el-button>
        </el-badge>
      </el-descriptions-item>
    </el-descriptions>

    <el-dialog
        v-model="dialogVisible"
        title="Tips"
        width="30%"
    >
      <span>请再次确认是否交易成功下架商品</span>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="removeGoods">
        确定</el-button
        >
      </span>
      </template>
    </el-dialog>

    <el-dialog
        v-model="dialogVisible2"
        title="Tips"
        width="30%"
    >
      <span>请再次确认是否交易失败重新上线商品</span>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取消</el-button>
        <el-button type="primary" @click="putawayGoods">
        确定</el-button
        >
      </span>
      </template>
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


    <el-dialog v-model="wantbuy" title="查看信息" :before-close="close">
      <el-table
          :data="tableDat"
          ref="table"
          @row-click="singleElection"
          :disabled="dis"
      >
        <el-table-column
            label="选择"
        >
          <template v-slot:default="scope">
            <el-radio
                :label="scope.$index"
                :disabled="dis"
                v-model="radio">
            </el-radio>
          </template>
        </el-table-column>
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
            label="交易成功"
            width="180">
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>

</template>

<script>
import {ref} from "vue";
import request from "@/utils/request";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Goods",
  data() {
    return {
      number: 0,
      dis: false,
      check:true,
      radio: "",
      radioRow: [],
      tradeid:[],
      goods_id:[],
      flag: '',
      orderList: [
        {
          neme: '',
          tele: '',
          tradeTime: '',
          tradePlace: '',
        },
        {
          neme: '',
          tele: '',
          tradeTime: '',
          tradePlace: '',
        }
      ],
      dialogFormVisible: false,
      dialogVisible: false,
      dialogVisible2: false,
      buyButtonFlag: true,
      // comfirm: false,
      wantbuy: false,
      loading: ref(false),
      currentDate: new Date().toDateString(),
      tableData: [
        {
          imgurl: '',
        }
      ],
      userinfo: {},
      tableDat: []
    }
  },
  created() {
    this.getGoodsInfo();
    this.checkgood();
    setTimeout(()=>{
      this.getNumber();
    },1000)
  },
  methods: {
    checkgood(){
      request.get("http://localhost:8081/seller/checkgoods").then(res => {
        this.dis=!res.data;
        this.check=res.data;
      })
    },
    singleElection(row) {
        if(this.check==true){
          this.dis=true;
          this.check=false;
          this.radioRow=row.name;
        alert("您选择了"+this.radioRow);
        this.radio = this.tableDat.indexOf(row);
        this.tradeid = row.tradeid;
        this.goods_id=row.id;
        request.get("http://localhost:8081/seller/chooseUser?id=" + this.tradeid+"&goods_id="+this.goods_id).then(res => {
          if (res.flag) {
            this.$message({
              type: 'success',
              message: res.msg
            })
          } else {
            this.$message.error(res.msg);
          }
        })
      }
        else{
        alert("您已经选择"+this.radioRow+",无法再次选择");
      }
    },
    getGoodsInfo() {
      this.dialogVisible2 = false;
      request.get("http://localhost:8082/goods/getGoodsList").then(res => {
        if (res.flag) {
          this.tableData = res.data.list;
          this.switchString(this.tableData[0].status);
          if (this.tableData[0].status != 0) {
            this.buyButtonFlag = false;
          } else {
            this.buyButtonFlag = true;
          }
          this.$message({
            type: 'success',
            message: res.msg
          })
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    removeGoods() {
      this.dialogVisible = false;
      request.post("http://localhost:8081/goods/removeGoods?goodsId=" + this.tableData[0].id).then(res => {
        if (res.flag) {
          this.$message({
            type: 'success',
            message: res.msg
          })
        } else {
          this.$message.error(res.msg);
        }
      }).finally(()=>{
        this.getGoodsInfo();
        this.checkgood();
      })
    },
    putawayGoods() {
      request.post("http://localhost:8081/goods/putawayGoods?goodsId=" + this.tableData[0].id).then(res => {
        if (res.flag) {
          this.$message({
            type: 'success',
            message: res.msg
          })
        } else {
          this.$message.error(res.msg);
        }
      }).finally(() => {
        this.dialogVisible2 = false;
      }).finally(()=>{
        this.getGoodsInfo();
        this.checkgood();
      })
    },
    removeGoods2() {
      this.dialogVisible = false;
      request.post("http://localhost:8081/goods/removeGoods2?goodsId=" + this.tableData[0].id).then(res => {
        if (res.flag) {
          this.$message({
            type: 'success',
            message: res.msg
          })
        } else {
          this.$message.error(res.msg);
        }
      }).finally(()=>{
        this.getGoodsInfo();
        this.checkgood();
      })
    },
    putawayGoods2() {
      request.post("http://localhost:8081/goods/putawayGoods2?goodsId=" + this.tableData[0].id).then(res => {
        if (res.flag) {
          this.$message({
            type: 'success',
            message: res.msg
          })
        } else {
          this.$message.error(res.msg);
        }
      }).finally(() => {
        this.dialogVisible2 = false;
      }).finally(()=>{
        this.getGoodsInfo();
        this.checkgood();
      })
    },
    switchString(status) {
      if (status == 0) {
        this.tableData[0].StrStatus = '未交易';
      } else if (status == 1) {
        this.tableData[0].StrStatus = '交易中';
      } else if (status == 2) {
        this.tableData[0].StrStatus = '冻结中';
      } else if (status == 3) {
        this.tableData[0].StrStatus = '下架了';
      }
    },
    select() {
      this.dialogFormVisible = true
      request.get("http://localhost:8081/Order/getTraders?id=" + this.tableData[0].id).then(res => {
        if (res.flag) {
          this.userinfo = res.data;
          this.$message({
            type: 'success',
            message: res.msg
          })
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    freezeGoods() {
      request.post("http://localhost:8081/goods/freezeGoods?goodsId=" + this.tableData[0].id).then(res => {
        if (res.flag) {
          this.$message({
            type: 'success',
            message: res.msg
          })
        } else {
          this.$message.error(res.msg);
        }
      }).finally(()=>{
        this.getGoodsInfo();
        this.checkgood();
      })
    },
    Showbuyer() {
      this.wantbuy = true
      request.get("http://localhost:8081/seller/intendingUSer?goods_id=" + this.tableData[0].id).then(res => {
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
    },
    getNumber(){
      request.get("http://localhost:8081/seller/getTradecount?goodsId=" + this.tableData[0].id).then(res => {
        if (res.flag) {
          this.number = res.data - this.tableData[0].status;
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

.image {
  width: 100px;
  height: 100px;
}

.div {
  margin-top: 10PX;
}

.div1 {
  margin-right: 55%;
  margin-top: 20px;
}

.div2 {
  margin-left: 50%;
  margin-top: -260px;
}

.line {
  position: absolute;
  display: inline-block;
  background: rgb(17, 17, 17);
  margin-left: 45%;
  width: 2px;
  height: 330px;
}
</style>
