<template>
	<div class="login-register">
		<div class="contain">
			<div class="big-box" :class="{ active: isLogin }">
				<div class="big-contain" key="bigContainLogin" v-if="isLogin">
					<div v-if="isPass">
						<div class="btitle">修改密码</div>
						<div class="bform">
						</div>
						<el-form class="loginform" :model="forgotModel" ref="forgotRef" :rules="forgotrules"
							:inline="false" size="defalut">

							<el-form-item prop="username">
								<el-input placeholder="请输入用户名" v-model="forgotModel.username"></el-input>
							</el-form-item>

							<el-form-item prop="code">
								<el-input placeholder="请输入验证码" v-model="forgotModel.code">
									<template #suffix>
										<div v-if="messageCodeVis" class="second-text">{{ countdown }}秒后重新获取</div>
										<el-button v-else style="color: rgb(57,167,176);" type="primary" link
											@click="sendCode()">获取验证码</el-button>
									</template>
								</el-input>
							</el-form-item>
							<el-form-item prop="password">
								<el-input type="password" placeholder="请输入密码" v-model="forgotModel.password"></el-input>
							</el-form-item>
						</el-form>

					</div>

					<div v-else>
						<div class="btitle">账户登录</div>
						<div class="bform">
						</div>
						<el-form class="loginform" :model="loginModel" ref="from" :rules="rules" :inline="false"
							size="large">
							<el-form-item prop="username">
								<el-input placeholder="请输入用户名" v-model="loginModel.username"></el-input>
							</el-form-item>
							<el-form-item prop="password">
								<el-input type="password" placeholder="请输入密码" v-model="loginModel.password"></el-input>
							</el-form-item>
						</el-form>


					</div>
					<button style="margin-top: 50px;" class="bbutton" @click="commit()">确认</button>

				</div>
				<div class="big-contain" key="bigContainRegister" v-else>
					<div class="btitle">创建账户</div>
					<div class="bform">

						<el-form class="loginform" :model="registerModel" ref="registerfrom" :rules="registerRules"
							:inline="false" size="default">

							<el-form-item prop="username">
								<el-input placeholder="请输入用户名" v-model="registerModel.username"></el-input>
							</el-form-item>


							<el-form-item prop="password">
								<el-input type="password" placeholder="请输入密码"
									v-model="registerModel.password"></el-input>
							</el-form-item>

							<el-form-item prop="phone">
								<el-input placeholder="请输入手机号" v-model="registerModel.phone"></el-input>
							</el-form-item>

							<el-form-item prop="code">
								<el-input placeholder="请输入验证码" v-model="registerModel.code">
									<template #suffix>
										<div v-if="messageCodeVis" class="second-text">{{ countdown }}秒后重新获取</div>
										<el-button v-else style="color: rgb(57,167,176);" type="primary" link
											@click="sendCode()">获取验证码</el-button>
									</template>
								</el-input>
							</el-form-item>
						</el-form>
					</div>
					<button class="bbutton" @click="register()">注册</button>
				</div>
			</div>
			<div class="small-box" :class="{ active: isLogin }">
				<div class="small-contain" key="smallContainRegister" v-if="isLogin">
					<div class="stitle">你好，朋友!</div>
					<p class="scontent">开始注册，和我们一起旅行</p>
					<button class="sbutton" @click="changeType">注册</button>
					<button style="margin-top: 10px;" class="sbutton" @click="$router.push('/')">进入网站</button>
					<button style="margin-top: 10px;" class="sbutton" v-if="!isPass" @click="isPassword()">忘记密码</button>
					<button style="margin-top: 10px;" class="sbutton" v-else @click="isPassword()">登录</button>
				</div>
				<div class="small-contain" key="smallContainLogin" v-else>
					<div class="stitle">欢迎回来!</div>
					<p class="scontent">与我们保持联系，请登录你的账户</p>
					<button class="sbutton" @click="changeType">登录</button>
					<button style="margin-top: 10px;" class="sbutton" @click="$router.push('/')">进入网站</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { sendMsg, loginApi, registerApi,forgotPasswordApi } from '@/api/wxuser/index'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import router from '@/router';
import { UserStore } from '@/stores/user/index';
import { el } from 'element-plus/es/locales.mjs';


//获取pinia
let store = UserStore()

let isLogin = ref(true)
let isPass = ref(false)
let emailError = ref(false)
let passwordError = ref(false)
let existed = ref(false)

//表单ref属性
let from = ref<FormInstance>();
let loginModel = reactive({
	username: "",
	password: ""
})

//表单验证规则
let rules = {
	username: [{ required: true, message: '请输入用户名', trigger: 'change' }],
	password: [{ required: true, message: '请选择密码', trigger: 'change' }]
}

