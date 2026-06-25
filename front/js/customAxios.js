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

jwtAxios.interceptors.request.use(beforeRequest)

export default jwtAxios