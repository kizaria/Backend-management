<template>
    <div class="main">
        <el-form 
        :model="addModel" 
        ref="addRef" 
        :rules="rules" 
        label-width="80px" 
        :inline="false" 
        size="default">


            <el-form-item prop="title" label="标题:">
                <el-input  v-model="addModel.title"></el-input>
            </el-form-item>

            <el-form-item prop="content" label="内容:">
                <div id="editor"></div>
            </el-form-item>

        </el-form>

        <div style="padding-left: 350px; padding-bottom: 30px;" >
            <el-button @click="commit()" style="margin-right: 100px;" size="large">确定</el-button>
            <el-button @click="resetBtn()" size="large">取消</el-button>
        </div>

    </div>
</template>

<script setup lang="ts">
import { reactive, ref ,onMounted,nextTick} from 'vue'
import { validateLocaleAndSetLanguage } from 'typescript';
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import {addGoodsHelpApi} from '@/api/help/index'
import E from "wangeditor"
import { UserStore } from '@/stores/user/index';

//获取pinia
let store=UserStore()

let editor: E | null=null

//表单ref属性
let addRef=ref<FormInstance>();

//新增表单数据
let addModel=reactive({
    title: "",
    content: "",
    userId: store.getUserId,
})
//表单验证规则
let rules={
    title:[{required: true, message: '请输入标题', trigger: 'blur'}],
    content:[{required: true, message: '请输入内容', trigger: 'blur'}]
}

//重置
let resetBtn=()=>{
    //清除表单数据
    addModel.title=''
    editor.txt.clear()
}

//提交
let commit=async ()=>{
    addModel.content=editor.txt.html() 
    //表单验证
    addRef.value?.validate(async(valid)=>{
        if(valid){
            //提交数据
            let res =await addGoodsHelpApi(addModel)
            //如果res返回，并且code等于200
            if(res&&res.code==200){
                //信息提示
                ElMessage.success(res.msg)
                addModel.title=''
                editor.txt.clear()
            }
           
        }
    })
}

onMounted(()=>{
    editor = new E(`#editor`)
// 设置参数
    editor.create() 
})
</script>

<style lang="scss" scoped>
.main{
    padding-left: 60px;
    padding-right: 80px;
    padding-top: 20px;
}
</style>