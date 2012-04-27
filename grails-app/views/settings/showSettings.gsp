<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2">
    <title><g:message code="user.action.show.title"/></title>
    <script src="${resource(dir: "js", file: "users.js")}" type="text/javascript"></script>
</head>

<body>
<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>
<div class="actionMenu">
    <ul>
        <li>
            <g:link controller="settings" action="editSettings" id="${user.id}">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'edit.png')}"
                     alt="${message(code: 'user.action.edit')}"/><g:message code="user.action.edit"/>
            </g:link>
        </li>
    </ul>

</div>

<g:render template="/user/showPage"/>

</body>
</html>
