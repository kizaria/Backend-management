<template>
    <div>
        <Breadcrumb/>
    </div>

    <div>
      <list></list>
    </div>



</template>

<script setup>
import Breadcrumb from './Breadcrumb.vue'
import list from './list.vue'
import HomeBanner from '@/views/Home/components/HomeBanner.vue'
import { reactive, ref ,onMounted,nextTick} from 'vue'
import { validateLocaleAndSetLanguage } from 'typescript';
import {getGoodsHelpApi} from '@/api/help/index'
import E from "wangeditor"
import { UserStore } from '@/stores/user/index';


</script>

<style lang="scss" scoped>


</style>