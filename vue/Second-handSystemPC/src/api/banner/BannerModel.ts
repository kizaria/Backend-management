//定义banner数据类型
export type Banner={
    banId: string,
    goodsId: string,
    title: string,
    images: string,
    status: string,
    orderNum: string
}

//定义列表查询的参数
export type BannerListParm={
    currentPage:number,
    pageSize:number,
    title:string,
    total?:number
}