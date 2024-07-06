//定义用户数据类型
export type WxUser = {
    userId?: string,
    username?: string,
    name?: string,
    nickName?: string,
    phone?: string,
    status?: string
}


//定义列表查询的参数
//total总条数
export type WxUserListParm={
    currentPage:number,
    pageSize:number,
    name:string,
    total?:number
}