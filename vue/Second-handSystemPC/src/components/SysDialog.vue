<template>
  <el-dialog
   :model-value="props.visible" 
   :title="props.title" 
   :width="props.width + 'px'" 
   append-to-body
   :close-on-click-modal="false"	
   :before-close="onClose">
    
    
    <div class="container" :style="{height:height+'px'}">
    <slot name="content"></slot>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="danger" @click="onClose">取消</el-button>
        <el-button type="primary" @click="onConfirm">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">

//弹框数据类型
interface DialogProps {
  title: string,//弹框标题
  visible: boolean,//弹框是否显示
  width: number,//弹框的宽度
  height: number//弹框的高度
}

//接收父组件传递的参数并设置默认值
let props = withDefaults(defineProps<DialogProps>(), {
  title: '新增',
  visible: false,//是否显示弹框
  width: 630,
  height: 280
})

//defineEmits调用父组件的方法
let emit = defineEmits(['onClose', 'onConfirm'])
//弹框关闭
let onClose = () => {
  emit('onClose')
}
//弹框确定
let onConfirm = () => {
  emit('onConfirm')
}
</script>

<style lang="scss" scope>
.container {
  overflow-x: initial; //如果它溢出了元素的内容区-剪辑div元素的左/右边缘内容：initial	将此属性设置为其默认值。
  overflow-y: auto; //如果溢出框，则应该提供滚动机制。
}

.el-dialog {
  border-top-left-radius: 7px !important;//为左上边框设置为圆角
  border-top-right-radius: 7px !important;//为右上边框设置为圆角

  .el-dialog__header {
    margin-right: 0px;
    border-top-left-radius: 7px !important;
    border-top-right-radius: 7px !important;
    background-color: #009688 !important;

    .el-dialog__title {
      color: #ffffff;
      font-size: 16px;
      font-weight: 600;
    }
  }

  .el-dialog__headerbtn {
    .el-dialog__close {
      color: #ffffff;
    }
  }

  .el-dialog__body {
    padding: 10px;
  }

  .el-dialog__footer {
    border-top: 1px solid #e8eaec !important;//规定上边框的宽度，样式，颜色
    padding: 10px;
  }
}
</style>