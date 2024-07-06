<template>
    <el-main>
        <!-- 表格 -->
        <el-table :data="tableList" size="small">
            <el-table-column prop="goodsName" label="商品名称"></el-table-column>

            <el-table-column prop="image" label="图片">
                <template #default="scope">
                    <el-image v-if="scope.row.image" style="width: 50px; height: 50px"
                        :src="scope.row.image.split(',')[0]" />
                </template>
            </el-table-column>

            <el-table-column prop="type" label="收货类型">
                <template #default="scope">
                    <p v-if="scope.row.type=='0'" >全部</p>
                    <p v-if="scope.row.type=='1'" >自提</p>
                    <p v-if="scope.row.type=='2'" >快递</p>
                </template>
            </el-table-column>


            <el-table-column prop="categoryId" label="商品分类">
                <template #default="scope">
                    <div v-for="(category, index) in list" :key="index">
                        <span v-if="category.value == scope.row.categoryId">
                            {{ category.label }}
                        </span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="goodsPrice" label="商品价格"></el-table-column>
            <el-table-column prop="goodsDesc" label="商品描述"></el-table-column>
            <!-- <el-table-column prop="phone" label="电话"></el-table-column>
            <el-table-column prop="wxNum" label="联系人微信"></el-table-column> -->
            <el-table-column prop="address" label="商品地址"></el-table-column>

            <el-table-column prop="auditing" label="审核状态">
                <template #default="scope">
                    <p v-if="scope.row.auditing=='0'" >待审核</p>
                    <p v-if="scope.row.auditing=='1'" >审核未通过</p>
                    <p v-if="scope.row.auditing=='2'" >审核通过</p>
                </template>
            </el-table-column>


            <el-table-column prop="status" label="上下架">
                <template #default="scope" >
                    <el-button v-if="scope.row.status=='0'" size="small" type="primary" effect="dark" @click="editstatus(scope.row)">启用</el-button>
                    <el-button v-if="scope.row.status=='1'" size="small"  type="danger" @click="editstatus(scope.row)" effect="dark" >停用</el-button>
                </template>
            </el-table-column>

            <el-table-column label="操作" align="center" width="220">
                <template #default="scope">
                    <el-button  type="primary" icon="Edit" size="small"
                        @click="editBtn(scope.row)">编辑</el-button>
                    <el-button  type="danger" icon="Delete" size="small"
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
        <!-- <SysDialog 

             <el-dialog v-model="dialogFormVisible" title="Shipping address" width="500">
    <el-form :model="form">
      <el-form-item label="Promotion name" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="Zones" :label-width="formLabelWidth">
        <el-select v-model="form.region" placeholder="Please select a zone">
          <el-option label="Zone No.1" value="shanghai" />
          <el-option label="Zone No.2" value="beijing" />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>
        :title="dialog.title" 
        :visible="dialog.visible" 
        :height="dialog.height" 
        :width="dialog.width"
        @onClose="onClose" 
        @onConfirm="commit">

            <template v-slot:content>
                <el-form 
                :model="addModel" 
                ref="addRef" 
                :rules="rules" 
                label-width="80px" 
                :inline="false" 
                size="small">


                    <el-form-item label-width="auto" prop="goodsName" label="商品名称:">
                        <el-input style="width: 100px;" v-model="addModel.goodsName"></el-input>
                    </el-form-item>

                    <el-form-item prop="image" label="图片:">
                        <UploadImage
                          ref="upImgRef"
                          @getImg="getImg"
                          :fileList="fileList"
                          :oldUrl="oldUrl"

                        ></UploadImage>
                    </el-form-item>

                    <el-form-item label-width="auto" prop="type" label="商品类别:">
                        <el-radio-group style="width: 100px;" v-model="addModel.type">
                            <el-radio :label="'0'">闲置</el-radio>
                            <el-radio :label="'1'">求购</el-radio>
                        </el-radio-group>
                    </el-form-item>

                    <el-form-item label-width="auto" prop="categoryId" label="商品分类:">
                        <el-select  v-model="addModel.categoryId" placeholder="Select" style="width: 240px">
                            <el-option
                            v-for="item in list"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                            />
                        </el-select>
                    </el-form-item>

                    <el-form-item label-width="auto" prop="goodsPrice" label="商品价格:">
                        <el-input style="width: 100px;" v-model="addModel.goodsPrice"></el-input>
                    </el-form-item>

                    <el-form-item label-width="auto" prop="goodsDesc" label="商品简介:">
                        <el-input style="width: 100px;" v-model="addModel.goodsDesc"></el-input>
                    </el-form-item>

                    <el-form-item label-width="auto" prop="userName" label="联系人:">
                        <el-input style="width: 100px;" v-model="addModel.userName"></el-input>
                    </el-form-item>

                    <el-form-item label-width="auto" prop="phone" label="电话:">
                        <el-input style="width: 100px;" v-model="addModel.phone"></el-input>
                    </el-form-item>

                    <el-form-item label-width="auto" prop="wxNum" label="微信号:">
                        <el-input style="width: 100px;" v-model="addModel.wxNum"></el-input>
                    </el-form-item>

                    <el-form-item label-width="auto" prop="address" label="地址:">
                        <el-input style="width: 100px;" v-model="addModel.address"></el-input>
                    </el-form-item>



                </el-form>
            </template>
        </SysDialog> -->
        <el-dialog v-model="dialogTableVisible" title="编辑商品" width="800">
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
                    <UploadImage ref="upImgRef" @getImg="getImg" :fileList="fileList" :oldUrl="oldUrl"></UploadImage>
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

            <div style="padding-left: 280px; padding-bottom: 30px;">
            <el-button @click="commit()" style="margin-right: 100px;" size="large">确定</el-button>
            <el-button @click="resetBtn()" size="large">取消</el-button>
        </div>
        </el-dialog>
    </el-main>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, nextTick } from 'vue'
