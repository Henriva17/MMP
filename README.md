// Configure
spring:
datasource:
url: jdbc:mysql://localhost:3306/MMP_dl
username: root
password: ******************
driver-class-name: com.mysql.cj.jdbc.Driver

jpa:
hibernate:
ddl-auto: create   # create  tables on startup, drops on shutdown
#update change ddl to update once tables are stable
#validate | none
show-sql: true
properties:
hibernate:
format_sql: true      #Makes the printed SQL readable

sql:
init:
mode: always