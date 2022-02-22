# 镜像基于java:8
FROM java:8
# 作者
MAINTAINER rayfoo@qq.compp
#创建项目日志存放的文件夹
RUN hash -p /app
# 匿名挂载目录
VOLUME /app
# 项目暴露的端口号
EXPOSE 10000
# 添加环境变量
ENV JAVA_OPTS="-Xms512m -Xmx512m"
# 将jar包添加到容器中 /app/目录并更名为xxx.jar
ADD ./target/myblog-0.0.1-SNAPSHOT.jar /app/myblog-0.0.1-SNAPSHOT.jar
# 运行jar包命令 "nohup" "&" 可省略
# -Djava.security.egd=file:/dev/./urandom 加速tomcat启动
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/myblog-0.0.1.jar", "--spring.profiles.active=dev", "--server.port=10000"]
