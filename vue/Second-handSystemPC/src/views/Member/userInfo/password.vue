<template>
    <div class="main">
        <el-form :model="addModel" ref="addRef" :rules="rules" label-width="80px" :inline="false" size="default">


            <el-form-item prop="oldPassword" label="原密码:">
                <el-input v-model="addModel.oldPassword"></el-input>
            </el-form-item>

            <el-form-item prop="password" label="新密码:">
                <el-input v-model="addModel.password"></el-input>
            </el-form-item>

            <el-form-item prop="confrim" label="确认新密码:">
                <el-input v-model="addModel.confrim"></el-input>
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
import { getInfoApi,WxUserEditApi ,updatePaswordApi} from '@/api/wxuser';
import router from '@/router';

//获取pinia
let store = UserStore()

//新增表单数据
let addModel = reactive({
    userId: store.getUserId,
    oldPassword:'',
    password: '',
    confrim:''
})


//表单ref属性
let addRef = ref<FormInstance>();

//表单验证规则
let rules = {
    oldPassword: [{ required: true, message: '请输入新密码', trigger: ['change', 'blur'] }],
    password: [{ required: true, message: '请输入新密码', trigger: ['change', 'blur'] }],
    confrim: [{ required: true, message: '请确认新密码', trigger: ['change', 'blur'] }],

}

//重置
let resetBtn = () => {
    //清除表单数据
    addRef.value?.resetFields()
}

//提交
let commit = async () => {
        //新密码和确认密码判断
        if(addModel.password!=addModel.confrim){
        ElMessage.warning("新密码和确认密码不一致");
        return;
    }
    //表单验证
    addRef.value?.validate(async (valid) => {
        if (valid) {
            //提交数据
            let res = await updatePaswordApi(addModel)
            //如果res返回，并且code等于200
            if (res && res.code == 200) {
                //信息提示
                ElMessage.success(res.msg)
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



onMounted(() => {
   
})
</script>

<style lang="scss" scoped>
.main {
    padding-left: 60px;
    padding-right: 80px;
    padding-top: 20px;
}
</style>