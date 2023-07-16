
		$(document).ready(function(){
			$("#logoutlink").on("click",function(e){
				e.preventDefault();
				document.logoutForm.submit();
			});
			customizeDropDownMenu();
		});
		
		$(document).ready(function(){
			$(".link-delete").on("click",function(e){
				e.preventDefault();
				link = $(this);
				//alert($(this).attr("href"));
				userId = link.attr("userId");
				$("#yesButton").attr("href",link.attr("href"));
				$("#confirmText").text("Are you sure want to delete this user ID " + userId);
				$("#confirmModal").modal();
			});
		});
		function clearFilter(){
			window.location = "[[@{/users}]]";
		}
		
function customizeDropDownMenu(){
	$(".navbar .dropdown").hover(
		function(){
				$(this).find('.dropdown-menu').first().stop(true,true).delay(250).slideDown();
			},
		function(){
				$(this).find('.dropdown-menu').first().stop(true,true).delay(100).slideUp();

			}
	);
	$(".dropdown > a").click(function(){
		location.href = this.href;
	});
}
	
	