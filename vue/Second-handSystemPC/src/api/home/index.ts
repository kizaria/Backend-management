import http from '@/http'

//查询总数
export let getTotalApi=()=>{
    return http.get("/api/home/getTotal")
}

//查询图表数据
export let getChartApi=()=>{
    return http.get("/api/home/getChart")
}

//查询图表数据
export let getChartNameApi=()=>{
    return http.get("/api/home/getChartName")
}