//定义商品数据类型
export type Goods={
    goodsId?:string,
    categoryId:string,
    goodsName:string,
    image?:string,
    type:string,
    goodsPrice:string,
    goodsDesc:string,
    userName:string,
    phone:string,
    wxNum:string,
    address:string,
    status?:string,
    setIndex?:string,
    sellStatus?:string
}


//定义列表查询的参数
//total总条数
export type GoodsListParm={
    currentPage:number,
    pageSize:number,
    goodsName:string,
    total?:number,
    categoryId:any
    
}

//上架下架的参数
export type UpdateParm={
    goodsId:string,
    status?:string,
    setIndex?:string,
}

//定义列表查询的参数
//total总条数
export type Parm={
    categoryId: string,
    keywords: string
    
}
