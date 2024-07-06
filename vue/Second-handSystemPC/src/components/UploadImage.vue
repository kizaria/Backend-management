<template>
    <el-upload 
    action="#" 
    list-type="picture-card" 
    :auto-upload="false" 
    ref="uploadRef" 
    :on-change="uploadFile"
    :on-remove="handleRemove" 
    :file-list="fileList" 
    :limit="3" 
    :on-exceed="moreLimit">
        <el-icon>
            <Plus />
        </el-icon>
    </el-upload>
    <el-dialog v-model="dialogVisible">
        <img w-full :src="dialogImageUrl" alt="Preview Image" />
    </el-dialog>
</template>

<script setup lang="ts">
import { uploadImageApi } from "@/api/img/index";
import { ref } from "vue";
import { Plus } from "@element-plus/icons-vue";
import { type NewType } from "@/type/BaseType";
import { ElMessage, type UploadFile, type UploadUserFile, type UploadFiles } from "element-plus";
//注册事件
let emits = defineEmits(['getImg'])
//图片上传组件的ref属性 
let uploadRef = ref();
let dialogImageUrl = ref("");
let dialogVisible = ref(false);
type PropType = {
    fileList: UploadUserFile[];
    oldUrl: Array<{ url: string }>;
};
//接收父组件传递的参数
let props = withDefaults(defineProps<PropType>(), {
    //默认值
    fileList: () => [],
    oldUrl: () => [],
});
//返回给父组件的值 
let newImgRes = ref<NewType>({
    newImgUrl: [],
    deleteUrl: []
});
//删除图片
let handleRemove = (file: UploadFile) => {
    if (props.oldUrl.some(item => item.url === file.name)) {
        newImgRes.value.deleteUrl.push({ url:file.name })
        emits("getImg", newImgRes.value);
    } else {
        let images = newImgRes.value.newImgUrl.filter((item) => item.url != file.name);
        newImgRes.value.newImgUrl = images;
       
        emits("getImg", newImgRes.value);
    }
};
//文件个数超出
let moreLimit = (files: File[], uploadFiles: UploadFiles) => {
    ElMessage.warning("最多只能上传" + uploadFiles.length + "张图片!");
};
//上传数据提交
let uploadFile = async (file: any) => {
    //判断选择的图片是不是图片类型
    let typeArr = ["image/png", "image/gif", "image/jpeg", "image/jpg"];
    let isImg = typeArr.indexOf(file.raw.type) !== -1;
    let isMore3M = file.size / 1024 / 1024 < 3;
    if (!isImg) {
        ElMessage.warning("只能上传图片类型！");
        uploadRef.value?.clearFiles();
        return;
    }
    if (!isMore3M) {
        ElMessage.warning("图片大小不能超过3M!");
        uploadRef.value?.clearFiles();
        return;
    }
    //组装上传的数据
    let formData = new FormData();
    formData.append("file", file.raw);
    console.log(formData)
    
    //提交数据
    let res = await uploadImageApi(formData)
    console.log(res)
    if (res && res.code == 200 && res.data) {
        ElMessage.success('图片上传成功')
        file.name = process.env.BASE_API + res.data;
        //设置到返回子组件的数据里面
        newImgRes.value.newImgUrl.push({
            url: process.env.BASE_API + res.data
        })
        emits('getImg', newImgRes.value)
    }
};
//清空数据
let clearimg = () => {
    uploadRef.value?.clearFiles();
    newImgRes.value.deleteUrl = [];
    newImgRes.value.newImgUrl = [];
}
defineExpose({
    clearimg
})
</script>
<style scoped></style>



