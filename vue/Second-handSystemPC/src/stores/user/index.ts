
import { defineStore } from 'pinia'

export let UserStore = defineStore('UserStore', {
    // 动作
    actions:{
        setUserId(userId:any){
          this.userId=userId;
        },
        setToken(token:any){
          this.token=token;
        },
        setNickName(nickName:any){
          this.nickName=nickName;
        },
        setavatar(avatar:any){
          this.avatar=avatar;
        },
        setinfo(){
          this.userId='',
          this.nickName='',
          this.avatar='',
          this.token=''
        }
    },
    
    // 状态
    state:()=>{
      return {
        userId:'',
        nickName:'',
        avatar:'',
        token:''
      }
    },
    // 计算
    getters:{
        getUserId(state){
            return state.userId;
        },
        getToken(state){
          return state.token;
        },
        getNickName(state){
          return state.nickName;
        },
        getavatar(state){
          return state.avatar;
        },
    },
    persist: true,
    // persist: {
    //   storage: sessionStorage,  关闭浏览器就清楚
    // },


  })