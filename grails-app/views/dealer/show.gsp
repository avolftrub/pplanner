<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2">
    <title><g:message code="dealer.action.show.title"/></title>
    <script src="${resource(dir: "js", file: "dealers.js")}" type="text/javascript"></script>
</head>
<body>
<div class="actionMenu">
    <ul>
        <li>
            <g:link controller="dealer" class="delete deleteDealerLink" action="delete" id="${dealer.id}" helpertext="${message(code: 'dealer.delete.confirm')}">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'delete.png')}" alt="${message(code: 'dealer.action.delete')}"/><g:message code="dealer.action.delete"/>
            </g:link>
        </li>

        <li>
            <g:link controller="dealer" action="edit" id="${dealer.id}">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'edit.png')}" alt="${message(code: 'dealer.action.edit')}"/><g:message code="dealer.action.edit"/>
            </g:link>
        </li>

        <li>
            <g:link controller="dealer" action="create">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'add.png')}" alt="${message(code: 'dealer.action.add')}"/><g:message code="dealer.action.add"/>
            </g:link>
        </li>
    </ul>

</div>

<div class="content show">

    <table class="entityShow">
    <colgroup>
        <col width="35%">
        <col width="65%">
    </colgroup>
    <tr>
        <td><g:message code="dealer.name.short"/></td>
        <td>${dealer.name}</td>
    </tr>
    <tr>
        <td><g:message code="dealer.code.short"/></td>
        <td>${dealer.code}</td>
    </tr>

</table>

</div>
</body>
</html>
