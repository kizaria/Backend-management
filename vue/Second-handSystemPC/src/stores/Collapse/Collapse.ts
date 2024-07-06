import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const CollapseStore = defineStore('CollapseStore', {
    // 动作
    actions:{
        setCollapse(collapse:any){
            this.collapse=collapse;
        }

    },
    // 状态
    state(){
      return {
        collapse:false
      }
    },
    // 计算
    getters:{
        getCollapse(state){
            return state.collapse;
        }
    }


  })