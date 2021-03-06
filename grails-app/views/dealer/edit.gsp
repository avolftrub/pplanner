<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2"/>
    <title><g:message code="dealer.action.edit.title"/></title>
    <script src="${resource(dir: "js", file: "dealers.js")}" type="text/javascript"></script>
</head>

<body>

<div class="actionMenu">
    <ul>
        <li>
            <g:link controller="dealer" class="delete deleteDealerLink" action="delete" id="${dealer.id}" helpertext="${message(code: 'dealer.delete.confirm')}">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'delete.png')}" alt="${message(code: 'delete')}"/><g:message code="dealer.action.delete"/>
            </g:link>
        </li>
    </ul>

</div>

<g:form method="post" controller="dealer" action="update" name="dealerForm">
    <g:hiddenField name="id" value="${dealer.id}"/>
    <g:render template="/dealer/form"/>
    <hr/>

    <div class="buttonBlock">
        <button type="submit" class="Save"><span><g:message code="save"/></span></button>
        <g:link action="list" class="Cancel SaveCancel"><span><g:message code="cancel"/></span></g:link>
    </div>
</g:form>

</body>
</html>