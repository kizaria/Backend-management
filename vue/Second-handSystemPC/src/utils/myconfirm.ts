/**
 * 信息确定提示框
 */
import { ElMessageBox } from 'element-plus'

export default function myconfirm(text: string) {
    return new Promise((resolve, reject) => {
        ElMessageBox.confirm(text, '系统提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
        ).then(() => { //确定按钮
            resolve(true) //将promise的状态设置成功
        }).catch(() => { //取消
            reject(false) //将promise的状态设置失败
        })
    }).catch(() => {
        return false;
    })
}