<%@ page import="ru.appbio.ProjectStatus" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2"/>
    <title><g:message code="project.action.list.archive.title"/></title>
    <script src="${resource(dir: "js", file: "projects.js")}" type="text/javascript"></script>
</head>
<body>
<div class="actionMenu">
    <ul>
        <shiro:hasRole name="${ShiroRole.ROLE_ADMIN}">
            <g:form method="POST" action="unarchive" name="archiveForm">
                <li>
                    <a id="archivePrjsBtn" href="#" style="display: none;">
                        <img class="actionIcon" src="${resource(dir: 'images', file: 'unarchive.png')}" alt="${message(code: 'project.action.unarchive')}"/><g:message code="project.action.unarchive"/>
                    </a>
                    <span id="archivePrjsDisableBtn">
                        <img class="actionIcon" src="${resource(dir: 'images', file: 'unarchive_dis.png')}" alt="${message(code: 'project.action.unarchive')}"/><g:message code="project.action.unarchive"/>
                    </span>
                </li>
            </g:form>
        </shiro:hasRole>

        <li>
            <g:link controller="project" action="exportToExcel" params="${[archived: true, q: quickSearchStr, sort: filter.sort, order: filter.order]}">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'excel.png')}" alt="${message(code: 'project.action.excel')}"/><g:message code="project.action.excel"/>
            </g:link>
        </li>

        <li class="quickSearch">
            <g:form controller="project" action="lookup" method="GET">
                <g:hiddenField name="archived" value="true"/>
                <g:textField name="q" value="${quickSearchStr}" placeholder="${message (code: 'projects.quicksearch')}"/>
            </g:form>
        </li>
    </ul>
</div>

<g:render template="list_inner" model="[projects: projects, currentUser: currentUser, filte: filter, total: total, quickSearchStr: quickSearchStr, archived: true]"/>

<g:render template="list_js"/>

</body>
</html>
