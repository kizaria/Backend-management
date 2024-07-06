import http from '@/http'
import type { Goods, GoodsListParm, UpdateParm ,Parm} from './GoodsModel'

//列表
export let getGoodsListApi=(parm:GoodsListParm)=>{
    return http.get("/api/goods/list",parm)
}
//上架下架
export let upanddownApi=(parm:UpdateParm)=>{
    return http.post("/api/goods/upanddown",parm)
}
//推荐首页
export let setIndexApi=(parm:UpdateParm)=>{
    return http.post("/api/goods/setIndex",parm)
}
//编辑
export let editGoodsApi=(parm:Goods)=>{
    return http.put("/api/goods/edit",parm)
}

//删除
export let deleteApi=(parm:UpdateParm)=>{
    return http.post("/api/goods/delete",parm)
}

//小程序分类接口对接
export let categoryApi=()=>{
	return http.get('/api/category/WxSelectList')
}

//新鲜好物
export let getGoodsNew=()=>{
    return http.get('/api/goods/GoodsNew')
}

//产品列表
export let getGoodsProduct=()=>{
    return http.get('/api/home/GoodsProduct')
}

//闲置分类列表
export let getUsedListApi=(parm:Parm)=>{
    return http.get('/api/goods/getUsedList',parm)
}

//获取商品详情页面
export let getGoodsById=(goodsId:any)=>{
    return http.get('/api/goods/getGoodsById',goodsId)
}

//获取图片
export let getImageApi=(goodsId:any)=>{
    return http.get('/api/goods/getImage',goodsId)
}

//发布商品
export let releaseApi=(parm:Goods)=>{
    return http.post('/api/goods/release',parm)
}

//我的闲置
export let getMyUnusedListApi=(parm:any)=>{
    return http.get('/api/goods/getMyUnusedList',parm)
}

//搜索结果
export let searchGoodsApi=(name:any)=>{
    return http.get('/api/goods/searchGoods',name)
}