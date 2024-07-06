<template>
    <div class="main">
        <el-form :model="addModel" ref="addRef" :rules="rules" label-width="80px" :inline="false" size="default">


            <el-form-item prop="goodsName" label="商品名:">
                <el-input v-model="addModel.goodsName"></el-input>
            </el-form-item>

            <el-form-item prop="categoryId" label="分类:">
                <el-select  v-model="addModel.categoryId" placeholder="选择分类" style="width: 240px">
                    <el-option v-for="item in categoryList" :key="item.value" :label="item.label" :value="item.value"
                        style="width: 240px;" />
                </el-select>
            </el-form-item>

            <el-form-item label-width="auto" prop="type" label="交易方式:">
                <el-radio-group v-model="addModel.type">
                    <el-radio :label="'0'">全部</el-radio>
                    <el-radio :label="'1'">自提</el-radio>
                    <el-radio :label="'2'">快递</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item prop="image" label="图片:">
                        <UploadImage
                          ref="upImgRef"
                          @getImg="getImg"
                          :fileList="fileList"
                          :oldUrl="oldUrl"

                        ></UploadImage>
                    </el-form-item>

            <el-form-item label-width="auto" prop="goodsDesc" label="商品详情:">
                <el-input :rows="4" type="textarea" v-model="addModel.goodsDesc"></el-input>
            </el-form-item>

            <el-form-item prop="goodsPrice" label="价格:">
                <el-input v-model="addModel.goodsPrice"></el-input>
            </el-form-item>

            <el-form-item prop="userName" label="联系人:">
                <el-input v-model="addModel.userName"></el-input>
            </el-form-item>

            <el-form-item prop="phone" label="电话:">
                <el-input v-model="addModel.phone"></el-input>
            </el-form-item>

            <el-form-item prop="wxNum" label="微信:">
                <el-input v-model="addModel.wxNum"></el-input>
            </el-form-item>

            <el-form-item prop="address" label="地址:">
                <el-input v-model="addModel.address"></el-input>
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
import { categoryApi,releaseApi } from '@/api/goods/index'
import E from "wangeditor"
import { UserStore } from '@/stores/user/index';
import UploadImage from '@/components/UploadImage.vue'
import type { NewType } from '@/type/BaseType';

//图片上传插槽的ref属性
let upImgRef=ref();
//图片初始值
let fileList=ref<Array<UploadUserFile>>([]);
//回显的图片
let oldUrl=ref<Array<{url:string}>>([]);
//图片地址
let imgUrl=ref<Array<{url:string}>>([])

//获取pinia
let store = UserStore()

//新增表单数据
let addModel = reactive({
    userId: store.getUserId,
    goodsName: '',
    categoryId: '',
    type: '',
    goodsDesc: '',
    goodsPrice: '',
    userName: '',
    phone: '',
    wxNum: '',
    address: '',
    image: ''
})


//表单ref属性
let addRef = ref<FormInstance>();

//表单验证规则
let rules = {
        categoryId:[{required: true, message: '请选择类型', trigger: ['change','blur']}],
        type:[{required: true, message: '请选择收货方式', trigger: ['change','blur']}],
	    goodsName:[{required: true, message: '请输入名称', trigger: ['change','blur']}],
		goodsDesc:[{required: true, message: '请输入简介', trigger: ['change','blur']}],
		goodsPrice:[{required: true, message: '请输入价格', trigger: ['change','blur']},
		{
					pattern: /^[0-9]*$/g,
					// 正则检验前先将值转为字符串
					transform(value: any) {
						return String(value);
					},
					message: '只能包含数字'
				},],
		userName:[{required: true, message: '请输入联系人', trigger: ['change','blur']}],
		phone:[{required: true, message: '请输入电话', trigger: ['change','blur']}],
		wxNum:[{required: true, message: '请输入微信号', trigger: ['change','blur']}],
		address:[{required: true, message: '请输入地址', trigger: ['change','blur']}],
		
}

//重置
let resetBtn = () => {
    //清除表单数据
    addRef.value?.resetFields()
    addModel.type="-1"
    //清空图片
    upImgRef.value.clearimg()
}

//提交
let commit = async () => {
    //表单验证
    addRef.value?.validate(async (valid) => {
        if (valid) {
            //提交数据
            let res = await releaseApi(addModel)
            //如果res返回，并且code等于200
            if (res && res.code == 200) {
                //信息提示
                ElMessage.success(res.msg)
                //清空图片
                upImgRef.value.clearimg()
                addRef.value?.resetFields()
            }

        }
    })
}
//分类数据
let categoryList = ref([])
//查询所有分类
let category = async () => {
    let res = await categoryApi()
    if (res.code == 200) {
        categoryList.value = res.data
    }
}

//接收图片上传地址
let getImg=(img:NewType)=>{

imgUrl.value=oldUrl.value.concat(img.newImgUrl);
console.log(imgUrl.value)
if(img.deleteUrl.length>0){
    let newArr=imgUrl.value.filter(
        (x)=>!img.deleteUrl.some(
            (item)=>x.url===item.url
            )
    );
    imgUrl.value=newArr;
}
//把图片路径拼接为逗号分隔的字符串
let url="";
for(let i=0;i<imgUrl.value.length;i++){
    url=url+imgUrl.value[i].url+",";
}
addModel.image=url.substring(0,url.lastIndexOf(","));
}


onMounted(() => {
    category()
})
</script>

<style lang="scss" scoped>
.main {
    padding-left: 60px;
    padding-right: 80px;
    padding-top: 20px;
}
</style>