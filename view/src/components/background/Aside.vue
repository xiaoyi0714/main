<template>
  <el-row class="tac">
  <el-col :span="12">
    <el-menu
        active-text-color="#ffd04b"
        background-color="#545c64"
        class="el-menu-vertical-demo"
        default-active="2"
        text-color="#fff"
        @open="handleOpen"
        @close="handleClose"
    >

      <el-sub-menu index="1">
        <template #title>
          <el-icon>
            <location/>
          </el-icon>
          <span>商品模块</span>
        </template>
        <el-menu-item-group>
          <router-link to="/background/publishGoods">
          <el-menu-item index="1-1">发布商品</el-menu-item>
          </router-link>
          <router-link to="/background/goods">
          <el-menu-item index="1-2">未交易完成商品</el-menu-item>
          </router-link>
          <router-link to="/background/goodsRecords">
          <el-menu-item index="1-3">查看历史商品</el-menu-item>
          </router-link>
<!--          <router-link to="/background/showBuyer">-->
<!--          <el-menu-item index="1-4">查看意向购买人</el-menu-item>-->
<!--          </router-link>-->
<!--          <router-link to="/background/BuyerAlreadySelect">-->
<!--          <el-menu-item index="1-5">查看已选交易人</el-menu-item>-->
<!--          </router-link>-->
        </el-menu-item-group>
      </el-sub-menu>
      <el-sub-menu index="2">
        <template #title>
          <el-icon>
            <icon-menu/>
          </el-icon>
          <span>管理员模块</span>
        </template>
        <el-menu-item-group>
          <router-link to="/background/changePassword">
          <el-menu-item index="2-1">修改密码</el-menu-item>
          </router-link>
        </el-menu-item-group>
      </el-sub-menu>
    </el-menu>
  </el-col>
  </el-row>
</template>

<script>
import {ref} from "vue";
import {
  Menu as IconMenu,
  Location,
} from '@element-plus/icons-vue'

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Aside",
  components: {
    IconMenu,
    Location
  },
  data() {
    return {
      isCollapse: ref(true),
    }
  }
}
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: calc(100vh - 50px);
}
</style>
