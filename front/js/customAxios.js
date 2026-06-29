import axios from "axios";
import Cookies from "universal-cookie";

const jwtAxios = axios.create()

const cookies = new Cookies(null, {path: '/', maxAge: 2592000})

const beforeRequest = (config) => {
    console.log('beforeRequest')

    const accessToken = cokkies.get("accessToken")
    if(!accessToken) {
        throw Error("No Token")
    }
    config.header["Authorization"] = "Bearer " + accessToken

    return config
}

const beforeResponse = (response) => {
    console.log('beforeResponse')

    return response
}

import { requestRefreshToken, saveToken } from "./api";

const errorResponse = (error) => {
    console.log('errorResponse')

    // 토큰이 만료된 경우에 동작
    if(error.response.status) {
        console.log('errorReponse')

        const status = error.response.status
        const res = error.response.data
        const errorMsg = res.error

        console.log(status, res, errorMsg)

        const refreshFn = async () => {
            console.log("Refresh Token")
            const data = await requestRefreshToken()
            saveToken("accessToken", data.accessToken)
            saveToken("refreshToken", data.refreshToken)

            error.config.headers["Authorization"] = "Bearer " + data.accessToken

            return await axios(error.config)
        }

        if(errorMsg.indexOf("expired") > 0) {
            return refreshFn()
        }else {
            return Promise.reject(error)
        }
    }
}

jwtAxios.interceptors.request.use(beforeRequest)
jwtAxios.interceptors.request.use(beforeRequest, errorResponse)

export default jwtAxios