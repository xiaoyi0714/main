<template>
  <div id="div2">
    <p class="word1">订单编号:</p>
    <div class="look">
      <form label="订单编号" label-width="160px" name="f">
        <input type="text" id="num" class="t1">
        <input type="button" class="b2" @click="check" value="查看">
      </form>
    </div>
    <img src="../../assets/img7.jpg" id="pic">
    <div id="div1">
      <h1 id="title">欢迎来到我们真菜</h1>
      <el-row type="flex" class="pic1">
        <el-col
            v-for="data in tableData"
            :key="data"
            :span="8"
        >
          <el-card :body-style="{ padding: '0px' }">
            <!--        v-show 决定哪张图片出现-->
            <img
                v-show="buyButtonFlag"
                :src="'data:image/png;base64,'+data.imgurl"
                class="image"
            />
            <img
                v-show="!buyButtonFlag"
                :src="'data:image/png;base64,'+data.imgurl"
                style="-webkit-filter: grayscale(100%);filter: grayscale(100%);"
                class="image"
            />
            <div style="padding: 1px">

              <div>
                <el-button backgroud=" #d1edc4" text class="bottom card-header" @click="dialogFormVisible = true"
                           v-show="buyButtonFlag">购买
                </el-button>

              </div>

            </div>


          </el-card>
        </el-col>
        <el-col
            v-for="data in tableData"
            :key="data"
            :span="8"
        >
          <div id="name">商品名称:{{ data.name }}</div>
          <div id="price">价格:{{ data.price }}</div>
          <div id="price">商品信息:{{ data.description }}</div>
        </el-col>
      </el-row>


      <el-dialog v-model="readimf" title="查看信息" :before-close="close1" class="dialog1">
        <template #footer>
          <div class="div3">
            <img :src="'data:image/png;base64,'+order.goods.imgurl" class="img3">
            <p>名称:{{ order.goods.name }}</p>
            <p>价格:{{ order.goods.price }}</p>
            <p>描述:{{ order.goods.description }}</p>
          </div>
          <div class="div2">
            <p>姓名:{{ order.user.name }}</p>
            <p>电话:{{ order.user.tele }}</p>
            <p>交易时间:{{ order.tradeAction.tradeTime }}</p>
            <p>交易地点:{{ order.tradeAction.tradePlace }}</p>
          </div>
          <span>
        <el-button @click="close1" class="b3">关闭</el-button>
      </span>
        </template>
      </el-dialog>

      <el-dialog v-model="readimf1" title="查看信息" :before-close="close">
        <h1>未查到此编号</h1>
        <template #footer>
      <span>
        <el-button @click="close2">关闭</el-button>
      </span>
        </template>
      </el-dialog>

      <el-dialog v-model="getid" title="查看编号" :before-close="close">
        <div id="name">订单编号:{{ id }}</div>
        <template #footer>
      <span>
        <el-button @click="close3">关闭</el-button>
      </span>
        </template>
      </el-dialog>


      <el-dialog v-model="dialogFormVisible" title="购买" :before-close="close">
        <el-form :model="form" ref="buyForm" :rules="rules">
          <el-form-item label="用户姓名" label-width="140px" prop="user.name">
            <el-input v-model="form.user.name" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="电话号码" label-width="140px" prop="user.tele">
            <el-input v-model="form.user.tele" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="交易时间" label-width="140px" prop="tradeTime">
            <div class="block">
              <span class="demonstration"></span>
              <el-date-picker
                  value-format="YYYY-MM-DD HH:mm:ss"
                  v-model="form.tradeTime"
                  type="datetime"
                  placeholder="Select date and time"
                  :disabled-date="disabledDate"
              />
            </div>
          </el-form-item>
          <el-form-item label="交易地点" label-width="140px" prop="tradePlace">
            <el-input v-model="form.tradePlace" autocomplete="off"/>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="buyGoods()">购买</el-button>
      </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>


import {reactive, ref} from "vue";
import request from "@/utils/request";


