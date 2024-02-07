<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!-- 기존 -->
<!DOCTYPE html>
<html lang="en">
<head>
  <title>MY BANK</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  <!-- 외부 스타일 시트 가져오기 -->
  <link rel="stylesheet" href="/css/style.css" >
  <link rel="icon" type="images/x-icon" href="/images/favicon.ico" />
  <link href="/css/bank.css" rel="stylesheet" />
</head>
<body>

<div class="jumbotron text-center banner--img" style="margin-bottom:0">
  <h1>my bank</h1>
  <p>최첨단 은행 관리 시스템</p> 
</div>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container px-5">
                <a class="navbar-brand" href="/layout/banksite">BANK</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a class="nav-link active" href="/account/saveForm">계좌생성</a></li>
                        <li class="nav-item"><a class="nav-link" href="/account/list">계좌목록</a></li>
                        <li class="nav-item"><a class="nav-link" href="/account/withdraw">출금</a></li>
                        <li class="nav-item"><a class="nav-link" href="/account/deposit">입금</a></li>
                        <li class="nav-item"><a class="nav-link" href="/account/transfer">이체</a></li>
                        <li class="nav-item"><a class="nav-link" href="/user/signIn">로그인</a></li>
                        <li class="nav-item"><a class="nav-link" href="/user/signUp">회원가입</a></li>
                    </ul>
                </div>
            </div>
        </nav>

<div class="container" style="margin-top:30px">
  <div class="row">
    <div class="col-sm-4">
      <h2>About Me</h2>
      <h5>Photo of me:</h5>
      
      	<!-- 로그인 여부에 코드 추가 하기 -->
      <c:choose>
	      <c:when test="${principal != null}">
	      	<img class="m--profile" alt="" src="${principal.setupUserImage()}">
	      </c:when>	
	      <c:otherwise>
	      	<div class="m--profile"></div>
	      </c:otherwise>
      </c:choose>	
      	
      <p>중단기 심화 - 은행 관리 시스템 예제</p>
     
    </div>
    <!--  end of header -->