<!-- 求购信息详情 -->
<template>
    <div class="top-category">
        <div class="container m-top-20">
            <div class="ref-goods">
                <div class="head">
                    <h3>求购信息</h3>
                </div>
                <div class="body">
                    <router-link :to="`/help/${ss}`" v-animate="['slideUpBigIn']">
                        <div style="display: flex;box-sizing: border-box;">
                            <!-- 用户头像 -->
                            <img style="width: 50px; height: 50px; border-radius: 50%;" :src="help.picture">
                            <div style="margin-left: 10px; flex: 1 1 0%;">
                                <div style="font-weight:bolder;padding-bottom: 5px;"> {{ help.title }}</div>
                                <div>
                                    <span style="padding-right: 20px;">{{ help.nickName }}</span>
                                    <span>{{ help.time }}</span>
                                </div>
                            </div>
                        </div>

                        <!-- 说说内容 -->
                        <div style="padding-top: 20px;padding-left: 20px;" v-html="help.content">
                        </div>

                    </router-link>

                    <div style="margin: 10px 0">
                        <el-input type="textarea" v-model="addModel.content"></el-input>
                        <div style="text-align: right; margin: 10px 0">
                            <el-button @click="comment()">提交</el-button>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <div class="top-category">
        <div class="container m-top-20">
            <div class="ref-goods">
                <div class="head">
                    <h3>-{{ CurrentCate }}-</h3>
                </div>
                <div class="body">


                    <div style="margin: 20px 0">
                        <div
                            style="margin: 10px ; font-size: 24px; padding: 10px 0; border-bottom: 1px solid #ccc; text-align: left">
                            评论列表
                        </div>
                        <div style="margin: 20px 0; text-align: left;">
                            <div style="padding: 10px 0; " v-for="item in commentList" :key="item.id">
                                <div style="display: flex">
                                    <div style="width: 80px">
                                        <el-avatar :size="50" :src="item.avatar"></el-avatar>
                                    </div>
                                    <div style="flex: 1">
                                        <div>{{ item.username }}</div>
                                        <div style="margin-top: 10px; color: #666">{{ item.content }}</div>
                                    </div>
                                </div>
                                <!-- 多级回复 -->
                                <div style="text-align: right;">
                                    <el-button type="text" @click="reply(item.id, item.username)">回复</el-button>
                                    <!-- 回复列表 -->
                                    <div v-if="item.children.length" style="text-align: left;margin-left: 100px; background-color:aliceblue;padding:10px;
                                    border-radius: 10px">
                                        <div v-for="sub in item.children" :key="sub.id">
                                            <div style="padding:5px 0"><b style="cursor: pointer"
                                                    @click="reply(sub.pid, sub.username)">{{ sub.username }}</b>
                                                <span>回复
                                                    <span style="color:cornflowerblue">@{{ sub.target }}</span>
                                                    <span style="color: #666; margin-left: 10px">{{ sub.content
                                                        }}</span></span>
                                            </div>
                                            <!-- <span style="float: right; font-size: 13px; color: #666; margin-top: 3px">{{sub.creatime}}</span> -->
                                        </div>
                                    </div>

                                    <div v-if="launch == item.id" style="margin: 10px 0">
                                        <el-input type="textarea" v-model="moreComment.content"></el-input>
                                        <div style="text-align: right; margin: 10px 0">
                                            <el-button @click="sunComment()">提交</el-button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- </div> -->
</template>

<script setup>
import Breadcrumb from './Breadcrumb.vue'
import HomeBanner from '@/views/Home/components/HomeBanner.vue'
import { reactive, ref, onMounted, nextTick } from 'vue'
import { validateLocaleAndSetLanguage } from 'typescript';
import { getGoodsHelpApi, getGoodsHelpByIdApi } from '@/api/help/index'
import E from "wangeditor"
import { useRoute } from 'vue-router'
import { UserStore } from '@/stores/user/index';
import { addCommentApi, getCommentApi } from '@/api/comment/index'

//获取pinia
let store = UserStore()

let route = useRoute()

//新增评论
let addModel = reactive({
    content: '',
    username: store.getNickName,
    avatar:store.getavatar,
    userId: store.getUserId,
    foreignId: route.params.id,
})
let commentList = ref([])

let help = ref({})

//获取求购信息详情
let getGoodsHelpById = async () => {
    let res = await getGoodsHelpByIdApi({ id: route.params.id })
    if (res.code == 200) {
        help.value = res.data
    }
}

//发布评论
let comment = async () => {
    let res = await addCommentApi(addModel)
    if (res.code == 200) {
        ElMessage.success('评论成功')
        addModel.content = ''
        commentList.value = ''
        getComment()
    }
}

//获取列表
let getComment = async () => {
    let res = await getCommentApi({ id: route.params.id })
    if (res.code == 200) {
        commentList.value = res.data
        console.log(res.data)
    }
}
let launch = ref(false)
//回复评论
let moreComment = reactive({
    username: store.getNickName,
    userId: store.getUserId,
    content: '',
    foreignId: route.params.id,
    pid: '',
    target: ''
})

//
let reply = (id, target) => {
    if (launch.value == id) {
        launch.value = -1
        return;
    }
    launch.value = id
    moreComment.pid = id
    moreComment.target = target

}

//子级评论
let sunComment = async () => {
    let res = await addCommentApi(moreComment)
    if (res.code == 200) {
        ElMessage.success('评论成功')
        moreComment.comment = ''
        launch.value = -1
    }
    commentList.value = ''
    getComment()
}

onMounted(() => {
    getGoodsHelpById()
    getComment()
})


</script>

<style lang="scss" scoped>
.top-category {
    h3 {
        font-size: 28px;
        color: #666;
        font-weight: normal;
        text-align: center;
        line-height: 100px;
    }

    .sub-list {
        margin-top: 20px;
        background-color: #fff;

        ul {
            display: flex;
            padding: 0 32px;
            flex-wrap: wrap;

            li {
                width: 168px;
                height: 160px;


                a {
                    text-align: center;
                    display: block;
                    font-size: 16px;

                    img {
                        width: 100px;
                        height: 100px;
                    }

                    p {
                        line-height: 40px;
                        align-items: center;
                    }

                    &:hover {
                        color: $xtxColor;
                    }
                }
            }
        }
    }

    .ref-goods {
        background-color: #fff;
        margin-top: 20px;
        position: relative;

        .head {

            .xtx-more {
                position: absolute;
                top: 20px;
                right: 20px;
            }

            .tag {
                text-align: center;
                color: #999;
                font-size: 20px;
                position: relative;
                top: -20px;
            }
        }

        .body {
            flex-wrap: wrap;
            // display: flex;
            //   justify-content: space-around;
            padding: 0 40px 30px;
        }
    }

    .bread-container {
        padding: 25px 0;
    }
}
</style>