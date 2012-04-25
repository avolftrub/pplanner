<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2"/>
    <title><g:message code="project.action.list.title"/></title>
    <script src="${resource(dir: "js", file: "projects.js")}" type="text/javascript"></script>
</head>
<body>
<div class="actionMenu">
    <ul>
        <li>
            <g:link controller="project" action="exportToExcel">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'excel.png')}" alt="${message(code: 'project.action.excel')}"/><g:message code="project.action.excel"/>
            </g:link>
        </li>

        <li>
            <g:link controller="project" action="create">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'add.png')}" alt="${message(code: 'project.action.add')}"/><g:message code="project.action.add"/>
            </g:link>
        </li>
    </ul>

</div>

<div id="list-projects" class="content scaffold-list" role="main">
    <h1><g:message code="project.action.list.title"/></h1>
    <table>
        <thead>
        <tr>
            <g:sortableColumn action="list" params="${params}" property="name" title="${g.message(code:'project.name.short')}" class="${list?'':'disabled'}"/>
            <g:sortableColumn action="list" params="${params}" property="dealer" title="${g.message(code:'project.dealer.short')}" class="${list?'':'disabled'}"/>
            <g:sortableColumn action="list" params="${params}" property="city" title="${g.message(code:'project.city.short')}" class="${list?'':'disabled'}"/>
            <g:sortableColumn action="list" params="${params}" property="sum" title="${g.message(code:'project.sum.short')}" class="${list?'':'disabled'}"/>
            <g:sortableColumn action="list" params="${params}" property="status" title="${g.message(code:'project.status')}" class="${list?'':'disabled'}"/>
            <g:sortableColumn action="list" params="${params}" property="releaseDate" title="${g.message(code:'project.releaseDate.short')}" class="${list?'':'disabled'}"/>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${projects}" status="i" var="nextProject">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td>${nextProject.name}</td>
                <td>${nextProject.dealer?.name}</td>
                <td>${nextProject.city}</td>
                <td>${nextProject.sum}</td>
                <td>${nextProject.status}</td>
                <td>${nextProject.releaseDate}</td>
                <td>
                    <g:link controller="project" action="edit" id="${nextProject.id}">
                        <img class="actionIcon" src="${resource(dir: 'images', file: 'edit.png')}" alt="${message(code: 'project.action.edit')}"/>
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
