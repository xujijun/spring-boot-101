<#list mapMsg>
list in sub-template included:
    <ul>
        <#items as msg>
            <li>subTpl: #{msg?index}: ${msg.title}, ${msg.content}</li>
        </#items>
    </ul>
</#list>