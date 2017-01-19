/**
 * Created by rain on 2016/9/16.
 */
function getReqToken(){
    $.ajax({
        url:"getReqToken.do",
        type:'post',
        data:{

        },
        success:function(data){
            $('oauthToken').innerHTML=data.oauthToken;
            $('oauthTokenSecret').innerHTML=data.oauthTokenSecret;
        },
        error:function(data){
            $('oauthToken').innerHTML=data.respMsg;
        }
    });
}