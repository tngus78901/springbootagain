<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/layout/header.jsp"%>

<div class="col-sm-8">
	<h2>회원 가입</h2>
	<h5>어서오세요 환영합니다</h5>
	<!-- form으로 data 넘길 때 multipart 반드시 선언 -->
	<form action="/user/sign-up" method="post"
		enctype="multipart/form-data">
		<div class="form-group">
			<label for="username">username:</label> <input type="text"
				name="username" class="form-control" placeholder="username"
				id="username">
		</div>
		<div class="form-group">
			<label for="pwd">password:</label> <input type="password"
				name="password" class="form-control" placeholder="Enter password"
				id="pwd">
		</div>
		<div class="form-group">
			<label for="fullname">fullname:</label> <input type="text"
				name="fullname" class="form-control" placeholder="fullname"
				id="fullname">
		</div>

		<div class="custom-file">
			<input type="file" class="custom-file-input" id="customFile"
				name="customFile"> <label class="custom-file-label"
				for="customFile">Choose file</label>
		</div>

		<br>
		<!-- 과제: 이벤트 전파 속성 - 버블링 뭔가? 캡쳐링 이란? -->
		<button type="submit" class="btn btn-primary">회원가입</button>
		</br>
	</form>
</div>
</br>
</div>


<script>
<!--  Add the following code if you want the name of the file appear on select-->
$(".custom-file-input").on("change", function() { 
	var fileName = $(this).val().split("\\").pop();
	$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});
</script>
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>
