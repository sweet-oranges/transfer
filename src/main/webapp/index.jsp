<%@ page language="java" contentType="text/html; ISO-8859-1" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>WebSocket</title>
    <script src="lib/vue.min.js"></script>
    <script src="js/jquery-1.8.3.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css">

    <script type="text/javascript">
        var ws = null;
        //判断当前浏览器是否支持WebSocket
        if('WebSocket' in window) {
            var host = window.location.host;
            console.log(host);
            ws = new WebSocket("ws://"+host+"/ws/asset");
        } else {
            alert('当前浏览器 Not support websocket')
        }
        /*
         *监听三种状态的变化js会回调
         */
        ws.onopen = function(message) {
            // 连接回调
            // var message = {
            //     time: new Date(),
            //     text: "Hello world!",
            //     clientId:"asdfpdar23",
            // };
            // ws.send(JSON.stringify(message));

        };
        ws.onclose = function(message) {
            // 断开连接回调
        };
        ws.onmessage = function(message) {
            console.log(message);
            // 消息监听
            showMessage(message.data);
        };
        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function() {
            ws.close();
        };
        //关闭连接
        function closeWebSocket() {
            ws.close();
        }
        //发送消息
        function send() {
            console.log("进入发送消息方法")
            var input = document.getElementById("msg");
            var text = input.value;

            // 消息体JSON 对象 对应JAVA 的 Msg对象
            var data = {
                // 定点发送给其他用户的userId
                toUid: "",
                data: text
            }

            ws.send(JSON.stringify(data));
            input.value = "";
        }

        function showMessage(message) {
            console.log(message);
            message= message.split(",");
            var br = document.createElement("br");
            var text = document.createTextNode(message);
            var myDate = new Date();
            $("#showText").append("<span>"+message[0]+"&nbsp;&nbsp;:&nbsp;&nbsp;"+message[1]+"</span>");
            document.getElementById("showText").append(br);

        }
    </script>
    <style>
        body{
            background-color: #303F68;
        }
        #showText{
            /*margin: 0 auto;*/
            margin-top: 50px;
            background-color: #ffe899;
            overflow: auto;
            height: 300px;
        }
        p{
            font-size: 25px;
        }
        #sendText{
            margin-top: 50px;
            background-color: #ffe899;

            height: 270px;
        }

        #addPort{
            background-color: #ffe899;
            height:350px;
            overflow: auto;
        }

    </style>

</head>

<body>

<div id="app">
    <div id="addPort">
        <%--        订阅一个发送主题:&nbsp;&nbsp;&nbsp;&nbsp;--%>
        <%--        <div style="display:inline-block;">--%>
        <%--            <input type="text" class="form-control" placeholder="topic" id="topic" aria-describedby="basic-addon1">--%>
        <%--        </div>--%>
        <%--        <button type="button" style="width: 100px" class="btn btn-primary" id="sub" name="sendBn" @click="sub">Subscribe</button>--%>
        <%--        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订阅一个接收主题:&nbsp;&nbsp;&nbsp;&nbsp;--%>
        <%--        <div style="display:inline-block;">--%>
        <%--            <input type="text" class="form-control" placeholder="topic" id="receive" aria-describedby="basic-addon1">--%>
        <%--        </div>--%>
        <%--        <button type="button" style="width: 100px" class="btn btn-primary" id="rec" name="sendBn" @click="receive">Subscribe</button>--%>


        <div style="margin-top: 30px;margin-bottom: 30px">
            <label>添加一个tcp端口:&nbsp;&nbsp;&nbsp;&nbsp;</label>
            <input type="text" style="width: 200px;display: inline-block" v-model="port"class="form-control" placeholder="port" id="port" aria-describedby="basic-addon1">
            <button type="button" style="width: 100px" class="btn btn-primary" id="add" @click="add" name="sendBn">添加</button>
        </div>
        <div>

            <table class="table table-bordered table-hover table-striped">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>端口</th>
                    <th>操作</th>
                    <th>推送主题</th>
                    <th>订阅主题</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in list" :key="item.id">
                    <td>{{item.id}}</td>
                    <td>{{item.port}}</td>
                    <td>
                        <a href="" @click.prevent="del(item.id)">释放端口</a>
                    </td>
                    <td>
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="" v-model="item.push">
                            <span class="input-group-btn">
                                    <button class="btn btn-default" type="button" @click="bingding(item.port,item.push)">绑定</button>
                                </span>
                        </div>
                    </td>
                    <td>
                        <input type="text" class="form-control" placeholder="Search for...">
                    </td>
                </tr>
                </tbody>
            </table>


        </div>


    </div>

    <div id="showText">
        <p>服务器接收的数据：</p>
    </div>
    <div id="sendText">
        <p>向设备发送数据：</p>
        <%--<input type="text" size="50" id="msg" name="msg" placeholder="输入十六进制格式的数据" />--%>
        <form role="form">
            <div class="form-group" style="width: 500px;">
                <textarea class="form-control" id="msg" name="msg" rows="5" cols="2" placeholder="输入十六进制格式的字符串"></textarea>
            </div>
        </form>
        <button type="button" style="width: 500px" class="btn btn-primary" id="sendBn" @click="sendMsg" name="sendBn">发送</button>
    </div>