import { deleteApi, getGoodsListApi, setIndexApi, upanddownApi, editGoodsApi, categoryApi ,getMyUnusedListApi} from '@/api/goods/index'
import { type Goods, type GoodsListParm, type UpdateParm } from '@/api/goods/GoodsModel'
import useInstance from '@/hooks/useInstance'
import { ElMessage, type FormInstance, type FormRules, type UploadUserFile } from 'element-plus'
import useDialog from '@/hooks/useDialog'
import SysDialog from '@/components/SysDialog.vue'
import UploadImage from '@/components/UploadImage.vue'
import type { NewType } from '@/type/BaseType'
import { UserStore } from '@/stores/user'

//弹窗关闭属性
let dialogTableVisible = ref(false)

//获取token
let user = UserStore()
//获取全局属性
let { global } = useInstance()
//表单ref属性
let addRef = ref<FormInstance>();
//获取弹框属性
let { dialog, onClose, onConfirm, onShow } = useDialog();

//图片上传插槽的ref属性
let upImgRef = ref();
//图片初始值
let fileList = ref<Array<UploadUserFile>>([]);
//回显的图片
let oldUrl = ref<Array<{ url: string }>>([]);
//图片地址
let imgUrl = ref<Array<{ url: string }>>([])

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
    goodsName: '',
    image: "",
    type: '',
    categoryId: '',
    goodsPrice: '',
    goodsDesc: '',
    userName: '',
    phone: '',
    wxNum: '',
    address: ''
})

//表单验证规则
let rules = reactive({
    goodsName: [{ required: true, message: '请输入名称', trigger: ['change', 'blur'] }],
    type: [{ required: true, message: '请选择类型', trigger: ['change', 'blur'] }],
    categoryId: [{ required: true, message: '请输入分类', trigger: ['change', 'blur'] }],
    goodsDesc: [{ required: true, message: '请输入简介', trigger: ['change', 'blur'] }],
    goodsPrice: [{ required: true, message: '请输入价格', trigger: ['change', 'blur'] }],
    userName: [{ required: true, message: '请输入联系人', trigger: ['change', 'blur'] }],
    phone: [{ required: true, message: '请输入电话', trigger: ['change', 'blur'] }],
    wxNum: [{ required: true, message: '请输入微信号', trigger: ['change', 'blur'] }],
    address: [{ required: true, message: '请输入地址', trigger: ['change', 'blur'] }],
})
//分类数据
let categoryList = ref([])
//查询所有分类
let category = async () => {
    let res = await categoryApi()
    if (res.code == 200) {
        categoryList.value = res.data
    }
}

//新增，编辑提交
let commit = () => {
    //表单验证
    addRef.value?.validate(async (valid) => {
        if (valid) {
            //提交数据
            let res = await editGoodsApi(addModel);

            //如果res返回，并且code等于200
            if (res && res.code == 200) {
                //信息提示
                ElMessage.success(res.msg)
                //清空图片
                upImgRef.value.clearimg()
                //刷新表格
                getGoodsList()
                //关闭弹框
                dialogTableVisible.value=false
            }

        }
    })
}


//编辑
let editBtn = (row: Goods) => {
    console.log(row)
    //清除表单数据
    addRef.value?.resetFields()
    imgUrl.value = []
    fileList.value = []
    oldUrl.value = []

    dialogTableVisible.value = true

    //设置数据回显
    //nextTick在状态改变后立即使用，简称立刻更新
    nextTick(() => {
        Object.assign(addModel, row);
        //图片回显
        if (addModel.image) {
            let imgs = addModel.image.split(",");

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


//搜索
let searchBtn = () => {
    getGoodsList()
}

//在输入栏回车搜索
let searchKey = () => {
    getGoodsList()
}

//重置
let resetBtn = () => {
    //清除表单数据
    addRef.value?.resetFields()
    addModel.type="-1"
    //清空图片
    upImgRef.value.clearimg()

    //关闭弹窗
    dialogTableVisible.value=false
}

//表格数据
let tableList = ref([])

//获取分类数据
let getCategoryList = async () => {
    let res = await categoryApi();
    if (res && res.code == 200) {
        list = res.data;
    }
}
//表格商品分类数据
let list = reactive([{
    value: '',
    label: ''
}])


//列表查询
let getGoodsList = async () => {
    let res = await getMyUnusedListApi({
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
    addModel.image = url.substring(0, url.lastIndexOf(","));
}


//删除
let deleteBtn = async (row: UpdateParm) => {
    let confirm = await global.$myconfirm('确认删除该商品吗？')

    if (confirm) {
        let res = await deleteApi(row)
        if (res && res.code == 200) {
            ElMessage.success(res.msg)
            getGoodsList()
        }
    }
}

//修改状态
let editstatus = async (row: Goods) => {
    let confirm =await global.$myconfirm('确认上下架该商品吗？')
   console.log(global)
   if(confirm){
    if(row.status=='1'){
        row.status='0'
    }else if(row.status=='0'){
        row.status='1'
    }
        let res = await editGoodsApi(row)
        //如果res返回，并且code等于200
        if (res && res.code == 200) {
            //信息提示
            ElMessage.success(res.msg)
            //刷新表格
            getGoodsList()
        }
    }
}


// 分页方法
//page-size 改变时触发
//pagesize就是当前页显示多少条数据
let sizeChange = (size: number) => {
    searchPram.pageSize = size
    getGoodsList()
}

//current-page 改变时触发
let currentChange = (page: number) => {
    searchPram.currentPage = page
    getGoodsList()

}






onMounted(() => {
    getCategoryList()
    getGoodsList()
    category()
})





</script>

<style scoped></style>