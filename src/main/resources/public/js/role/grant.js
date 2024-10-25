$(function () {
    //加载树形结构
    loadModuleInfo();
});
//定义树形结构对象
var zTreeObj;

/**
 * 加载资源树形数据
 */
function loadModuleInfo() {
   /* console.log($("#roleId").val())*/
    //通过ajax查询资源列表
    $.ajax({
        type:"post",
        url:ctx+"/module/queryAllModules",
        data:{
            roleId:$("#roleId").val()
        },
        dataType:"json",
        success:function (data) {
            // zTree 的参数配置，深⼊使⽤请参考 API ⽂档（setting 配置详解）
            var setting = {
                //使用简单的JSON数据
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                view:{
                    showLine: false
                    // showIcon: false
                },
                //使用复选框
                check: {
                    enable: true,
                        chkboxType: { "Y": "ps", "N": "ps" }
                },
                callback: {
                    //当checkbox/radio 被选中或取消时触发的函数
                    onCheck: zTreeOnCheck
                }
            };
            var zNodes =data;
            zTreeObj=$.fn.zTree.init($("#test1"), setting, zNodes);
        }
    })
}
function zTreeOnCheck(event, treeId, treeNode) {
    var nodes= zTreeObj.getCheckedNodes(true);
    var roleId=$("#roleId").val();
    var mids="mIds=";
    for(var i=0;i<nodes.length;i++){
        if(i<nodes.length-1){
            mids=mids+nodes[i].id+"&mIds=";
        }else{
            mids=mids+nodes[i].id;
        }
    }
    $.ajax({
        type:"post",
        url:ctx+"/role/addGrant",
        data:mids+"&roleId="+roleId,
        dataType:"json",
        success:function (data) {
            console.log(data);
        }
    })
}