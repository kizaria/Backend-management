import http from "@/http";
import type { Message } from "./chatModel";

//查询所有消息
export let getMessage=(user:Message)=>{
    return http.post('/api/imsingle',user)
}
