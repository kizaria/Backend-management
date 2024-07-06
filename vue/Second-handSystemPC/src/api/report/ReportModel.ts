//定义列表查询的参数
//total总条数
export type ListParm={
    currentPage:number,
    pageSize:number,
    goodsName:string,
    total?:number
}

export type report={
    goodsId:string,
    reportUser:string,
    reason:string
}

export type ReportParm={
    userId?:string,
    currentPage?:number,
    pageSize?:number,
    goodsName?:string,
    goodsId?:string,
    total?:number,
    reason?:string,
    reportUser?:string
}

