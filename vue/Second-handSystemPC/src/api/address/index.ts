import http from '@/http'

//查询地址
export let getAddressApi=(userId:any)=>{
    return http.get("/api/address/getAddress",userId)
}

//新增地址
export let saveAddressApi=(parm:any)=>{
    return http.post("/api/address/saveAddress",parm)
}

//编辑地址
export let editAddressApi=(parm:any)=>{
    return http.post("/api/address/editAddress",parm)
}

//设为默认地址
export let defaultAddressApi=(parm:any)=>{
    return http.post("/api/address/defaultAddress",parm)
}

//删除地址
export let deleteAddressApi=(parm:any)=>{
    return http.post("/api/address/delete",parm)
}

//查询地址
export let getListApi=(parm:any)=>{
    return http.post("/api/address/getList",parm)
}