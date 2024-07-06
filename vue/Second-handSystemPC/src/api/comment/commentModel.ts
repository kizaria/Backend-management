//定义用户数据类型
export type Comment={
    content:string,
    username:string
    userId:string
    foreignId:string
    pid?:string
    target?:string
}

//定义列表查询的参数
//total总条数
export type CategoryListParm={
    currentPage:number,
    pageSize:number,
    categoryName:string,
    total?:number
}