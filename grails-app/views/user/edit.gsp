<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2"/>
    <title><g:message code="user.action.edit.title"/></title>
    <script src="${resource(dir: "js", file: "users.js")}" type="text/javascript"></script>
</head>

<body>
<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>
<div class="actionMenu">
    <ul>
        <li>
            <g:link controller="user" class="delete deleteUserLink" action="delete" id="${user.id}" helpertext="${message(code: 'user.delete.confirm')}" params="[actionBack: 'edit']">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'delete.png')}" alt="${message(code: 'delete')}"/><g:message code="user.action.delete"/>
            </g:link>
        </li>
    </ul>

</div>

<g:form method="post" controller="user" action="update" name="userEditForm">
    <g:hiddenField name="id" value="${user.id}"/>
    <g:hiddenField id="changePwdFlag" name="pwdChange" value="${pwdChange}"/>
    <g:render template="/user/form"/>
    <hr/>

    <div class="buttonBlock">
        <button type="submit" class="Save"><span><g:message code="save"/></span></button>
        <g:link action="list" class="Cancel SaveCancel"><span><g:message code="cancel"/></span></g:link>
    </div>
</g:form>

</body>
</html>