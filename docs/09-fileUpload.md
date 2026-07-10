## 01. 파일 업로드 관련 설정(application.properties)
spring.servlet.multipart.enabled=true  
spring.servlet.multipart.max-file-size=3MB  
spring.servlet.multipart.max-request-size=30MB  
spring.webflux.multipart.max-in-memory-size=256KB  
com.ex3.khg.upload.path=upload  
spring.web.resources.static-locations=classpath:/static/,file:${com.ex3.khg.upload.path}/  

## 02. 업로드 관련 사항
- 이미지 파일만 업로드 가능
- 권한이 있는 이용자만 가능
- 조회는 모든 이용자 가능
- 파일명 중복을 막기 위해 UUID를 파일명 앞에 붙인다.