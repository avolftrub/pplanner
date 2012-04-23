<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2"/>
    <title><g:message code="project.action.edit.title"/></title>
    <script src="${resource(dir: "js", file: "projects.js")}" type="text/javascript"></script>
    %{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">--}%
</head>

<body>
%{--<g:deleteDialog id="dialog-confirm" titleKey="project.action.delete.title">--}%
    %{--<p><g:message code="project.action.delete.title"/></p>--}%
    %{--<g:form url="${createLink(action:'delete', id: project.id)}">--}%
        %{--<div class="Actions">--}%
            %{--<div>--}%
                %{--<button class="Destructive" type="submit"><g:message code="yes.delete" /></button>--}%
                %{--<a href="#" onclick="$('#dialog-confirm').dialog('close');return false;"><g:message code="no.cancel" /></a>--}%
            %{--</div>--}%
        %{--</div>--}%
    %{--</g:form>--}%
%{--</g:deleteDialog>--}%

<div class="actionMenu">
    <ul>
        <li>
            <a class="NegativeAction" href="#" onclick="$('#dialog-confirm').dialog('open');
            return false;">
                <img class="actionIcon" src="${resource(dir: 'images', file: 'delete.png')}" alt="${message(code: 'project.action.delete')}"/><g:message code="project.action.delete"/>
            </a>

            <g:link controller="project" class="delete" action="delete" id="${project.id}">

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

<g:form method="post" controller="project" action="update" name="projectForm">
    <g:hiddenField name="id" value="${project.id}"/>
    <g:render template="/project/form"/>
    <hr/>

    <div>
        <button type="submit" class="Save"><span><g:message code="save"/></span></button>
        <g:link action="list" class="Cancel SaveCancel"><span><g:message code="cancel"/></span></g:link>
    </div>
</g:form>

</body>
</html>