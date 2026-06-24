import axios from 'axios'

export const testApi = () => {
    console.log("test Api.....")
}
const url = "http://localhost:8080/api/v1/"

// 비동기 처리를 위한 async/await 추가
export const makeToken = async (mid, mpw) => {
    const path = url + "token/make"

    const data = {mid, mpw}

    const res = await axios.post(path, data)
    
    // 결과 데이터 반환
    return res.data
}