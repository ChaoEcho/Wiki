
start cmd /k "D:\ProgramFiles\Redis\redis-server.exe"


start cmd /k "D:\ProgramFiles\rocketmq\bin\mqnamesrv.cmd"


start cmd /k "D:\ProgramFiles\rocketmq\bin\mqbroker.cmd -n localhost:9876 autoCreateTopicEnable=true"