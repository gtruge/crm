layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;


    /**
     * 关闭弹出层
     */
    $("#closeBtn").click(function () {
        //当你在iframe页面关闭时
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    })

    $.ajax({
        type:"get",
        url:ctx+"/user/queryAllSales",
        data:{},
        success:function (data) {
            //判断返回的数据是否为空
            if (data!=null){
                //获取隐藏域设置的指派人Id
                var assignManId=$("#assignManId").val()
                //遍历返回的数据
                for (var i = 0; i < data.length; i++) {
                    var opt="";
                    //如果循环得到的Id与隐藏域一致，则表示被选中
                    if (assignManId==data[i].id){
                        //设置下拉选项，设置默认值
                        opt = "<option value=\""+data[i].id+"\"  selected='selected' >"+data[i].uname+"</option>";
                    }else {
                        //设置下拉选项，不设置默认值
                        opt = "<option value=\""+data[i].id+"\"  >"+data[i].uname+"</option>";
                    }
                    //将下拉选项设置到下拉框
                    $("#assignMan").append(opt)
                }
            }
            //重新渲染下拉框的内容
            layui.form.render("select");
        }
    })



    /**
     * 监听表单submit事件，完成营销机会的添加
     */
    form.on('submit(addOrUpdateSaleChance)',function (data) {
        var index= top.layer.msg("数据提交中,请稍后...",{icon:16,time:false,shade:0.8});

        //通过营销机会的Id来判断当前需要执行添加操作还是修改操作
        //id为空执行添加操作，id不为空执行修改操作
        var url=ctx + "/sale_chance/add";
        var saleChanceId= $("[name='id']").val();
        if (saleChanceId !=null && saleChanceId !=""){
            url=ctx + "/sale_chance/update"
        }

        $.post(url,data.field,function (res) {
            if(res.code==200){
                top.layer.msg("操作成功");
                top.layer.close(index);
                layer.closeAll("iframe");
                // 刷新父页面
                parent.location.reload();
            }else{
                layer.msg(res.msg);
            }
        });
        return false;
    });

});