// 提交
let commit = () => {

	if (isPass.value) {
		console.log("tt")
		//表单验证
		forgotRef.value?.validate(async (valid) => {
			if (valid) {
				//提交数据
				let res = await forgotPasswordApi(forgotModel)
				console.log(res)
				//如果res返回，并且code等于200
				if (res && res.code == 200) {
					isPass.value=!isPass.value
					//信息提示
					ElMessage.success(res.msg)
				} else {
					router.push({ path: '/login' })
				}

			}
		})
	} else {
		//表单验证
		from.value?.validate(async (valid) => {
			console.log("dd")
			if (valid) {
				//提交数据
				let res = await loginApi(loginModel)
				console.log(res)
				//如果res返回，并且code等于200
				if (res && res.code == 200) {
					console.log(res.data)
					//   //存入id
					store.setUserId(res.data.userId)
					//存入userName
					store.setNickName(res.data.userName)
					//   //存入token
					store.setToken(res.data.token)
					//存入头像
					store.setavatar(res.data.avatar)
					//   //跳转
					router.push("/");
					//信息提示
					ElMessage.success(res.msg)
				} else {
					router.push({ path: '/login' })
				}

			}
		})
	}

}
//表单ref属性
let registerfrom = ref<FormInstance>();
let registerModel = reactive({
	username: '',
	password: '',
	phone: '',
	code: ''
})
//表单验证规则
let registerRules = {
	username: [{ required: true, message: '请输入用户名', trigger: 'change' }],
	password: [{ required: true, message: '请输入密码', trigger: 'change' }],
	phone: [{ required: true, message: '请输入手机号', trigger: 'change' }],
	code: [{ required: true, message: '请输入验证码', trigger: 'change' }]
}

let register = () => {
	//表单验证
	registerfrom.value?.validate(async (valid) => {
		if (valid) {
			//提交数据
			let res = await registerApi(registerModel)
			console.log(res)
			//如果res返回，并且code等于200
			if (res && res.code == 200) {
				//信息提示
				ElMessage.success(res.msg)
				isLogin.value=!isLogin.value
			} else {

			}

		}
	})
}
let messageCodeVis = ref(false)
let countdown = ref(0)

//获取验证码
let sendCode = async () => {
	//判断是注册还是忘记密码
	//isPass为true是为忘记密码
	if (isPass.value) {
		console.log("执行忘记密码")
		countdown.value = 60
		messageCodeVis.value = true
		startCountdown()
		let res = await sendMsg(forgotModel)
		if (res.code == 200) {
		}
	} else {
		console.log("执行注册")
		if (!registerModel.phone) {
			alert("请输入手机号")
			return;
		}
		countdown.value = 60
		messageCodeVis.value = true
		startCountdown()
		let res = await sendMsg(registerModel)
		if (res.code == 200) {

		}
	}


}
/* 倒计时函数 */
const startCountdown = () => {
	const intervalId = setInterval(() => {
		if (countdown.value > 0) {
			countdown.value--;
		} else {
			clearInterval(intervalId)
			messageCodeVis.value = false
		}
	}, 1000)
}


let changeType = () => {
	isLogin.value = !isLogin.value
	// form.username = ''
	// form.useremail = ''
	// form.userpwd = ''
}

let isPassword = () => {
	isPass.value = !isPass.value
}

//忘记密码
let forgotModel = reactive({
	username: '',
	password: '',
	code: ''
})
let forgotRef = ref<FormInstance>();

//表单验证规则
let forgotrules = {
	username: [{ required: true, message: '请输入用户名', trigger: 'change' }],
	password: [{ required: true, message: '请输入密码', trigger: 'change' }],
	code: [{ required: true, message: '请输入验证码', trigger: 'change' }]
}


</script>

<style scoped="scoped">
.login-register {
	width: 100vw;
	height: 100vh;
	box-sizing: border-box;
}

.contain {
	width: 60%;
	height: 80%;
	position: relative;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	background-color: #fff;
	border-radius: 20px;
	box-shadow: 0 0 3px #f0f0f0,
		0 0 6px #f0f0f0;
}

.big-box {
	width: 70%;
	height: 100%;
	position: absolute;
	top: 0;
	left: 30%;
	transform: translateX(0%);
	transition: all 1s;
}

.big-contain {
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
}

.btitle {
	font-size: 1.5em;
	font-weight: bold;
	color: rgb(57, 167, 176);
}

.bform {
	width: 100%;
	height: 40%;
	padding: 2em 0;
	display: flex;
	flex-direction: column;
	justify-content: space-around;
	align-items: center;
}

.bform .errTips {
	display: block;
	width: 50%;
	text-align: left;
	color: red;
	font-size: 0.7em;
	margin-left: 1em;
}

.bform input {
	width: 50%;
	height: 30px;
	border: none;
	outline: none;
	border-radius: 10px;
	padding-left: 2em;
	background-color: #f0f0f0;
}

.bbutton {
	width: 20%;
	height: 40px;
	border-radius: 24px;
	border: none;
	outline: none;
	background-color: rgb(57, 167, 176);
	color: #fff;
	font-size: 0.9em;
	cursor: pointer;
}

.small-box {
	width: 30%;
	height: 100%;
	background: linear-gradient(135deg, rgb(57, 167, 176), rgb(56, 183, 145));
	position: absolute;
	top: 0;
	left: 0;
	transform: translateX(0%);
	transition: all 1s;
	border-top-left-radius: inherit;
	border-bottom-left-radius: inherit;
}

.small-contain {
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
}

.stitle {
	font-size: 1.5em;
	font-weight: bold;
	color: #fff;
}

.scontent {
	font-size: 0.8em;
	color: #fff;
	text-align: center;
	padding: 2em 4em;
	line-height: 1.7em;
}

.sbutton {
	width: 60%;
	height: 40px;
	border-radius: 24px;
	border: 1px solid #fff;
	outline: none;
	background-color: transparent;
	color: #fff;
	font-size: 0.9em;
	cursor: pointer;
}

.big-box.active {
	left: 0;
	transition: all 0.5s;
}

.small-box.active {
	left: 100%;
	border-top-left-radius: 0;
	border-bottom-left-radius: 0;
	border-top-right-radius: inherit;
	border-bottom-right-radius: inherit;
	transform: translateX(-100%);
	transition: all 1s;
}
</style>
