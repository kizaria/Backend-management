import http from "@/http";

//查询网站公告
export let getAnnouncementListApi=()=>{
    return http.get("/api/announcement/getAnnouncementList")
}