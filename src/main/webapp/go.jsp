<%@ page language="java" contentType="text/html; ISO-8859-1" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Java后端WebSocket的Tomcat实现</title>
    <script src="js/jquery.min.js"></script>
<%--&lt;%&ndash;    <script src="js/jquery-1.8.3.min.js"></script>&ndash;%&gt;--%>
    <link rel="stylesheet" type="text/css" href="css/iview.css">

    <script src="lib/vue.min.js"></script>

<%--    <link rel="stylesheet" type="text/css" href="http://unpkg.com/iview/dist/styles/iview.css">--%>
    <script src="js/iview.min.js"></script>
<%--    <script type="text/javascript" src="http://vuejs.org/js/vue.min.js"></script>--%>
<%--    <script type="text/javascript" src="http://unpkg.com/iview/dist/iview.min.js"></script>--%>
<%--    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">--%>
</head>
<body>
<%--Welcome<br/><input id="text" type="text"/>--%>
<%--<button onclick="send()">发送消息</button>--%>
<%--<hr/>--%>
<%--<button onclick="closeWebSocket()">关闭WebSocket连接</button>--%>
<%--<hr/>--%>
<%--<div id="message"></div>--%>
<!-- 模态框（Modal） -->
<div id="app">
    <i-input></i-input>
    <i-button @click="show">Click me!</i-button>
    <Modal v-model="visible" title="Welcome">Welcome to iView</Modal>
</div>

<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
    Launch demo modal
</button>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
                <p>One fine body…</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>

<%--<script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>--%>
<%--<script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>--%>
<script>
    new Vue({
        el: '#app',
        data: {
            visible: false
        },
        methods: {
            show: function () {
                this.visible = true;
            }
        }
    })
</script>
</body>

<script type="text/javascript">
    // var websocket = null;
    // //判断当前浏览器是否支持WebSocket
    // if ('WebSocket' in window) {
    //     websocket = new WebSocket('ws://localhost:8888/websocket');
    // }
    // else {
    //     alert('当前浏览器 Not support websocket')
    // }
    //
    // //连接发生错误的回调方法
    // websocket.onerror = function () {
    //     setMessageInnerHTML("WebSocket连接发生错误");
    // };
    //
    // //连接成功建立的回调方法
    // websocket.onopen = function () {
    //     setMessageInnerHTML("WebSocket连接成功");
    // }
    //
    // //接收到消息的回调方法
    // websocket.onmessage = function (event) {
    //     setMessageInnerHTML(event.data);
    // }
    //
    // //连接关闭的回调方法
    // websocket.onclose = function () {
    //     setMessageInnerHTML("WebSocket连接关闭");
    // }
    //
    // //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    // window.onbeforeunload = function () {
    //     closeWebSocket();
    // }
    //
    // //将消息显示在网页上
    // function setMessageInnerHTML(innerHTML) {
    //     document.getElementById('message').innerHTML += innerHTML + '<br/>';
    // }
    //
    // //关闭WebSocket连接
    // function closeWebSocket() {
    //     websocket.close();
    // }
    //
    // //发送消息
    // function send() {
    //     var message = document.getElementById('text').value;
    //     websocket.send(message);
    // }
</script>
</html>