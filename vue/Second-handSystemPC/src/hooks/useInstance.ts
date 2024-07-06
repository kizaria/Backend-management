import { getCurrentInstance, type ComponentInternalInstance } from 'vue'

export default function useInstance(){
    let {appContext,proxy}=getCurrentInstance() as ComponentInternalInstance
    let global=appContext.config.globalProperties;
    return {proxy,global}
}