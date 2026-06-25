import axios from 'axios';
import Cookies from "universal-cookie";
import jwtAxios from './customAxios';

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

// 쿠키 기능 설정(기본 경로 /, 30일 동안 저장)
const cookies = new Cookies(null, {path: '/', maxAge: 2592000})

export const saveToken = (tokenName, tokenValue) => {
    cookies.set(tokenName, tokenValue)
}

export const getSamples = async (pageNum) => {
    const path = url + "samples/list"
    const res = await jwtAxios.get(path)

    return res.data
}