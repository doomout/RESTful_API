## 파일 업로드 관련 설정(application.properties)
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=3MB
spring.servlet.multipart.max-request-size=30MB
spring.webflux.multipart.max-in-memory-size=256KB
com.ex3.khg.upload.path=upload
spring.web.resources.static-locations=classpath:/static/,file:${com.ex3.khg.upload.path}/