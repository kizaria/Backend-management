import http from '@/http'
import type { Banner, BannerListParm } from './BannerModel'

//查询商品
export let BannerGetGoods=()=>{
    return http.get("/api/banner/getIndexBanner")
}

//新增
export let addBannerApi=(parm:Banner)=>{
    return http.post("/api/banner",parm)
}

//列表
export let getBannerListApi=(parm:BannerListParm)=>{
    return http.get("/api/banner/list",parm)
}

//新增
export let upanddownBannerApi=(parm:Banner)=>{
    return http.post("/api/banner/upanddown",parm)
}

//编辑
export let editBannerApi=(parm:Banner)=>{
    return http.put("/api/banner",parm)
}

//删除
export let deleteBannerApi=(parm:Banner)=>{
    return http.delete(`/api/banner/${parm.banId}`)
}
