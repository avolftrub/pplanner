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
        <li>
            <g:link controller="project" action="exportToExcel">
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

<div id="list-projects" class="content scaffold-list" role="main">
    <h1><g:message code="project.action.list.title"/></h1>
    <g:if test="${total > 0}">
        <table>
            <colgroup>
                <col width="25%"/>
                <col width="15%"/>
                <col width="15%"/>
                <col width="15%"/>
                <col width="5%"/>
                <col width="10%"/>
                <col width="10%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
            </colgroup>
            <thead>
            <tr>
                <g:sortableColumn action="list" params="${params}" property="name" title="${g.message(code:'project.name.short')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="list" params="${params}" property="productName" title="${g.message(code:'project.productName.short')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="list" params="${params}" property="dealer" title="${g.message(code:'project.dealer.short')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="list" params="${params}" property="customer" title="${g.message(code:'project.customer.short')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="list" params="${params}" property="city" title="${g.message(code:'project.city.short')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="list" params="${params}" property="sum" title="${g.message(code:'project.sum.short')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="list" params="${params}" property="status" title="${g.message(code:'project.status')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="list" params="${params}" property="approvalStatus" title="${g.message(code:'project.approvalStatus')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="list" params="${params}" property="releaseDate" title="${g.message(code:'project.releaseDate.short')}" class="${list?'':'disabled'}"/>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${projects}" status="i" var="nextProject">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>
                        <g:link controller="project" action="show" id="${nextProject.id}">
                            ${nextProject.name}
                        </g:link>
                    </td>
                    <td>${nextProject.productName}</td>
                    <td>${nextProject.dealer?.name}</td>
                    <td>
                        ${nextProject.customer}<br/>
                        (<g:message code="project.customer.inn.short"/>:&nbsp;${nextProject.inn})
                    </td>
                    <td>${nextProject.city?.name}</td>
                    <td><g:formatMoney value="${nextProject.sum}"/></td>
                    <td><g:message code="${'project.status.' + nextProject.status.id}"/></td>
                    <td class="centered">
                        <img class="actionIcon" width="24" src="${resource(dir: 'images', file: 'lt_status_' + nextProject.approvalStatus.id + '.png')}" alt="${message(code: 'project.status.lt.' + nextProject.approvalStatus.id)}" title="${message(code: 'project.status.lt.' + nextProject.approvalStatus.id)}"/>
                    </td>
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
    </g:if>

    <g:if test="${total == 0}">
        <table class="notFound">
            <tr>
                <td>
                    <div class="notfoundTitle"><g:message code="list.notFound"/></div>
                    <div class="NothingFoundActions">
                        <g:message code="you.can"/><g:link controller="project" action="list"><g:message code="list.clearFilter"/></g:link>
                        <g:message code="or"/><g:link controller="project" action="create"><g:message code="add.project"/></g:link>

                    </div>
                </td>
            </tr>
        </table>
    </g:if>

    <g:if test="${filter.max < total}">
        <div class="pagination">
            <g:paginate total="${total}" />
        </div>
    </g:if>
</div>
</body>
</html>
