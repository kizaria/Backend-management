<template>
    <div class="main">
        <el-form :model="addModel" ref="addRef" :rules="rules" label-width="80px" :inline="false" size="default">


            <el-form-item prop="nickName" label="昵称:">
                <el-input v-model="addModel.nickName"></el-input>
            </el-form-item>


            <el-form-item prop="picture" label="头像:">
                <UploadImage ref="upImgRef" @getImg="getImg" :fileList="fileList" :oldUrl="oldUrl"></UploadImage>
            </el-form-item>



            <el-form-item prop="phone" label="电话:">
                <el-input v-model="addModel.phone"></el-input>
            </el-form-item>

        </el-form>

        <div style="padding-left: 350px; padding-bottom: 30px;">
            <el-button @click="commit()" style="margin-right: 100px;" size="large">确定</el-button>
            <el-button @click="resetBtn()" size="large">取消</el-button>
        </div>

    </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, nextTick } from 'vue'
import { validateLocaleAndSetLanguage } from 'typescript';
import { ElMessage, type FormInstance, type FormRules, type UploadUserFile } from 'element-plus'
import { addGoodsHelpApi } from '@/api/help/index'
import { categoryApi, releaseApi } from '@/api/goods/index'
import E from "wangeditor"
import { UserStore } from '@/stores/user/index';
import UploadImage from '@/components/UploadImage.vue'
import type { NewType } from '@/type/BaseType';
import { getInfoApi,WxUserEditApi } from '@/api/wxuser';
import router from '@/router';

//图片上传插槽的ref属性
let upImgRef = ref();
//图片初始值
let fileList = ref<Array<UploadUserFile>>([]);
//回显的图片
let oldUrl = ref<Array<{ url: string }>>([]);
//图片地址
let imgUrl = ref<Array<{ url: string }>>([])

//获取pinia
let store = UserStore()

//新增表单数据
let addModel = reactive({
    userId: store.getUserId,
    nickName: '',
    picture: '',
    phone: '',
})


//表单ref属性
let addRef = ref<FormInstance>();

//表单验证规则
let rules = {
    nickName: [{ required: true, message: '请选择类型', trigger: ['change', 'blur'] }],
    picture: [{ required: true, message: '请选择收货方式', trigger: ['change', 'blur'] }],
    phone: [{ required: true, message: '请输入电话', trigger: ['change', 'blur'] }],

}

//重置
let resetBtn = () => {
    //清除表单数据
    addRef.value?.resetFields()
    //清空图片
    upImgRef.value.clearimg()
}

//提交
let commit = async () => {
    //表单验证
    addRef.value?.validate(async (valid) => {
        if (valid) {
            //提交数据
            let res = await WxUserEditApi(addModel)
            //如果res返回，并且code等于200
            if (res && res.code == 200) {
                //信息提示
                ElMessage.success(res.msg)
                //清空图片
                upImgRef.value.clearimg()
                addRef.value?.resetFields()
            }
        //跳转
        router.push({path:'/login'})
        store.setinfo()
        //清空缓存
        localStorage.clear()
        }


    })
}

//接收图片上传地址
let getImg = (img: NewType) => {

    imgUrl.value = oldUrl.value.concat(img.newImgUrl);
    console.log(imgUrl.value)
    if (img.deleteUrl.length > 0) {
        let newArr = imgUrl.value.filter(
            (x) => !img.deleteUrl.some(
                (item) => x.url === item.url
            )
        );
        imgUrl.value = newArr;
    }
    //把图片路径拼接为逗号分隔的字符串
    let url = "";
    for (let i = 0; i < imgUrl.value.length; i++) {
        url = url + imgUrl.value[i].url + ",";
    }
    addModel.picture = url.substring(0, url.lastIndexOf(","));
}

//查询个人信息
let info = async () => {
    let res = await getInfoApi({
        userId: store.getUserId
    })
    if (res.code == 200) {
        nextTick(() => {

            addModel.nickName = res.data.nickName
            addModel.picture = res.data.picture
            addModel.phone = res.data.phone
            console.log(addModel)
            //图片回显
            if (addModel.picture) {
                let imgs = addModel.picture.split(",");

                for (let i = 0; i < imgs.length; i++) {
                    let img = { name: '', url: '' };
                    img.name = imgs[i]
                    img.url = imgs[i]
                    fileList.value.push(img)
                    oldUrl.value.push({ url: imgs[i] })
                }
            }
        })
    }

}


onMounted(() => {
    info()
})
</script>

<style lang="scss" scoped>
.main {
    padding-left: 60px;
    padding-right: 80px;
    padding-top: 20px;
}
</style>