<%@ page import="ru.appbio.ProjectStatus" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2">
    <title><g:message code="project.action.show.title"/></title>
    <script src="${resource(dir: "js", file: "projects.js")}" type="text/javascript"></script>
</head>
<body>
<div>
</div>
<div class="content">
    <g:if test="${isNew}">
        <h1 class="newEntity"><g:message code="dealer.action.create.title"/></h1>
    </g:if>
    <g:else>
        <h1>
            <span class="createDate">${message(code: 'project.date.of.creation', args: [formatPlainDate(value: project.createDate)])}</span>
            <g:message code="project.action.edit.title"/>
        </h1>
    </g:else>

    <table class="entityEdit">
        <colgroup>
            <col width="30%">
            <col width="70%">
        </colgroup>

        <tr>
            <td><g:message code="dealer.name"/></td>
            <td>
                <g:textField name="name" value="${dealer.name}" class="${hasErrors(bean: dealer, field: 'name', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${dealer}" field="name"/>
            </td>
        </tr>
        <tr>
            <td><g:message code="dealer.code"/></td>
            <td>
                <g:textField name="code" value="${dealer.code}" class="${hasErrors(bean: dealer, field: 'code', 'errors')}"/>
                <g:renderFieldErrors bean="${dealer}" field="code"/>
            </td>
        </tr>

    </table>

</div>
</body>
</html>
