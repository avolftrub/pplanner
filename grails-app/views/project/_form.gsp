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
            <col width="35%">
            <col width="65%">
        </colgroup>
        <tr>
            <td><g:message code="project.name"/></td>
            <td>
                <g:textField name="name" value="${project.name}" class="${hasErrors(bean: project, field: 'name', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${project}" field="name"/>
            </td>
        </tr>

        <tr>
            <td><g:message code="project.productName"/></td>
            <td>
                <g:textField name="productName" value="${project.productName}" class="${hasErrors(bean: project, field: 'productName', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${project}" field="productName"/>
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
                <g:textField name="city" value="${project.city?.name}" class="${hasErrors(bean: project, field: 'city', 'errors')}" lookupUrl="${createLink(controller: 'project', action: 'lookupCity')}"/>&nbsp;*
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
            <td><g:message code="project.contactEmail"/></td>
            <td>
                <g:textField name="contactEmail" value="${project.contactEmail}" class="${hasErrors(bean: project, field: 'contactEmail', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${project}" field="contactEmail"/>
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
                <g:select name="status" class="w45" value="${project.status.id}" optionKey="id" from="${ProjectStatus.values()}" valueMessagePrefix="project.status"/>
                <g:renderFieldErrors bean="${project}" field="status"/>
            </td>
        </tr>

        <tr>
            <td><g:message code="project.sum.rub"/></td>
            <td>
                <g:textField name="sum" value="${project.sum.intValue()}" class="w45 ${hasErrors(bean: project, field: 'sum', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${project}" field="sum"/>
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
            <td><g:message code="project.closeDate"/></td>
            <td>
                <input class="calendarInput ${hasErrors(bean: project, field: 'closeDate', 'errors')}" type="text" id="closeDate" name="closeDate" value="${formatPlainDate(value: project.closeDate)}"/>
                <g:renderFieldErrors bean="${project}" field="closeDate"/>
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
