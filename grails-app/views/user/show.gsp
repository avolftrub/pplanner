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
        <shiro:hasRole name="${ShiroRole.ROLE_ADMIN}">
            <li>
                <g:link controller="user" class="delete deleteUserLink" action="delete" id="${user.id}" params="[actionBack: 'show']"
                        helpertext="${message(code: 'user.delete.confirm')}">
                    <img class="actionIcon" src="${resource(dir: 'images', file: 'delete.png')}"
                         alt="${message(code: 'user.action.delete')}"/><g:message code="user.action.delete"/>
                </g:link>
            </li>
        </shiro:hasRole>

        <li>
            <g:link controller="user" action="edit" id="${user.id}">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'edit.png')}"
                     alt="${message(code: 'user.action.edit')}"/><g:message code="user.action.edit"/>
            </g:link>
        </li>
    </ul>

</div>

<div class="content show">
    <h1>
        <span class="createDate">${message(code: 'user.date.of.creation', args: [formatPlainDate(value: user.createDate)])}</span>
        ${user.name}
    </h1>

    <table class="entityShow">
        <colgroup>
            <col width="35%">
            <col width="65%">
        </colgroup>
        <tr>
            <td><g:message code="user.username"/></td>
            <td>${user.username}</td>
        </tr>
        <tr>
            <td><g:message code="user.role"/></td>
            <td>${message(code: 'ROLE.' + user.role.name)}</td>
        </tr>
        <tr>
            <td><g:message code="user.dealer"/></td>
            <td><g:link controller="dealer" action="show" id="${user.dealer?.id}">${user.dealer?.name}</g:link></td>
        </tr>
    </table>
</div>
</body>
</html>
