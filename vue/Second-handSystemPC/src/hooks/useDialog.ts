import { reactive } from 'vue'

export default function useDialog() {
    let dialog = reactive(
        {
            title: '新增',
            visible: false,//是否显示弹框
            width: 630,
            height: 280
        }
    )
    //弹框显示
    function onShow() {
        dialog.visible = true
    }

    //弹框关闭
    let onClose = () => {
        dialog.visible = false
    }

    //弹框确定
    let onConfirm = () => {
        dialog.visible = false
    }

    return{
        dialog,
        onShow,
        onClose,
        onConfirm
    }
}