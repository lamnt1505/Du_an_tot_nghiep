$(document).ready(function() {
			var quantitiy = 0;
			
			$(".quantity-right-plus").click(function(e) {
				// Stop acting like a button
				e.preventDefault();
				// Get the field name
				var quantity = parseInt($("#quantity").val());
				// If is not undefined
				$("#quantity").val(quantity + 1);
				// Increment
			});
			
			$('.quantityinput').change(function() {
				var quantity = $(this).val();
				var price = $(this).parents('tr').find('.price').text();
				$(this).parents('tr').find('.total').text(quantity * price);
			});
							
			$(".updateclick").click(function(e) {
				e.preventDefault();
				var quantity = $(this).parents('tr').find('.quantityinput').val();
				var idproduct = $(this).data('productid');
				var product = {
					idproduct : idproduct,
					quantity : quantity
			};
				
			//console.log(product)
			$.post("/updatequantities",product).done(function(data,status) {
				if (data == "0") {
					Swal.fire({
						  icon: 'success',
						  title: 'Có lỗi hoặc số lượng đang nhỏ hơn 0! Vui lòng thử lại...'
						})
				} else if (data == "1") {
					Swal.fire({
						  icon: 'success',
						  title: 'Đã cập nhật thành công !'
						})
				} else if (data == "2") {
					Swal.fire({
						  icon: 'error',
						  title: 'Sản phẩm đã loại khỏi giỏ hàng !'
						})
						setTimeout(function(){
							window.location.reload();
						}, 1500);
					} else {
						alert("Có gì đó sai sai...^^ !");
					}
				});
			});
			
			$("#submitorders").click(function(e) {
				console.log('---start---');
			$.post("/orders").done(function(data,status) {
				if (data == "0") {
					Swal.fire({
						  icon: 'error',
						  title: 'Bạn phải đăng nhập!'
						})
						setTimeout(function(){
							window.location = "/login";
						}, 1000);
				} else if (data == "-1") {
					Swal.fire({
						  icon: 'error',
						  title: 'Chưa chọn sản phẩm vào giỏ hàng!'
						})
				} else if (data == "1") {
					Swal.fire({
						  icon: 'success',
						  title: 'Đặt hàng thành công!'
						})
						setTimeout(function(){
							window.location.reload();
						}, 3000);
					} else {
						alert("Có gì đó sai sai...^^ !");
					}
				});
			});

			console.log('---end---');
			
			$(".product-remove").click(function(e) {
				e.preventDefault();
				var quantity = 0;
				var idproduct = $(this).data('productid');
				var product = {
						idproduct : idproduct,
						quantity : quantity
				};
				
			//console.log(product)
			$.post("/updatequantities",product).done(function(data,status) {
				if (data == "0") {
					Swal.fire({
						  icon: 'success',
						  title: 'Có lỗi hoặc số lượng đang nhỏ hơn 0! Vui lòng thử lại...'
						})
				} else if (data == "1") {
					Swal.fire({
						  icon: 'success',
						  title: 'Đã cập nhật thành công !'
						})
				} else if (data == "2") {
					Swal.fire({
						  icon: 'error',
						  title: 'Sản phẩm đã loại khỏi giỏ hàng !'
						})
						setTimeout(function(){
							window.location.reload();
						}, 1500);
					} else {
						alert("Có gì đó sai sai...^^ !");
					}
				});
			});
			
			$(".quantity-left-minus").click(function(e) {
				// Stop acting like a button
				e.preventDefault();
				// Get the field name
				var quantity = parseInt($("#quantity").val());
				// Increment
				if (quantity > 0) {
					$("#quantity").val(quantity - 1);
					}
			});
		});