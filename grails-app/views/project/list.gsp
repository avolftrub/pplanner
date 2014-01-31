<%@ page import="ru.appbio.ProjectStatus" %>
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
        <shiro:hasRole name="${ShiroRole.ROLE_ADMIN}">
            <g:form method="POST" action="archive" name="archiveForm">
                <li>
                    <a id="archivePrjsBtn" href="#" style="display: none;">
                        <img class="actionIcon" src="${resource(dir: 'images', file: 'add2archive.png')}" alt="${message(code: 'project.action.archive')}"/><g:message code="project.action.archive"/>
                    </a>
                    <span id="archivePrjsDisableBtn">
                        <img class="actionIcon" src="${resource(dir: 'images', file: 'add2archive_dis.png')}" alt="${message(code: 'project.action.archive')}"/><g:message code="project.action.archive"/>
                    </span>
                </li>
            </g:form>
        </shiro:hasRole>
        <li>
            <g:link controller="project" action="exportToExcel" params="${[q: quickSearchStr, sort: filter.sort, order: filter.order]}">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'excel.png')}" alt="${message(code: 'project.action.excel')}"/><g:message code="project.action.excel"/>
            </g:link>
        </li>

        <shiro:hasRole name="${ShiroRole.ROLE_DEALER}">
            <li>
                <g:link controller="project" action="create">
                    <img class="actionIcon" src="${resource(dir: 'images', file: 'add.png')}" alt="${message(code: 'project.action.add')}"/><g:message code="project.action.add"/>
                </g:link>
            </li>
        </shiro:hasRole>

        <li class="quickSearch">
            <g:form controller="project" action="lookup" method="GET">
                <g:textField name="q" value="${quickSearchStr}" placeholder="${message (code: 'projects.quicksearch')}"/>
                %{--<span id="advancedSearch" class="advancedSearchOpen"><a href="#"><g:message code="project.advanced.search"/></a></span>--}%
                %{--<span id="advancedSearchClose" style="display: none;" class="advancedSearchClose"><a href="#"><g:message code="project.advanced.search.close"/></a></span>--}%
            </g:form>
        </li>
    </ul>
</div>

%{--<div id="advancedSearchBlock" style="display: none;">--}%
    %{--<label for="status">--}%
        %{--<g:message code="poject.statusSelect"/>--}%
        %{--<g:select name="status" class="w45" optionKey="id" from="${ProjectStatus.values()}"/>--}%
    %{--</label>--}%

%{--</div>--}%

<g:render template="list_inner" model="[projects: projects, currentUser: currentUser, filte: filter, total: total, quickSearchStr: quickSearchStr, archived: false]"/>

<g:render template="list_js"/>

</body>
</html>
