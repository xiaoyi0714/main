<template>
  <div>
      <Header />
    <div style="display: flex">
      <Aside />
      <router-view style="flex: 1;"/>
    </div>
  </div>
</template>

<script>
import Header from '../components/background/Header'
import Aside from '../components/background/Aside'
export default {
  name: "BackgroundHome",
  components:{
    Header,
    Aside
  }
}
</script>

<style scoped>
</style>
