$(document).ready(function() {

    var orderid =0;

    $(".editmodal").click(function(e) {
        e.preventDefault();

        var status = $(this).parents('tr').find('.status').text();
        orderid = $(this).data('orderid');

        console.log(status);

        $(".optionorder").val(status).change();
    });

    $(".submitstatus").click(function(e) {
        e.preventDefault();

        var newstatus = $(".optionorder").val();
        console.log(newstatus);
        console.log(orderid);

        $.post("/updatestatus",{
            "orderid":orderid,
            "status":newstatus}
            )
                .done(function(data,status) {
                    var quantity = parseInt(data);
                    console.log(quantity);
                    if (quantity==0) {
                        Swal.fire({
                              icon: 'error',
                              title: 'Có lỗi ! Vui lòng thử lại...'
                            })
                    }
                    else if (quantity==-1) {
                        Swal.fire({
                              icon: 'error',
                              title: 'Số lượng trong kho không đủ !'
                            })
                    }
                    else if (quantity==-2) {
                        Swal.fire({
                              icon: 'error',
                              title: 'Sản phẩm chưa được cập nhật trong kho !'
                            })
                    }
                    else if (quantity==1) {
                        Swal.fire({
                              icon: 'success',
                              title: 'Thêm thành công !'
                            })
                    }
                    else if (quantity<0) {
                        alert("Có gì đó sai sai...^^ !");
                    }
                    setTimeout(function(){
                        window.location.reload();
                    }, 2000);
                });

    });
});