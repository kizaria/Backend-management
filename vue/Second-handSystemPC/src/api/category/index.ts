import http from "@/http";
import {type Category} from './CategoryModel'
import {type CategoryListParm} from './CategoryModel'

//新增
export let addCategoryApi=(parm:Category)=>{
    return http.post("/api/category",parm)
}


//列表
export let getCategoryListApi=(parm:CategoryListParm)=>{
    return http.get("/api/category/list",parm)
}

//编辑
export let editCategoryApi=(parm:Category)=>{
    return http.put("/api/category",parm)
}

//删除
export let deleteCategoryApi=(categoryId:string)=>{
    return http.delete(`/api/category/${categoryId}`)
}
/*** */
//列表
export let getCateListApi=()=>{
    return http.get("/api/category/getCateList")
}