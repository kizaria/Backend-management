//定义列表查询的参数
//total总条数
export type LogParm={
    currentPage:number,
    pageSize:number,
    username:string,
    total?:number,
}

export type Log={
    logId:string,
    operation:string,
    type:string,
    pip:string,
    logUser:string,
    time:string,
    username:string,

}