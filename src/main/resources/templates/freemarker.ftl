<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>welcome to freemarker</title>
</head>
<body>
<h4>亲爱的${toUserName}，你好！</h4>

<#list messages as m>
	<p> ${m}，请不要告诉其他人，切记！</p>
</#list>

<#list mapMsg>
    <ul>
		<#items as msg>
		    <li>#{msg?index}: ${msg.title}, ${msg.content}</li>
		</#items>
	</ul>
</#list>

祝：开心！
</br>
${fromUserName}
</br>
${time?date}

</body>
</html>