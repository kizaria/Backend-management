
import type { Directive } from "vue";
import { UserStore } from "@/stores/user";

export let permission:Directive={
    mounted(el,binding){
        //获取store
        let store=UserStore()
        //获取用户在登录时保存的权限
        let permissions=store.codeList;
        //获取按钮上的权限
        let {value}=binding;
        //value存在才继续执行
        if(value&&value instanceof Array&&value.length>0){
            let permissionRoles=value;
            //判断按钮上的权限是否在用户权限里
            let hasPermission=permissions.some((role)=>{
                return permissionRoles.includes(role)
            })

            //如果不存在
            if(!hasPermission){
                //隐藏按钮
                el.style.display='none'
            }
        }else{
            throw new Error('按钮没有权限！！！！')
        }
    }
}