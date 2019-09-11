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
                alert(response.message)
            }
            console.log(response)
        },
        dataType: "json",
        contentType: "application/json"
    });
    console.log("所评论问题id " + questionId);
    console.log("评论内容 " + content);
}
