import http from '@/http'

//图片上传
export let uploadImageApi=(parm:object)=>{
    return http.upload("/api/upload/uploadImage",parm)
}