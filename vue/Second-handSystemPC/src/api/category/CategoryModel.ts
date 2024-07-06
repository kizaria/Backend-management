//定义用户数据类型
export type Category={
    categoryId:string,
    categoryName:string,
    orderNum:string
}

//定义列表查询的参数
//total总条数
export type CategoryListParm={
    currentPage:number,
    pageSize:number,
    categoryName:string,
    total?:number
}