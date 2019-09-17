/**
 *
 * Created by 舒先亮 on 2019/9/11.
 */

/**
 * 提交回复
 * */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, content, 1);
}

function comment2target(targetId, content, type) {
    if (!content) {
        alert("您还没有输入回复内容呢~~~")
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
                $("#comment_section").hide()
            } else {
                if (response.code == 2004) {
                    //    跳出弹框问是否登录，用confirm确认框
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.c83b663ef84a8086&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                        //当确认登录的时候，跳转链接，然后设置页面可关闭为true,存到浏览器里面，在index.html中加入js的window.onload方法
                        window.localStorage.setItem("closeable", true)
                    }
                } else {
                    alert(response.message)
                }
            }
            console.log(response)
        },
        dataType: "json",
        contentType: "application/json"
    });
    console.log("所评论问题id " + targetId);
    console.log("评论内容 " + content);
}

/**
 * 提交二级评论
 * */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var comment = $("#input-" + commentId).val();
    comment2target(commentId, comment, 2);
}

/**
 * 展开二级评论
 */
function collapseComment(e) {
    console.log(e)
    var id = e.getAttribute("data-id");
    console.log(id);
    var comments = $("#comment-" + id);
    console.log(comments);
    //获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //    二级评论已经展开了，折叠
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContent = $("#comment-" + id);
        //判断如果子标签不只包含评论的子标签的话，说明子评论标签内容不为空，则不需要重新each遍历追加子评论内容了，直接展开就好了；不需要加载，以后只要提交回复就能够按照时间顺序展示在最上面
        if(subCommentContent.children().length != 1){
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in")
            e.classList.add("active");
        }else {
            //要不然就each遍历一遍，然后展示追加到子评论模块
            $.getJSON("/comment/" + id, function (data) {
                console.log(data)
                //reverse()方法将查出来的从上到下按顺序展示出来，即时间倒序
                $.each( data.data.reverse(), function( index,comment ) {
                    console.log(comment)

                    var mediaLeftElement = $("<div/>", {
                        "class":"media-left"
                    }).append($("<img/>", {
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class":"media-body menu-tag-sub",
                    }).append($("<h5/>", {
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>", {
                        "html":comment.content
                    })).append($("<div/>", {
                        "class":"menu",
                    }).append($("<span/>", {
                        "class":"pull-right date-tag menu-tag",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));


                    var commentForeachSubElement = $("<div/>", {
                        "class":"comment-foreach-sub",
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var  commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                        //这个id是我们点击的id，不是外层的id
                        // "id": "comment-" + id,
                        // html: items.join( "" )是将上面所有的元素都写进来
                        // html: comment.content
                    }).append(commentForeachSubElement);
                    // mediaLeftElement.append(avatarElement);
                    // commentForeachSubElement.append(mediaBodyElement);
                    // commentElement.append(commentForeachSubElement);
                    subCommentContent.prepend(commentElement);
                });

                /* $("<div/>", {
                 "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comment",
                 // html: items.join( "" )是将上面所有的元素都写进来
                 html: items.join( "" )
                 }).appendTo(commentBody);*/

                /*$( "<ul/>", {
                 "class": "my-new-list",
                 html: items.join( "" )
                 }).appendTo( "body" );*/
                // debugger;
                //展开二级评论
                comments.addClass("in");
                //标记二级评论展开状态
                e.setAttribute("data-collapse", "in")
                e.classList.add("active");

            });
        }


    }
}
