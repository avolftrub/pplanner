<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2"/>
    <title><g:message code="project.action.edit.title"/></title>
    <script src="${resource(dir: "js", file: "projects.js")}" type="text/javascript"></script>
</head>

<body>

<div class="actionMenu">
    <ul>
        <li>
            <g:link controller="project" class="delete deleteProjectLink" action="delete" id="${project.id}" helpertext="${message(code: 'project.delete.confirm')}">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'delete.png')}" alt="${message(code: 'project.action.delete')}"/><g:message code="project.action.delete"/>
            </g:link>
        </li>
    </ul>

</div>

<g:form method="post" controller="project" action="update" name="projectForm">
    <g:hiddenField name="id" value="${project.id}"/>
    <g:hiddenField name="dealer" value="${project.dealer?.id}"/>
    <g:render template="/project/form"/>
    <hr/>

    <div class="buttonBlock">
        <button type="submit" class="Save"><span><g:message code="save"/></span></button>
        <g:link action="list" class="Cancel SaveCancel"><span><g:message code="cancel"/></span></g:link>
    </div>
</g:form>

</body>
</html>