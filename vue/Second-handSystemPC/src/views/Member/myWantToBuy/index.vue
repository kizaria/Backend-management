<template>
    <el-main>
        <!-- 表格 -->
        <el-table :data="tableList" size="small">
            <el-table-column prop="title" label="求购标题"></el-table-column>


            <el-table-column prop="content" label="求购详情">
                <template #default="scope">
                    <el-button type="primary"size="small"
                        @click="detail(scope.row.content)">详情</el-button>
                </template>
            </el-table-column>

            <el-table-column prop="status" label="审核情况">
                <template #default="scope">
                    <p v-if="scope.row.status=='0'" >待审核</p>
                    <p v-if="scope.row.status=='1'" >未通过</p>
                    <p v-if="scope.row.status=='2'" >已通过</p>
                </template>
            </el-table-column>

            <el-table-column prop="solved" label="解决状态">
                <template #default="scope" >
                    <el-button v-if="scope.row.solved=='0'" size="small" type="danger" effect="dark" @click="editstatus(scope.row)">未解决</el-button>
                    <el-button v-if="scope.row.solved=='1'" size="small"  type="primary" @click="editstatus(scope.row)" effect="dark" >已解决</el-button>
                </template>
            </el-table-column>


            <el-table-column label="操作" align="center" width="220">
                <template #default="scope">
                    <el-button  type="primary" icon="Edit" size="small"
                        @click="editBtn(scope.row)">编辑</el-button>
                    <el-button type="danger" icon="Delete" size="small"
                        @click="deleteBtn(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页 -->
        <el-pagination @size-change="sizeChange" @current-change="currentChange"
            :current-page.sync="searchPram.currentPage" :page-sizes="[10, 20, 40, 80, 100]"
            :page-size="searchPram.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="searchPram.total"
            background>
        </el-pagination>



        <!-- 弹框 -->
        <el-dialog v-model="dialogTableVisible" title="编辑求购信息" width="800">
            <el-form 
        :model="addModel" 
        ref="addRef" 
        :rules="rules" 
        label-width="80px" 
        :inline="false" 
        size="default"
        :close="closeDialog()">


            <el-form-item prop="title" label="标题:">
                <el-input  v-model="addModel.title"></el-input>
            </el-form-item>

            <el-form-item prop="content" label="内容:">
                <div id="editor"></div>
            </el-form-item>

        </el-form>

            <div style="padding-left: 280px; padding-bottom: 30px;">
            <el-button @click="commit()" style="margin-right: 100px;" size="large">确定</el-button>
            <el-button @click="resetBtn()" size="large">取消</el-button>
        </div>
        </el-dialog>

        <!-- 详情弹框 -->
        <el-dialog v-model="detaildialog" title="求购详情" width="800">
            <span v-html="content"></span>
        </el-dialog>

    </el-main>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, nextTick } from 'vue'
import { upanddownApi, editGoodsApi, categoryApi ,getMyUnusedListApi} from '@/api/goods/index'
import { type Goods, type GoodsListParm, type UpdateParm } from '@/api/goods/GoodsModel'
import useInstance from '@/hooks/useInstance'
import { ElMessage, type FormInstance, type FormRules, type UploadUserFile } from 'element-plus'
import useDialog from '@/hooks/useDialog'
import SysDialog from '@/components/SysDialog.vue'
import UploadImage from '@/components/UploadImage.vue'
import type { NewType } from '@/type/BaseType'
import { UserStore } from '@/stores/user'
import { deleteMyHelpApi, editApi, getMyHelpApi} from '@/api/help/index'
import E from "wangeditor"
import type { Help } from '@/api/help/helpModel'
//弹窗关闭属性
let dialogTableVisible = ref(false)
let detaildialog=ref(false)

//获取token
let user = UserStore()
//获取全局属性
let { global } = useInstance()
//表单ref属性
let addRef = ref<FormInstance>();
//获取弹框属性
let { dialog, onClose, onConfirm, onShow } = useDialog();
let editor: E | null=null

//搜索绑定的对象
let searchPram = reactive({
    currentPage: 1,
    pageSize: 10,
    goodsName: "",
    total: 0,
    categoryId: null
})

//新增表单数据
let addModel = reactive({
    title: "",
    content: "",
    userId: user.getUserId,
})

//表单验证规则
let rules = reactive({
    title: [{ required: true, message: '请输入标题', trigger: ['change', 'blur'] }],
    content:[{required: true, message: '请输入内容', trigger: 'blur'}]
})


//新增，编辑提交
let commit = () => {
    addModel.content=editor.txt.html() 
    //表单验证
    addRef.value?.validate(async (valid) => {
        if (valid) {
            //提交数据
            let res = await editApi(addModel);
            //如果res返回，并且code等于200
            if (res && res.code == 200) {
                //信息提示
                ElMessage.success(res.msg)
                //刷新表格
                getHelpList()
                //关闭弹框
                dialogTableVisible.value=false
            }

        }
    })
}


//编辑
let editBtn = (row: Help) => {
    console.log(row)
    //清除表单数据
    addRef.value?.resetFields()
    dialogTableVisible.value = true
    //设置数据回显
    //nextTick在状态改变后立即使用，简称立刻更新
    nextTick(() => {
        Object.assign(addModel, row);
        editor = new E(`#editor`)
        // 设置参数
        editor.create()
        editor.txt.clear()
        console.log(row.content)
        editor.txt.html(row.content)
    })

}

//重置
let resetBtn = () => {
    //清除表单数据
    addRef.value?.resetFields()

    //关闭弹窗
    dialogTableVisible.value=false
}

//表格数据
let tableList = ref([])
//列表查询
let getHelpList = async () => {
    let res = await getMyHelpApi({
        userId:user.getUserId,
        currentPage:searchPram.currentPage,
        pageSize:searchPram.pageSize
    })
    console.log(res)
    if (res && res.code == 200) {
        tableList.value = res.data.records;
        searchPram.total = res.data.total;
    }
}




//删除
let deleteBtn = async (row: Help) => {
    let confirm = await global.$myconfirm('确认删除该信息吗？')
    if (confirm) {
        let res = await deleteMyHelpApi({id:row.id})
        if (res && res.code == 200) {
            ElMessage.success(res.msg)
            getHelpList()
        }
    }
}

//修改状态
let editstatus = async (row: Help) => {
    let confirm =await global.$myconfirm('确认更改该状态吗？')
   console.log(global)
   if(confirm){
    if(row.solved=='1'){
        row.solved='0'
    }else if(row.solved=='0'){
        row.solved='1'
    }
        let res = await editApi(row)
        //如果res返回，并且code等于200
        if (res && res.code == 200) {
            //信息提示
            ElMessage.success(res.msg)
            //刷新表格
            getHelpList()
        }
    }
}


//求购详情
let content=ref()
let detail=(item:any)=>{
    detaildialog.value=true
    content.value=item

}


// 分页方法
//page-size 改变时触发
//pagesize就是当前页显示多少条数据
let sizeChange = (size: number) => {
    searchPram.pageSize = size
    getHelpList()
}

//current-page 改变时触发
let currentChange = (page: number) => {
    searchPram.currentPage = page
    getHelpList()

}

// 在dialog销毁时调用
let closeDialog=()=> {
  // 销毁编辑器
  editor?.destroy()
  editor = null
}




onMounted(() => {
    getHelpList()

 
})





</script>

<style scoped></style>