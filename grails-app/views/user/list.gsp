<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2"/>
    <title><g:message code="user.action.list.title"/></title>
    <script src="${resource(dir: "js", file: "users.js")}" type="text/javascript"></script>
</head>
<body>
<div class="actionMenu">
    <ul>
        <li>
            <g:link controller="user" action="create" params="[admin: false]">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'user_dealer.png')}" alt="${message(code: 'user.action.add.dealer')}"/><g:message code="user.action.add.dealer"/>
            </g:link>
        </li>
    </ul>

    <ul>
        <li>
            <g:link controller="user" action="create" params="[admin: true]">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'user_admin.png')}" alt="${message(code: 'user.action.add.admin')}"/><g:message code="user.action.add.admin"/>
            </g:link>
        </li>
    </ul>

</div>

<div id="list-users" class="content scaffold-list">
    <h1><g:message code="user.action.list.title"/></h1>
    <table>
        <colgroup>
            <col width="25%"/>
            <col width="20%"/>
            <col width="20%"/>
            <col width="30%"/>
            <col width="5%"/>
        </colgroup>
        <thead>
        <tr>
            <g:sortableColumn action="list" params="${params}" property="lastName" title="${g.message(code:'user.name')}" class="${list?'':'disabled'}"/>
            <g:sortableColumn action="list" params="${params}" property="username" title="${g.message(code:'user.username')}" class="${list?'':'disabled'}"/>
            <g:sortableColumn action="list" params="${params}" property="role" title="${g.message(code:'user.role')}" class="${list?'':'disabled'}"/>
            <g:sortableColumn action="list" params="${params}" property="dealer" title="${g.message(code:'user.dealer')}" class="${list?'':'disabled'}"/>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${users}" status="i" var="nextUser">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td>
                    <g:link controller="user" action="show" id="${nextUser.id}">
                        ${nextUser.getName()}
                    </g:link>
                </td>
                <td>${nextUser.username}</td>
                <td>${message(code: 'ROLE.' + nextUser.role.name)}</td>
                <td><g:link controller="dealer" action="show" id="${nextUser.dealer?.id}">${nextUser.dealer?.name}</g:link></td>

                <td>
                    <g:link controller="user" action="edit" id="${nextUser.id}">
                        <img class="actionIcon" src="${resource(dir: 'images', file: 'edit.png')}" alt="${message(code: 'user.action.edit')}"/>
                    </g:link>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <g:if test="${filter.max < total}">
        <div class="pagination">
            <g:paginate total="${total}" />
        </div>
    </g:if>
</div>
</body>
</html>
