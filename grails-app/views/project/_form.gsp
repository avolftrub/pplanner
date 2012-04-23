<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main2">
    <title><g:message code="project.action.show.title"/></title>
</head>
<body>
<div>
    <p> Between
</div>
<div class="content">
    <h1>${project.name},&nbsp;<span class="projectStatus"><g:message code="${'project.status.'+project.status}"/></span></h1>

    <table class="entityEdit">
        <colgroup>
            <col width="150">
            <col width="150">
        </colgroup>
        <tr>
            <td><g:message code="project.createDate"/></td>
            <td>
                <g:textField name="customer" value="${project.customer}" class="${hasErrors(bean: project, field: 'customer', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${project}" field="customer"/>
            </td>
        </tr>
        <tr>
            <td><g:message code="project.department"/></td>
            <td>
                <g:textField name="department" value="${project.department}" class="${hasErrors(bean: project, field: 'department', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${project}" field="department"/>
            </td>
        </tr>
        <tr>
            <td><g:message code="project.city"/></td>
            <td>
                <g:textField name="city" value="${project.city}" class="${hasErrors(bean: project, field: 'city', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${project}" field="city"/>
            </td>
        </tr>
        <tr>
            <td><g:message code="project.contactPerson"/></td>
            <td>
                <g:textField name="contactPerson" value="${project.contactPerson}" class="${hasErrors(bean: project, field: 'contactPerson', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${project}" field="contactPerson"/>
            </td>
        </tr>

        <tr>
            <td><g:message code="project.contactPhone"/></td>
            <td>
                <g:textField name="contactPhone" value="${project.contactPhone}" class="${hasErrors(bean: project, field: 'contactPhone', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${project}" field="contactPhone"/>
            </td>
        </tr>

        <tr>
            <td><g:message code="project.releaseDate"/></td>
            <td>
                <input type="text" id="releaseDate" name="releaseDate" value="${project.releaseDate}"
                       class="${hasErrors(bean: project, field: 'contactPhone', 'errors')}"/>
                <g:renderFieldErrors bean="${project}" field="releaseDate"/>
            </td>
        </tr>

    </table>

</div>
</body>
</html>
