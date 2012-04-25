<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2">
    <title><g:message code="project.action.show.title"/></title>
</head>
<body>
<div class="actionMenu">
    <ul>
        <li>
            <g:link controller="project" class="delete" action="delete" id="${project.id}">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'delete.png')}" alt="${message(code: 'project.action.delete')}"/><g:message code="project.action.delete"/>
            </g:link>
        </li>

        <li>
            <g:link controller="project" action="edit" id="${project.id}">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'edit.png')}" alt="${message(code: 'project.action.edit')}"/><g:message code="project.action.edit"/>
            </g:link>
        </li>

        <li>
            <g:link controller="project" action="create">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'add.png')}" alt="${message(code: 'project.action.add')}"/><g:message code="project.action.add"/>
            </g:link>
        </li>
    </ul>

</div>

<div class="content">
    <h1>
        <span class="createDate">${message(code: 'project.date.of.creation', args: [formatPlainDate(value: project.createDate)])}</span>
        ${project.name},&nbsp;<span class="projectStatus"><g:message
            code="${'project.status.' + project.status}"/></span>
    </h1>

    <table class="entityShow">
    <colgroup>
        <col width="150">
        <col width="150">
    </colgroup>
    <tr>
        <td><g:message code="project.createDate"/></td>
        <td><g:formatPlainDate value="${project.createDate}"/></td>
    </tr>
    <tr>
        <td><g:message code="project.customer"/></td>
        <td>${project.customer}</td>
    </tr>
    <tr>
        <td><g:message code="project.department"/></td>
        <td>${project.department}</td>
    </tr>
    <tr>
        <td><g:message code="project.city"/></td>
        <td>${project.city}</td>
    </tr>
    <tr>
        <td><g:message code="project.contactPerson"/></td>
        <td>${project.contactPerson}</td>
    </tr>

    <tr>
        <td><g:message code="project.contactPhone"/></td>
        <td>${project.contactPhone}</td>
    </tr>

    <tr>
        <td><g:message code="project.releaseDate"/></td>
        <td>${project.releaseDate}</td>
    </tr>

</table>

</div>
</body>
</html>
