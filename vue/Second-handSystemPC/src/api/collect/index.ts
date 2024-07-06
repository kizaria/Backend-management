import http from "@/http";
import type { CollectParm, GoodsCollect } from "./collectModel";

//人气推荐
export let getGoodsHot=()=>{
    return http.get('/api/collect/GoodsHot')
}

//收藏人数
export let getCountApi=(goodsId:any)=>{
    return http.get('/api/collect/getCount',goodsId)
}

//收藏
export let getMyCollectApi=(parm:CollectParm)=>{
    return http.get('/api/collect/getMyCollect',parm)
}

//我的收藏
export let collectApi=(parm:GoodsCollect)=>{
    return http.post('/api/collect/collect',parm)
}

//判断是否收藏
export let hasCollectApi=(parm:GoodsCollect)=>{
    return http.get('/api/collect/hasCollect',parm)
}