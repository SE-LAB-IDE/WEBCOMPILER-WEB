# 📋안전한 컴파일 시스템을 이용한 웹 IDE - web part📋

> WEB IDE USING SECURE COMPILATION SYSTEM

<br>

### ©CopyRight

> SE WEBCOMPILER PROJECT TEAM

> Department of Computer Engineering, Hanshin University

<br>

### 📒Contents
> 특정 환경에서 작동되는 프로그램 없이도 간단하게 웹에서 여러 언어를 사용할 수 있다.부가적으로 작성한 코드를 저장하거나 또는 알고리즘 풀이기능과 편의를 위한 유저 정보 수정, 찾기 기능, 유저끼리의 커뮤니티를 위한 게시판과 문의 기능이 제공된다. OPENCV를 활용한 OCR 분석과 사용자 활동에 따른 데이터 분석을 추가해 4가지 언어로 사용자가 시스템을 사용할 수 있게 제작했다. 

<br>

### 🔧Environment
  - AWS : Naver Cloud Platform basic EC2
  - Server : Ubuntu 18.04 LTS, Apache Tomcat 9.0
  - Framework : MVC2 JSP
  - Database : MySQL
  - JDK-14.0.2
  - Python : 2.7.17
  - gcc (Ubuntu 7.5.0-3ubuntu1~18.04) 7.5.0
  - nodeJS v8.10.0

<br>

### 📑EDITOR
 Microsoft에서 공개한 [Monaco Editor open Sorce](https://microsoft.github.io/monaco-editor/)를 사용하였다.  
 C/C++, Java, Javascript, Python 네가지의 언어를 지원하기 위해 동적으로 선택한 언어의 editor 객체를 생성하도록 하였다.  
 ```javascript
    function createEdtior(ocr, ocrCode){
         var language=getCodeType(); // codeType 가져오기 codeType에 따라 editor의 언어 설정이 바뀜
         /* 중략 */
		var remove = document.getElementById('editorDiv');
		if(remove!=null)// 만약 editor div가 있다면 삭제
			document.body.removeChild(remove);
		
   	    var newDiv = document.createElement('div');
   	    newDiv.style.height= '90vh';
   	 	newDiv.id= "editorDiv";
   	    document.body.appendChild(newDiv);// editor div를 동적으로 생성
   	    // editor
        require.config({ paths: { 'vs': 'https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.16.2/min/vs' }});
        require(['vs/editor/editor.main'], function() {
          editor = monaco.editor.create(document.getElementById('editorDiv'), {
            theme: 'vs-dark',
            fontFamily: 'Nanum Gothic Coding',
            automaticLayout: true,
            language: language,
            value: [
              code
            ].join('\n')
          });
        });
    }
```
<br>

### 🔨IDE
* WorkSpace
<br> 
 한 페이지 안에 iframe을 이용하여 Workspace, Editor, Consol, OCR 네가지의 부분을 구현하였다.  
 workSpace는 Session에 저장된 아이디를 이용하여 Database에서 사용자가 저장한 코드들을 불러온다.  
 코드 저장, 삭제시 PopUp창에서 저장,삭제를 실행하고 WorkspaceList를 초기화 하였다.  
 
 ```javascript
 window.onload = function () {// 페이지 로드 시 workSpace를 새로고침하고 페이지를 닫음
	 opener.reloadWorkSpaceList('<%= result %>');
	 window.close();
	}
```
<br>

* OCR  

  OCR의경우 Server로 FileUpload -> ocrCompiler에서 해독 -> ocr페이지 출력의 순서로 진행된다.  
  ocr.jsp -> FileUpload.jsp -> corCompiler.java -> ocr.jsp의 순서로 동작되도록 설계하였으나 세개이상의 파일을 거쳐가는 경우 iframe을 못찾는 경우가 발생하여서 하나의 jsp파일에서 동작하도록 만들었다.  
  ocr.jsp파일은 ocrCompiler.java 혹은 ocr.java 파일만 호출하도록 작성되었다.
  ```javascript
  if(request.getAttribute("ocrCode")!=null){ // ocrCompiler를 이용하여 코드 추출후 출력 부분
    /*내용 생략*/}
  else if (isMultipart) { //파일업로드 실행부분
  /* 내용 생략*/}
<br>

### 💡Algorithm
 AlgorithmList와 Editor두부분으로 구성되어 있다.
 * AlgorithmList  
 Level별로 문제를 골라서 풀수 있으며 본인이 시도한 문제는 성공 실패 여부를 확인 가능하다.  
 List에서 문제를 클릭할 경우 Editor가 해당 문제의 번호를 가진상태로 새로고침된다.  
 이후 Editor는 소스코드와 문제번호를 서버로 전송 시켜 성공 실패 여부를 AlgorithmList로 반환시킨다. 
 ```javascript
    function toIDE(num,retry){
       var form = document.createElement("form");//폼 생성
          
       form.setAttribute("charset", "UTF-8");//인코딩 타입
       form.setAttribute("method", "Post");  //전송 방식
       form.setAttribute("target", "editor"); //타겟
       form.setAttribute("action", "editorForAlgorithm.jsp"); //요청 보낼 주소
       if(retry==1)
          form.setAttribute("action", "./algorithm/editorForAlgorithm.jsp"); //요청 보낼 주소
       
       var hiddenField = document.createElement("input"); // input 버튼 생성
       hiddenField.setAttribute("type", "hidden");
       hiddenField.setAttribute("name", "algorithmNum");
       hiddenField.setAttribute("value", num);
       form.appendChild(hiddenField);// form에 추가
              
       
       document.body.appendChild(form);//form을 body에 생성
       form.submit(); //submit
      
   }
```

### 🔐Login
* RSA Encryption
  암호화 방식에는 [RSA함호화 방식](https://namu.wiki/w/RSA%20%EC%95%94%ED%98%B8%ED%99%94)을 사용하였다.  
![RSA](https://user-images.githubusercontent.com/67648064/103686081-9d416580-4fd1-11eb-9006-cba38255b9f3.png) 
  1. login page로 이동 전 loginFormServlet으로 이동하여 개인키와 공개키를 생성하고 개인키는 세션에 저장, 공개키는 RSA library로 넘겨준 후 login page로 이동한다.  
  2. 로그인을 시도하면 RSA library는 id와 pw를 가로챈 후 공개키로 암호화 하여 화면에 나타나지 않는 form 태그를 이용하여 암호화된 id와 pw를 전송한다.
  3. 서버에서는 암호화된 id와 pw를 세션값에 저장된 개인키를 이용하여 복호화 한다. 이후 로그인을 진행하여 결과를 되돌려 준다.
  
<br>

### 👪Contributers

- [16학번 김동건](https://github.com/DongGeon0908)
- 16학번 황인준
- 18학번 이상호
- [19학번 안병욱](https://github.com/uuuugi)

<br>

### 🔗Link
- [WEBCOMPILER-WEB](https://github.com/SE-LAB-IDE/WEBCOMPILER-WEB)
