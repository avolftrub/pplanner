<%@ page import="ru.appbio.ProjectStatus" %>
<div class="content edit">
    <g:if test="${isNew}">
        <h1 class="newEntity"><g:message code="project.action.create.title"/></h1>
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
            <td><g:message code="project.name"/></td>
            <td>
                <g:textField name="name" value="${project.name}" class="${hasErrors(bean: project, field: 'name', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${project}" field="name"/>
            </td>
        </tr>
        <tr>
            <td><g:message code="project.dealer"/></td>
            <td>
                <g:if test="${isNew}">
                    <g:if test="${currentUser?.isAdmin()}">
                        <g:select name="dealer" class="w45" value="${project.dealer?.name}" optionKey="id" from="${Dealer.list()}"/>
                    </g:if>
                    <g:else>
                        ${currentUser?.dealer?.name}
                    </g:else>
                </g:if>
                <g:else>
                    <span class="entityValue">${project.dealer?.name}</span>
                </g:else>
            </td>
        </tr>

        <tr>
            <td><g:message code="project.customer"/></td>
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
            <td><g:message code="project.current.status"/></td>
            <td>
                <g:select name="status" class="w45" value="${project.status}" optionKey="id" from="${ProjectStatus.values()}"/>
                <g:renderFieldErrors bean="${project}" field="status"/>
            </td>
        </tr>

        <tr>
            <td><g:message code="project.releaseDate"/></td>
            <td>
                <input class="calendarInput ${hasErrors(bean: project, field: 'releaseDate', 'errors')}" type="text" id="releaseDate" name="releaseDate" value="${formatPlainDate(value: project.releaseDate)}"/>
                <g:renderFieldErrors bean="${project}" field="releaseDate"/>
            </td>
        </tr>

        <tr>
            <td><g:message code="project.comments"/></td>
            <td>
                <g:textArea cols="100" rows="5" name="comments" value="${project.comments}" class="${hasErrors(bean: project, field: 'comments', 'errors')}"/>
                <g:renderFieldErrors bean="${project}" field="comments"/>
            </td>
        </tr>


    </table>

</div>
