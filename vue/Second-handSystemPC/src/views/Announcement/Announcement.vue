<!-- 首页网站公告 -->
<template>
    <div class="top-category">
        <div class="container m-top-20">
            <div class="ref-goods">
                <div class="head">
                    <h3>网站信息</h3>
                </div>
                <el-timeline style="max-width: 700px;margin-left: 250px;">
                    <el-timeline-item v-for="item in announcementList"  :timestamp="item.time" placement="top">
                        <el-card>
                            <h4>{{item.title}}</h4>
                            <p>{{item.content}}</p>
                        </el-card>
                    </el-timeline-item>
                </el-timeline>
            </div>
        </div>
    </div>


</template>

<script setup>
import Breadcrumb from './Breadcrumb.vue'
import HomeBanner from '@/views/Home/components/HomeBanner.vue'
import { reactive, ref, onMounted, nextTick } from 'vue'
import { validateLocaleAndSetLanguage } from 'typescript';
import { getAnnouncementListApi } from '@/api/announcement/index'
import E from "wangeditor"
import { UserStore } from '@/stores/user/index';

let announcementList = ref([])

//查询公告信息
let getAnnouncement = async () => {
    let res = await getAnnouncementListApi()
    if (res.code == 200) {
        announcementList.value = res.data

    }
}
onMounted(() => {
    getAnnouncement()
})


</script>


<style scoped lang="scss">
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
            display: flex;
            padding: 0 40px 30px;
        }
    }

    .bread-container {
        padding: 25px 0;
    }
}
</style>