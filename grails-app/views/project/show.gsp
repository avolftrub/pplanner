<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2">
    <title><g:message code="project.action.show.title"/></title>
    <script src="${resource(dir: "js", file: "projects.js")}" type="text/javascript"></script>
</head>

<body>
<div class="actionMenu">
    <ul>
        <span class="listBack"><g:link controller="project" action="backToList" params="[archived: project.archived]"><g:message code="project.action.show.back"/></g:link></span>
        <shiro:hasRole name="${ShiroRole.ROLE_DEALER}">
            <g:if test="${project.dealer == currentUser.dealer}">
                <li>
                    <g:link controller="project" class="delete deleteProjectLink" action="delete" id="${project.id}" helpertext="${message(code: 'project.delete.confirm')}" params="${params}">
                        <img class="actionIcon" src="${resource(dir: 'images', file: 'delete.png')}"
                             alt="${message(code: 'project.action.delete')}"/><g:message code="project.action.delete"/>
                    </g:link>
                </li>

                <li>
                    <g:link controller="project" action="edit" id="${project.id}">
                        <img class="actionIcon" src="${resource(dir: 'images', file: 'edit.png')}"
                             alt="${message(code: 'project.action.edit')}"/><g:message code="project.action.edit"/>
                    </g:link>
                </li>

            </g:if>
        </shiro:hasRole>

        %{--<shiro:hasRole name="${ShiroRole.ROLE_ADMIN}">--}%
            %{--<g:if test="${project.approvalStatus == ru.appbio.LTProjectStatus.NEW}">--}%
                %{--<li>--}%
                    %{--<g:link controller="project" action="reject" id="${project.id}">--}%
                        %{--<img class="actionIcon" src="${resource(dir: 'images', file: 'reject.png')}"--}%
                             %{--alt="${message(code: 'project.reject')}"/><g:message code="project.reject"/>--}%
                    %{--</g:link>--}%
                %{--</li>--}%

                %{--<li>--}%
                    %{--<g:link controller="project" action="approve" id="${project.id}">--}%
                        %{--<img class="actionIcon" src="${resource(dir: 'images', file: 'approve.png')}"--}%
                             %{--alt="${message(code: 'project.approve')}"/><g:message code="project.approve"/>--}%
                    %{--</g:link>--}%
                %{--</li>--}%
            %{--</g:if>--}%
        %{--</shiro:hasRole>--}%

    </ul>

</div>
<div class="content show">
    <h1>
        <span class="createDate">${message(code: 'project.date.of.creation', args: [formatDate(format: 'yyyy-MM-dd HH:mm', date: project.dateCreated)])}</span><br/>
        <span class="createDate">${message(code: 'project.date.of.modification', args: [formatDate(format: 'yyyy-MM-dd HH:mm', date: project.lastUpdated)])}</span>
        ${project.name}
        %{--,&nbsp;<span class="projectStatus st${project.approvalStatus.id}"><g:message--}%
            %{--code="${'project.status.lt.' + project.approvalStatus.id}"/></span>--}%
    </h1>

    <g:if test="${project.dealer == currentUser.dealer || currentUser.isAdmin()}">
        <g:render template="projectFullInfo" model="[project: project]"/>
    </g:if>
    <g:else>
        <g:render template="projectShortInfo" model="[project: project]"/>
    </g:else>

</div>
</body>
</html>
