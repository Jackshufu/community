/**
 * Created by 舒先亮 on 2019/9/11.
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                $("#comment_section").hide()
            } else {
                if(response.code == 2004){
                //    跳出弹框问是否登录，用confirm确认框
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.c83b663ef84a8086&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                        //当确认登录的时候，跳转链接，然后设置页面可关闭为true,存到浏览器里面，在index.html中加入js的window.onload方法
                        window.localStorage.setItem("closeable", true)
                    }
                }else {
                    alert(response.message)
                }
            }
            console.log(response)
        },
        dataType: "json",
        contentType: "application/json"
    });
    console.log("所评论问题id " + questionId);
    console.log("评论内容 " + content);
}
