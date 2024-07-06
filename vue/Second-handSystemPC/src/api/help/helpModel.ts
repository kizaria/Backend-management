//定义用户数据类型
export type Help={
    id?:string
    title:string,
    content:string,
    userId:string,
    solved:string
}

//定义列表查询的参数
//total总条数
export type CategoryListParm={
    currentPage:number,
    pageSize:number,
    categoryName:string,
    total?:number
}