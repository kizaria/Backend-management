
import { defineStore } from 'pinia'

export let NoticeStore = defineStore('NoticeStore', {
    // 动作
    actions:{
        setisNotice(isNotice:any){
          this.isNotice=isNotice;
        }
    },
    
    // 状态
    state:()=>{
      return {
        isNotice:false
      }
    },
    // 计算
    getters:{
        getisNotice(state){
            return state.isNotice;
        }
    },
    persist: true,
    // persist: {
    //   storage: sessionStorage,  关闭浏览器就清楚
    // },


  })