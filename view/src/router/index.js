import { createRouter, createWebHistory } from 'vue-router'
import Login from '../components/background/Login'
import Main from '../components/Main.vue'
import Home from '../components/front/Home'
import BackgroundHome from "@/views/BackgroundHome";
import Welcome from '../pages/BackgroundWelcome'
import Content from '../components/background/Content'
import PublishGoods from '../pages/publishGoods'
import ChangePassword from "@/pages/ChangePassword";
import GoodsRecords from "@/pages/GoodsRecords";
import ShowBuyer from "@/pages/ShowBuyer";
import BuyerAlreadySelect from "@/pages/BuyerAlreadySelect";
import Goods from '../pages/Goods'
import registery from '../pages/regiest'
const routes = [
    {
        path: '/',
        name: 'Main',
        component: Main
    },
    {
        path: '/background/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/registry',
        name: 'registery',
        component: registery
    },
    {
        path: '/front/home',
        name: 'Home',
        component: Home
    },
    {
        path: '/background',
        name: 'BackgroundHome',
        component: BackgroundHome,
        meta:{
          requiredToken: true
        },
        children:[
            {
                path: 'welcome',
                component: Welcome,
            },
            {
                path: 'content',
                component: Content,
            },
            {
                path: 'publishGoods',
                component: PublishGoods
            },
            {
                path: 'changePassword',
                component: ChangePassword
            },
            {
                path: 'goodsRecords',
                component: GoodsRecords
            },
            {
                path: 'goods',
                component: Goods
            },
            {
                path: 'showbuyer',
                component: ShowBuyer
            },
            {
                path: 'buyerAlreadySelect',
                component: BuyerAlreadySelect
            }
        ]
    }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

//全局路由设置，实现登录拦截
router.beforeEach((to,from,next)=>{
    if(to.meta.requiredToken){
        if(localStorage.getItem('token') != null){
            next()
        }else {
            next({path:'/background/login'})
        }
    }else {
        next()
    }
})

export default router
