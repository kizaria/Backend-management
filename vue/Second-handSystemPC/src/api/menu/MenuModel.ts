
//菜单数据类型
export type Menu={
    menuId: string,
    parentId: string,
    title: string,
    code: string,
    icon: string,
    orderNum: string,
    parentName: string,
    type: string,
    path: string
}
//查询树数据的参数类型
export type AssignParm={
    assId:string,
    userId:string
}
//保存权限的数据类型
export type SaveAssignParm={
    assId:string,
    list:Array<string>
}
