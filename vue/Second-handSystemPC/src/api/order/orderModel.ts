//定义列表查询的参数
//total总条数
export type ListParm={
    currentPage:number,
    pageSize:number,
    goodsName:string,
    total?:number
}

export type Order={
    orderId?:string,
    goodsId?:string,
    goodsName?:string,
    orderUser?:string,
    price?:string,
    createTime?:string,
    addressid?:string,
    deleteStatus?:string,
    orderStatus?:string,
    orderPay?:string,



}