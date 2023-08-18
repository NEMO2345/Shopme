$(document).ready(function(){
	
	 var csrfToken = $("meta[name='_csrf']").attr("content"); // Lấy giá trị của thuộc tính content của thẻ meta có name='_csrf'
  var csrfHeader = $("meta[name='_csrf_header']").attr("content"); // Lấy giá trị của thuộc tính content của thẻ meta có name='_csrf_header'

  $.ajaxSetup({
    beforeSend: function(xhr) {
      xhr.setRequestHeader(csrfHeader, csrfToken); // Thiết lập header với tên và giá trị của token CSRF
    }
  });
	
	$("#products").on("click","#linkAddProduct",function(e){
		e.preventDefault();
		link = $(this);
		url = link.attr("href");
		
		$("#addProductModal").on("shown.bs.modal",function(){
			$(this).find("iframe").attr("src",url);
		});
		
		$("#addProductModal").modal();
	});
});