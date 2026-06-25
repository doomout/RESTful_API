import axios from "axios";

const jwtAxios = axios.create()
const beforeRequest = (config) => {
    console.log('beforeRequest')
    return config
}

jwtAxios.interceptors.request.use(beforeRequest)

export default jwtAxios