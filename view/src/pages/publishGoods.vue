<template>
  <div class="div">
    <div v-show="goodsExists">
      <h1 class="h1">已经发布过商品了，不能继续发布了</h1>
    </div>
    <div class="div1" v-show="!goodsExists">
      <el-form ref="form" :model="form" label-width="120px" :rules="rules">
        <el-form-item label="商品名称" class="item" prop="goodsName">
          <el-input v-model="form.goodsName"/>
        </el-form-item>
        <el-form-item label="类别" class="item" prop="goodsType">
          <el-select v-model="form.goodsType">
            <el-option
                v-for="t in type"
                :key="t"
                :label="t"
                :value="t"/>
          </el-select>
        </el-form-item>
        <el-form-item label="价格" class="item" prop="goodsPrice">
          <el-input placeholder="0.00" @keydown="handleInput2" v-model="form.goodsPrice"/>
        </el-form-item>
        <el-form-item label="描述" class="item" prop="goodsDescription">
          <el-input v-model="form.goodsDescription" type="textarea"/>
        </el-form-item>
        <el-form-item label="图片" class="item" prop="img">
          <el-upload id="pic" action="#"
                     list-type="picture-card"
                     :auto-upload="false"
                     :on-change="handleChange"
                     :before-upload="beforeUpload"
                     ref="upload"
                     :on-remove="handleRemove"
                     :limit="1"
                     :class="{ disabled: uploadDisabled }">
            <el-icon>
              <Plus/>
            </el-icon>
          </el-upload>
        </el-form-item>
        <div class="bnt">
          <el-form-item>
            <el-button type="primary" @click="onSubmit">发布</el-button>
            <el-button>取消</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {Plus} from '@element-plus/icons-vue'
import request from "@/utils/request";
import {reactive} from "vue";
import {ElMessage} from "element-plus";

export default {
  name: "publishGoods",
  components: {
    Plus,
  },
  data() {
    const validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入商品名称'));
      }else if(value.toString().match("[^\u4e00-\u9fa5a-zA-Z0-9]")){
        callback(new Error('商品名称填写有误，不能包含特殊字符'));
      } else {
        callback();
      }
    };
    const validatePrice = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入商品价格'));
      } else {
        if (value.toString().match("[^0-9\\.]") || value.toString().match("^\\.")) {
          callback(new Error('请输入正确的商品价格'));
        } else {
          callback();
        }
      }
    };
    const validateGoodsType = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请选择商品类型'));
      } else {
        callback();
      }
    };
    return {
      goodsExists: false,
      uploadDisabled: false,
      type: [],
      form: {
        goodsName: '',
        goodsType: '',
        goodsPrice: '',
        goodsDescription: '',
      },
      rules: reactive({
        goodsName: [{required: true, validator: validateName, trigger: 'blur'},
          {min: 2, max: 20, message: '长度必须在2-20之内', trigger: 'blur'}],
        goodsPrice: [{required: true, validator: validatePrice, trigger: 'blur'}],
        goodsType: [{required: true, validator: validateGoodsType, trigger: 'change'}],
        goodsDescription: [{required: true, message: '商品描述不能为空', trigger: 'change'},
          {max: 100, message: '长度不能大于100', trigger: 'blur'}],
      })
    }
  },
  methods: {
    beforeUpload(file) {
      if (file.type !== 'image/jpeg') {
        ElMessage.error('图片格式不是jpeg格式的!')
        return false
      } else if (file.size / 1024 / 1024 > 4) {
        ElMessage.error('图片大小超过了4MB!')
        return false
      } else {
        let formData = new FormData();
        formData.append("goodsName", this.form.goodsName)
        formData.append("img", file)
        formData.append("goodsDescription", this.form.goodsDescription)
        let price = this.form.goodsPrice
        let index = price.indexOf(".");
        if (index === -1) {
          price = price + ".00"
        } else {
          let len = price.length;
          let poor = len - index - 1;
          if (poor < 2) {
            for (var i = 0; i < 2 - poor; i++) {
              price = price + "0";
            }
          }
        }
        formData.append("goodsPrice", price)
        formData.append("goodsType", this.form.goodsType)
        request.post("http://localhost:8081/seller/publishGoods", formData).then(res => {
          if (res.flag) {
            this.$message({
              type: 'success',
              message: res.msg
            })
          } else {
            this.$message.error(res.msg);
          }
        }).finally(() => {
          request.get("http://localhost:8081/goods/checkGoodsExists").then(res => {
            if (res.data == 1) {
              this.goodsExists = true;
            } else {
              this.goodsExists = false;
            }
          });
          this.form = {
            goodsName: '',
            goodsType: '',
            goodsPrice: '',
            goodsDescription: '',
          }
        })
        return false;
      }
    },
    handleChange() {
      this.uploadDisabled = true;
    },
    handleRemove() {
      this.uploadDisabled = false;
    },
    onSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$refs.upload.submit()
        }
      })
    },
    initType() {
      request.get("http://localhost:8082/vegetablesType/getAllType").then(res => {
        if (res.flag) {
          this.type = res.data
          this.$message({
            type: 'success',
            message: res.msg
          })
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    handleInput2(e) {
      // 通过正则过滤小数点后两位
      e.target.value = (e.target.value.match(/^\d*(\.?\d{0,1})/g)[0]) || null
    },
  },
  created() {
    request.get("http://localhost:8081/goods/checkGoodsExists").then(res => {
      if (res.data == 1) {
        this.goodsExists = true;
      } else {
        this.goodsExists = false;
      }
    });
    this.initType();
  }
}
</script>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.div {
  background-color: #E0EEE0;
}

.div1 {
  position: absolute;
  left: 30%;
  right: 25%;
  top: 20%;
  bottom: 10%;
  text-align: center;
  margin-top: 30px;
}

.item {
  width: 500px;
  margin-top: 30px;
  margin-left: 25px;
}

.bnt {
  position: absolute;
  left: 20%;
  bottom: 10px;
}

.h1 {
  text-align: center;
  margin-top: 20px;
}
</style>

<style>

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.disabled .el-upload--picture-card {
  display: none;
}
</style>