</div>


<script>
    // $('#sendBn').click(function () {
    //     var input = document.getElementById("msg");
    //     var text = "m"+input.value;
    //     ws.send(text);
    //     input.value = "";
    // })
    //
    // $('#sub').click(function () {
    //     var input = document.getElementById("topic");
    //     if(input.value == ""){
    //         // alert("不能订阅一个空的主题")
    //     }else{
    //         var text = "t"+input.value;
    //         ws.send(text)
    //         input.value="";
    //     }
    //
    // })
    //
    // $('#add').click(function () {
    //     var input = document.getElementById("port");
    //     if(input.value == ""){
    //
    //     }else{
    //         var text = "p"+input.value;
    //         ws.send(text);
    //         input.value="";
    //
    //
    //     }
    // })

</script>
<script>
    var vm = new Vue({
        el:'#app',
        data:{
            id:'',
            port:'',
            i:1,
            flag:false,
            list:[
                {id:0,port:'9000',push:'manhole_monitor_up',receive:'manhole_monitor'}
            ]
        },
        created(){
            this.get()
        },
        methods:{
            get(){
                //发起get请求
                //   this.$http.get('/wd/test').then(function (res) {
                //       console.log(res);
                //   },function () {
                //       console.log("请求失败")
                //   })
                $.ajax({
                    url:"",
                    type:"get",
                    dataType:"json",
                    success:function (res) {
                        cosole.log(res)
                    }
                })
            },
            add(){
                //添加的方法
                console.log('ok')
                for(var i=0;i<this.list.length;i++){
                    if(this.port == this.list[i].port){
                        this.flag = false
                    }else{
                        this.flag = true
                    }
                }
                if(this.flag){
                    this.flag = false;
                    console.log(this.flag)
                    var link = {id:this.i++,port:this.port}
                    this.list.push(link);
                    var input = document.getElementById("port");
                    if(input.value == ""){

                    }else{
                        var message = {
                            flag:"p",
                            text:input.value,
                        }
                        ws.send(JSON.stringify(message));
                        input.value="";
                    }

                }


            },
            del(id){
                //根据id删除数据
                this.list.some((item,i)=>{
                    if(item.id == id){
                        this.list.splice(i,1);
                        return true;
                    }
                });
                var input = document.getElementById("port");
                var message = {
                    flag:"d",
                    text:input.value,
                }
                ws.send(JSON.stringify(message));
            },
            // sub(){
            //     var input = document.getElementById("topic");
            //     if(input.value == ""){
            //         // alert("不能订阅一个空的主题")
            //     }else{
            //         var text = "t"+input.value;
            //         ws.send(text)
            //         input.value="";
            //     }
            // },
            sendMsg(){
                var input = document.getElementById("msg");
                var message = {
                    flag:"m",
                    text:input.value,
                }
                ws.send(JSON.stringify(message));
                input.value = "";
            },
            // receive(){
            //     var input = document.getElementById("receive");
            //     var text = "r"+input.value;
            //     ws.send(text);
            //     input.value = "";
            // },
            bingding(port,push){
                console.log("进入推送主题方法")
                console.log(port,push);
                //列表绑定推送主题方法
                if(push == ""){
                    alert("不能订阅一个空主题");
                }else{
                    var message = {
                        flag:"t",
                        text:push,
                        port:port,
                    }
                    ws.send(JSON.stringify(message));
                    alert("绑定橙功")
                }
            }
        }
    });
</script>
</body>

</html>
