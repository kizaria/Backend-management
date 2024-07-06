//定义用户数据类型
export type User={
    userId:string,
    username:string,
    password:string,
    nickName:string,
    phone:string,
    sex:string,
    status:string
}

//定义列表查询的参数
//total总条数
export type ListParm={
    currentPage:number,
    pageSize:number,
    nickName:string,
    total?:number
}

//定义登录参数
export type LoginParm={
    username:string,
    password:string,
    code:string
}

//定义修改密码参数
export type UpdatePasswordParm={
    userId:string,
    password:string,
    oldPassword:string
}