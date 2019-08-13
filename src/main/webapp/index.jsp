<%@ page language="java" contentType="text/html; ISO-8859-1" import="java.util.*" pageEncoding="utf-8" %>
<%@page import="java.util.*"%>
<%
    String path = request.getContextPath().replace("/", "");
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + "/" + path;
%>
<%
    Random random = new Random();
    int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>WebSocket</title>
    <link rel="stylesheet" type="text/css" href="css/iview.css">
    <script src="lib/vue.min.js"></script>
    <script src="js/iview.min.js"></script>
    <script src="js/jquery-1.8.3.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="lib/vue-resource.min.js"></script>

    <script type="text/javascript">
        var ws = null;
        //判断当前浏览器是否支持WebSocket
        if('WebSocket' in window) {
            var host = window.location.host;
            console.log(host);
            ws = new WebSocket("ws://"+host+"/websocket");
        } else {
            alert('当前浏览器 Not support websocket')
        }
        /*
         *监听三种状态的变化js会回调
         */
        ws.onopen = function(message) {

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
<%--                        <div class="input-group">--%>
<%--                            <input type="text" class="form-control" placeholder="" v-model="item.push">--%>
<%--                            <span class="input-group-btn">--%>
<%--                                    <button class="btn btn-default" type="button" @click="bingding(item.port,item.push)">绑定</button>--%>
<%--                                </span>--%>
<%--                        </div>--%>
<%--                        <button type="button" style="width: 150px" class="btn btn-primary" id="pub" @click="" name="sendBn">查看推送主题</button>--%>
                            <i-button type="primary" @click="showPush(item.port)">查看推送主题</i-button>
                            <Modal v-model="visible" width="800px" :closable="clo" title="推送主题">
                                <i-input search v-model="new_topic" enter-button="添加" @on-search="addTopic(item.port)" placeholder="Enter something..." ></i-input>
                                <i-table :columns="push" :data="pushTopics" width="500px">

                                </i-table>
                            </Modal>
                    </td>
                    <td>
                        <i-button type="primary" @click="showSub(item.port)">查看订阅主题</i-button>
                        <Modal v-model="subscribes" width="800px" :closable="clo" title="订阅主题">
                            <i-input search v-model="new_subscribe" enter-button="添加" @on-search="addSubscribe" placeholder="Enter something..." ></i-input>
                            <i-table :columns="subscribe" :data="subTopics" width="500px">
                            </i-table>
                        </Modal>
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
    <input type="hidden" id="path" value="<%=basePath%>">

</div>


<script>


</script>
<script type="text/javascript">
    var path = $('#path').val();
    var id = 1;
    $.ajax({
        // type:"post",
        // data:{"id":id},
        // url:path+"/test/getAll",
        // dataType:"json",
        // success:function (res) {
        //     console.log(res)
        //     // $(res).each(function (index) {
        //     //
        //     // })
        // }
    });
</script>
<script>
    var vm = new Vue({
        el:'#app',
        data(){
            return{
                new_topic:'',
                new_subscribe:'',
                id:'',
                port:'',
                visible: false,
                subscribes:false,
                curr_port:'',
                clo:false,
                i:1,
                flag:false,
                currentid:0,
                list:[
                {id:0,port:'9000',push:'manhole_monitor_up',receive:'manhole_monitor'}
            ],
                subscribe:[{title:'序号',key:'sid'},{title:'主题',key:'subscribe'},

                    {
                        title: '操作', key: 'action', width: 150, align: 'center', render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.deleted(params.index);
                                            this.$http.get('/subscribe/delSubscribe', {params: {id: params.row.id}}).then(function () {
                                                console.log('删除成功');

                                            }, function (err) {
                                                console.log('删除失败')
                                            })
                                        }
                                    }
                                }, '删除')
                            ])
                        }
                    }
                ],
                push:[{title:'序号',key:'id'},{title:'主题',key:'topic'},
                    {
                        title:'数据格式',key:'dataflag',width:150,align:'center',render:(h,params)=>{
                            let label;
                            if(params.row.dataflag === '0'){
                                label = '按十六进制转发';
                            }
                            if(params.row.dataflag === '1'){
                                label = '保留原数据';
                            }
                            return h("Select",{
                                props:{
                                    label:label,
                                    value:params.row.dataflag,
                                    transfer:"true"
                                },
                                on:{
                                    'on-change':e=>{
                                        // params.row.dataflag = e //改变下拉框的值
                                        console.log(e);
                                        this.$http.get('/data/dataType',{params:{id:params.row.id,dataflag:e}}).then(function () {
                                            console.log('成功修改传输方式');
                                        },function (err) {
                                            console.log("修改失败")
                                        })

                                    }
                                }
                            },[
                                h('Option',{
                                    props:{
                                        value:'0'
                                    }
                                },'按十六进制转发'),
                                h('Option',{
                                    props:{
                                        value:'1'
                                    }
                                },'保留原始数据')
                            ])
                        }
                    },
                    {title:'操作',key:'action',width:150,align:'center',render:(h,params)=>{
                    return h('div',[
                        h('Button',{
                            props:{
                                type:'error',
                                size:'small'
                            },
                            on:{
                                click:() =>{
                                    this.deletes(params.index);
                                    this.$http.get('/topic/delTopic',{params:{id:params.row.id}}).then(function () {
                                        console.log('删除成功');

                                    },function (err) {
                                        console.log('删除失败')
                                    })
                                }
                            }
                        },'删除')
                    ])
                }

            }],
                pushTopics:[{id:'1',topic:'manhole_monitor_up'}],
                subTopics:[]

            }
            },

        mounted(){
            this.get()
        },
        methods:{
            showPush: function (port) {
                this.curr_port = port;
                this.visible = true;
                this.$http.get('/topic/getAll',{params:{port:port}}).then(function (res) {
                    this.pushTopics = res.body;
                },function () {
                    console.log('请求失败')
                })
            },
            showSub:function(port){
                this.subscribes = true;
                this.curr_port = port;
                this.$http.get('/subscribe/getSub',{params:{port:port}}).then(function (res) {
                    this.subTopics = res.body;
                },function () {
                    console.log('请求失败')
                })
            },
            get(){
                //发起get请求
                //   this.$http.get('/wd/test').then(function (res) {
                //       console.log(res);
                //   },function () {
                //       console.log("请求失败")
                //   })
                var that = this;
                $.ajax({
                    url:path+"/link/getAll",
                    type:"get",
                    dataType:"json",
                    success:function (res) {
                        that.list = res;
                        // $(res).each(function (index) {
                        //     console.log(res[index].name)
                        // })
                        console.log(that.list);
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
                if(id == 1){
                    alert("该端口不可释放")
                }else{
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
                }

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
            },
            deletes:function (index) {
                this.pushTopics.splice(index,1);
            },
            deleted:function (index) {
                this.subTopics.splice(index,1);
            },
            addTopic(port){
                console.log("端口："+this.curr_port+"新增主题名称:"+this.new_topic);
                this.$http.post('/topic/add',{port:this.curr_port,topic:this.new_topic},{emulateJSON:true}).then(function () {
                    this.pushTopics.push({id:'3',topic:this.new_topic});
                },function (err) {
                    console.log('请求失败')
                })

            },
            addSubscribe(){
                console.log(this.new_subscribe)
                this.$http.post('/subscribe/addSubscribe',{port:this.curr_port,subscribe:this.new_subscribe},{emulateJSON:true}).then(function(){
                    this.subTopics.push({sid:'2',subscribe:this.new_subscribe});
                },function (err) {
                    console.log("添加失败")
                })
            }
        }
    });
</script>
</body>

</html>
