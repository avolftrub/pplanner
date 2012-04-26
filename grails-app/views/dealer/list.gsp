<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2"/>
    <title><g:message code="dealer.action.list.title"/></title>
    <script src="${resource(dir: "js", file: "dealers.js")}" type="text/javascript"></script>
</head>
<body>
<div class="actionMenu">
    <ul>
        <li>
            <g:link controller="dealer" action="create">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'add.png')}" alt="${message(code: 'dealer.action.add')}"/><g:message code="dealer.action.add"/>
            </g:link>
        </li>
    </ul>

</div>

<div id="list-dealers" class="content scaffold-list">
    <h1><g:message code="dealer.action.list.title"/></h1>
    <table>
        <thead>
        <tr>
            <g:sortableColumn action="list" params="${params}" property="name" title="${g.message(code:'dealer.name.short')}" class="${list?'':'disabled'}"/>
            <g:sortableColumn action="list" params="${params}" property="code" title="${g.message(code:'dealer.code.short')}" class="${list?'':'disabled'}"/>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${dealers}" status="i" var="nextDealer">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td>
                    <g:link controller="dealer" action="show" id="${nextDealer.id}">
                        ${nextDealer.name}
                    </g:link>
                </td>
                <td>${nextDealer.code}</td>
                <td>
                    <g:link controller="dealer" action="edit" id="${nextDealer.id}">
                        <img class="actionIcon" src="${resource(dir: 'images', file: 'edit.png')}" alt="${message(code: 'dealer.action.edit')}"/>
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