export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Home",
  data() {
    const validateName = (rule, value, callback) => {
      var reg = /^[\u4E00-\u9FA5]{2,4}$/;
      if (value === '') {
        callback(new Error('姓名不能为空'));
      } else if (!reg.test(value)) {
        callback(new Error('请输入二到五位的中文字符'))
        this.$message.error("请输入二到五位的中文字符");
      } else {
        callback();
      }
    };
    const validateTele = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请填写电话号码'));
      } else if (!value.toString().match(new RegExp("^1[3-9]\\d{9}$"))) {
        callback(new Error('电话号码有误，请重新填写'));
        this.$message.error("电话号码有误，请重新填写");
      } else {
        callback();
      }
    };
    const validateDate = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('交易时间不能为空'));
      } else {
        callback();
      }
    };
    const validatePlace = (rule, value, callback) => {
      var reg = /^[0-9a-zA-Z\u4E00-\u9FA5_]{1,31}$/;
      if (value === '') {
        callback(new Error('交易地点不能为空'));
      } else if (!reg.test(value)) {
        callback(new Error('交易地址输入不符合规范'))
        this.$message.error("交易地址输入不符合规范");
      } else {
        callback();
      }
    };

    return {
      disabledDate(time) {
        return time.getTime() < Date.now() - 8.64e7;//禁止选择今天以前的时间
      },
      // flag: true,
      number: {
        user: {
          id: '12',
        }
      },
      order: {},
      buyButtonFlag: true,
      loading: ref(false),
      currentDate: new Date().toDateString(),
      tableData: [],
      dialogFormVisible: ref(false),
      readimf: ref(false),
      readimf1: ref(false),
      getid: ref(false),
      form: {
        user: {
          name: '',
          tele: '',
        },
        tradeTime: '',
        tradePlace: '',
      },
      userinfo: {},
      tableDat: [],
      id: '',
      rules: reactive({
        user: {
          name: [
            {validator: validateName, trigger: 'blur'},
            {required: true,}
          ],
          tele: [
            {validator: validateTele, trigger: 'blur'},
            {required: true,}
          ],
        },
        tradeTime: [
          {validator: validateDate, trigger: 'blur'},
          {required: true,}
        ],
        tradePlace: [
          {validator: validatePlace, trigger: 'blur'},
          {required: true,}
        ],
      })
    }
  },
  created() {
    this.getGoodsInfo();
  },
  methods: {
    check() {
      let num = document.getElementById("num").value;
      if (num == '') {
        alert("编号不能为空")
      } else {
        request.get("http://localhost:8082/order/getOrder?id=" + num).then(res => {
          if (res.flag) {
            this.order = res.data;
            this.readimf = true
          } else {
            this.readimf1 = true
          }
        })
      }
    },
    getGoodsInfo() {
      request.get("http://localhost:8082/goods/getGoodsList").then(res => {
        this.tableData = res.data.list;
        if (this.tableData[0].status != 0) {
          this.buyButtonFlag = false;
        } else {
          this.buyButtonFlag = true;
        }
      })
    },
    buyGoods() {
      this.form.goodsId = this.tableData[0].id;
      this.$refs['buyForm'].validate((valid) => {
        if (valid) {
          request.post("http://localhost:8082/user/buy", this.form).then(res => {
            if (res.flag) {
              this.id = res.data;
              this.$message({
                type: 'success',
                message: res.msg
              });
              this.getid = true;
            } else {
              this.$message.error(res.msg);
            }
          }).finally(() => {
                this.dialogFormVisible = false;
                this.getGoodsInfo();
                this.form = {
                  user: {
                    name: '',
                    tele: '',
                  },
                  tradeTime: '',
                  tradePlace: '',
                }
              }
          )
        }
      })
    },
    close() {
      this.dialogFormVisible = false;
      // this.form = {
      //   user: {
      //     name: '',
      //     tele: '',
      //   },
      //   tradeTime: '',
      //   tradePlace: '',
      // };
    },
    close1() {
      this.readimf = false;
      this.form = {
        user: {
          name: '',
          tele: '',
        },
        tradeTime: '',
        tradePlace: '',
      };
    },
    close2() {
      this.readimf1 = false;
    },
    close3() {
      this.getid = false;
    }
  }
}
</script>

<style scoped>

* body {
  background: url("../../assets/img7.jpg");
  background-repeat: no-repeat;
  background-size: 100% 100%;
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
}


#title {
  text-align: center;
}

.bottom {
  background-image: url(../../assets/button-bg.jpg);
  margin-top: 13px;
  margin-left: 170px;
  height: 19px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.button1 {
  background-image: url(../../assets/button-bg.jpg);
  margin-top: 110px;
  margin-left: 100px;
  width: 110px;
  height: 25px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.image {
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
}

#div1 {
  position: absolute;
  left: 20%;
  right: 20%;
  top: 15%;
  bottom: 2%;
  border: 10px inset rgb(62, 69, 77);
  background: linear-gradient(to bottom right, #107cad, #0a96bc, #f6f6f6);
  box-shadow: 20px 20px 10px 3px #000; /*Chrome 6+, Firefox 4+, IE 9+, iOS 5+, Opera 10.50+*/
  -webkit-box-shadow: 10px 8px 10px 3px #000;
  -moz-box-shadow: 10px 8px 10px 3px #000;
  *background-clip: padding-box;
  opacity: 0.9; /*透明度*/
}

#pic {
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
}

.pic1 {
  margin-left: 150px;
}

#name {
  margin-left: 100px;
  margin-top: 10px;
}

#price {
  margin-left: 100px;
  margin-top: 50px;
}

.look {
  position: absolute;
  margin-left: 500px;
  margin-top: 400px;
}

.b2 {
  position: absolute;
  z-index: 1;
  background-image: url(../../assets/button-bg.jpg);
  margin-left: 480px;
  margin-top: 240px;
  width: 40px;
  height: 25px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.t1 {
  z-index: 1;
  position: absolute;
  margin-left: -130px;
  height: 20px;
  width: 200px;
}

.div2 {
  background-size: 100%;
  float: left;
  margin-top: -200px;
}

/* .div3{
  background-size: 100% ;
} */

.b3 {
  margin-bottom: 20px;
}

.img3 {
  height: 200px;
  width: 200px;
}

.word1 {
  position: absolute;
  margin-left: 840px;
  margin-top: 602px;

  z-index: 1;
}
</style>
